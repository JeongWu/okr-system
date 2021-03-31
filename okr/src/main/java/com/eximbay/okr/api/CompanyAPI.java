package com.eximbay.okr.api;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.company.CompanyDashboardResponse;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.InvalidRestInputException;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.company.CompanyDashboardContentModel;
import com.eximbay.okr.model.company.CompanyOkrModel;
import com.eximbay.okr.model.company.CompanyUpdateFormModel;
import com.eximbay.okr.service.FileUploadService;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.TemplateService;
import com.eximbay.okr.utils.DateTimeUtils;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyAPI {

    private final MapperFacade mapper;
    private final FileUploadService fileUploadService;
    private final TemplateService templateService;
    private final ICompanyService companyService;
    private final IMemberService memberService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CompanyUpdateFormModel update(@Valid CompanyUpdateFormModel updateFormModel, BindingResult errors){
        if (errors.hasErrors()) throw new InvalidRestInputException(errors.getFieldErrors());

        Optional<CompanyDto> company = companyService.getCompany();
        if (company.isEmpty()) throw new UserException(new NotFoundException("Do not have any record of Company"));

        if (updateFormModel.getImageFile() != null && !updateFormModel.getImageFile().isEmpty()){
            String imageSrc;
            try {
                imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR, EntityType.COMPANY, updateFormModel.getImageFile());
            } catch (UserException e){
                String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
                throw new RestUserException(message);
            }
            company.get().setLogo(imageSrc);
        }
        mapper.map(updateFormModel, company.get());
        if (updateFormModel.isGoogleSignIn()) company.get().setGoogleSignIn(FlagOption.Y);
        else company.get().setGoogleSignIn(FlagOption.N);
        companyService.save(company.get());
        updateFormModel.setImageFile(null);
        return updateFormModel;
    }

    @GetMapping("/okrs/quarterly/dashboard/chart")
    @ResponseStatus(value = HttpStatus.OK)
    public CompanyDashboardResponse getCompanyDashboardChart(String quarter){
        formatQuarter(quarter);
        return companyService.getCompanyDashboardModel(quarter);
    }

    @GetMapping("/okrs/quarterly/dashboard")
    public String getCompanyDashboardContent(String quarter){
        formatQuarter(quarter);
        CompanyDashboardContentModel viewModel = companyService.buildCompanyDashboardContentModel(quarter);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/companies/dashboard-content");
    }

    private void formatQuarter(String quarter){
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches())
            quarter = DateTimeUtils.findCurrentQuarter();
    }

    @GetMapping("/okrs/quarterly")
    public String viewOkr(String quarter){
        formatQuarter(quarter);
        CompanyOkrModel viewModel = companyService.buildCompanyOkrModel(quarter);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/companies/okr");
    }
}

