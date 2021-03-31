package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.dto.divisionhistory.DivisionHistoryDto;
import com.eximbay.okr.dto.divisionmember.DivisionMemberWithTimeDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.entity.DivisionHistory;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.DivisionChangeMembersModel;
import com.eximbay.okr.model.DivisionForDivisionsModel;
import com.eximbay.okr.model.DivisionUpdateFormModel;
import com.eximbay.okr.model.DivisionsModel;
import com.eximbay.okr.model.EditDivisionModel;
import com.eximbay.okr.model.MemberForDivisionChangeMembersModel;
import com.eximbay.okr.model.division.DivisionAddModel;
import com.eximbay.okr.repository.CompanyRepository;
import com.eximbay.okr.repository.DivisionHistoryRepository;
import com.eximbay.okr.repository.DivisionRepository;
import com.eximbay.okr.service.Interface.IDivisionHistoryService;
import com.eximbay.okr.service.Interface.IDivisionMemberService;
import com.eximbay.okr.service.Interface.IDivisionService;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DivisionServiceImpl implements IDivisionService {

    private final MapperFacade mapper;
    private final DivisionRepository divisionRepository;
    private final IDivisionHistoryService divisionHistoryService;
    private final ITeamMemberService teamMemberService;
    private final IDivisionMemberService divisionMemberService;
    private final CompanyRepository companyRepository;
    private final DivisionHistoryRepository divisionHistoryRepository;

    @Override
    public List<DivisionDto> findAll() {
        List<Division> divisions = divisionRepository.findAll();
        return mapper.mapAsList(divisions, DivisionDto.class);
    }

    @Override
    public Optional<DivisionDto> findById(Integer id) {
        Optional<Division> division = divisionRepository.findById(id);
        return division.map(m -> mapper.map(m, DivisionDto.class));
    }

    @Override
    public void remove(DivisionDto divisionDto) {
        Division division = mapper.map(divisionDto, Division.class);
        divisionRepository.delete(division);
    }

    @Override
    public DivisionDto save(DivisionDto divisionDto) {
        Division division = mapper.map(divisionDto, Division.class);
        division = divisionRepository.save(division);
        return mapper.map(division, DivisionDto.class);
    }

    @Override
    public DivisionsModel buildDivisionsModel() {
        DivisionsModel divisionsModel = new DivisionsModel();
        List<Division> divisions = divisionRepository.findAll();
        List<DivisionDto> divisionDtos = mapper.mapAsList(divisions, DivisionDto.class);
        List<DivisionForDivisionsModel> divisionModels = mapper.mapAsList(divisions, DivisionForDivisionsModel.class);
        for (int i = 0; i < divisionModels.size(); i++) {
            if (divisionModels.get(i).getTeams() != null)
                divisionModels.get(i).setTeams(divisionModels.get(i).getTeams().stream().filter(e -> e.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));
            List<MemberDto> memberDtos = divisionMemberService.findActiveMembersOfDivision(divisionDtos.get(i));
            divisionModels.get(i).setMembers(memberDtos);
        }
        divisionsModel.setDivisions(divisionModels);
        return divisionsModel;
    }

    @Override
    public EditDivisionModel buildEditDivisionModel(Integer id) {
        Optional<Division> division = divisionRepository.findById(id);
        if (division.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = " + id));

        EditDivisionModel dataModel = new EditDivisionModel();
        dataModel.setSubheader("Edit ");
        DivisionUpdateFormModel model = mapper.map(division.get(), DivisionUpdateFormModel.class);
        dataModel.setMutedHeader(model.getName());
        model.setUseFlag(division.get().getUseFlag().equals(FlagOption.Y));
        dataModel.setModel(model);
        return dataModel;
    }

    @Override
    public void updateFormModel(DivisionUpdateFormModel updateFormModel) {
        Optional<Division> division = divisionRepository.findById(updateFormModel.getDivisionSeq());
        if (division.isEmpty())
            throw new UserException(new NotFoundException("Not found Object with Id = " + updateFormModel.getDivisionSeq()));
        mapper.map(updateFormModel, division.get());
        if (updateFormModel.isUseFlag()) division.get().setUseFlag(FlagOption.Y);
        else division.get().setUseFlag(FlagOption.N);

        DivisionHistoryDto divisionHistoryDto = mapper.map(division.get(), DivisionHistoryDto.class);
        divisionHistoryDto.setJustification(updateFormModel.getJustification());
        divisionHistoryDto.setDivision(mapper.map(division.get(), DivisionDto.class));
        divisionHistoryService.save(divisionHistoryDto);
        Division saveDivision = divisionRepository.save(division.get());
    }

    @Override
    public DivisionChangeMembersModel buildDivisionChangeMembersModel(Integer id) {
        Optional<Division> division = divisionRepository.findById(id);
        if (division.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = " + id));

        DivisionChangeMembersModel dataModel = new DivisionChangeMembersModel();
        dataModel.setMutedHeader(division.get().getLocalName());
        List<MemberDto> availableMembers = divisionMemberService.findAvailableMemberToAddToDivision(mapper.map(division.get(), DivisionDto.class));
        dataModel.setAvailableMembers(availableMembers);
        dataModel.setDivision(mapper.map(division.get(), DivisionDto.class));
        return dataModel;
    }

    @Override
    public List<MemberForDivisionChangeMembersModel> getMembersForDivisionChangeMembersModel(Integer id) {
        Optional<Division> division = divisionRepository.findById(id);
        if (division.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = " + id));

        List<DivisionMemberWithTimeDto> memberDtos = divisionMemberService.findActiveMembersOfDivisionWithTime(mapper.map(division.get(), DivisionDto.class));
        List<MemberForDivisionChangeMembersModel> memberModels = new ArrayList<>();
        memberDtos.forEach(m -> {
            MemberForDivisionChangeMembersModel item = mapper.map(m.getMember(), MemberForDivisionChangeMembersModel.class);
            List<TeamDto> teamDtos = teamMemberService.findActiveTeamsOfMember(m.getMember());
            item.setTeams(teamDtos);
            item.setApplyBeginDate(m.getApplyBeginDate());
            memberModels.add(item);
        });
        return memberModels;
    }

    @Override
    public DivisionAddModel buildDefaultDivisionAddModel() {
        DivisionAddModel divisionAddModel = new DivisionAddModel();
        divisionAddModel.setCompany(companyRepository.findById(1).orElse(null));
        return divisionAddModel;
    }

    @Override
    public Division addDivision(DivisionAddModel divisionAddModel) {
        Division division = mapper.map(divisionAddModel, Division.class);
        if (divisionAddModel.isUseFlag()) {
            division.setUseFlag("Y");
        } else {
            division.setUseFlag("N");
        }
        division = divisionRepository.save(division);
        DivisionHistory divisionHistory = mapper.map(division, DivisionHistory.class);
        divisionHistory.setJustification("Add new");
        divisionHistory.setDivision(division);
        divisionHistoryRepository.save(divisionHistory);
        return division;
    }
}
