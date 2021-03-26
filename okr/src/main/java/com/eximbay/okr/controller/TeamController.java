package com.eximbay.okr.controller;

import java.util.List;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.enumeration.TeamType;
import com.eximbay.okr.model.AllDetailsTeamModel;
import com.eximbay.okr.model.AllTeamUpdateModel;
import com.eximbay.okr.model.EditForViewAllTeamsModel;
import com.eximbay.okr.model.EditTeamModel;
import com.eximbay.okr.model.PageModel;
import com.eximbay.okr.model.TeamUpdateFormModel;
import com.eximbay.okr.model.team.TeamAddModel;
import com.eximbay.okr.service.Interface.IDivisionService;
import com.eximbay.okr.service.Interface.ITeamService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final ITeamService teamService;
    private final IDivisionService divisionService;
    // private final ITeamHistoryService teamHistoryService;
    // private final FileUploadService fileUploadService;

    @GetMapping
    public String viewAllTeams(Model model, @PageableDefault Pageable pageable) {
        AllDetailsTeamModel dataModel = teamService.buildAllDetailsTeamModel(pageable);
        model.addAttribute("model", dataModel);
        return "pages/teams/teams";
        // // model.addAttribute("teamType",TeamType.values());
        // PageModel pageModel = new PageModel();
        // long totalCount = teamService.countAllTeam();
        // pageModel.setSubheader("Team");
        // pageModel.setMutedHeader(totalCount + " total");
        // model.addAttribute("model", pageModel);
        // model.addAttribute("teamType", TeamType.values());
        // return "pages/teams/team-list";
    }

    @GetMapping("/view/edit/{id}")
    public String editFormForViewAllTeams(@PathVariable Integer id, Model model) {
        EditForViewAllTeamsModel viewModel = teamService.buildEditAllTeamsModel(id);

        model.addAttribute("model", viewModel);
        model.addAttribute("dataModel", viewModel.getModel());
        System.out.println(viewModel.getModel());
        return "pages/teams/edit-teams";
    }

    // @RequestMapping(value = "/view/save", method = RequestMethod.POST)
    @PostMapping("/view/save")
    public String update(@Validated AllTeamUpdateModel req, BindingResult error) {

        if (error.hasErrors())
            return "redirect:/teams/view/edit/" + req.getTeamSeq();

        teamService.updateForViewAllTeamModel(req);
        return "redirect:/teams";
    }
    // @PostMapping("/view/save")
    // public String update(AllTeamUpdateModel allTeamUpdateModel, BindingResult
    // error) {
    // Optional<TeamDto> team =
    // teamService.findById(allTeamUpdateModel.getTeamSeq());
    // if (team.isEmpty())
    // throw new UserException(
    // new NotFoundException("Not found Team with Id = " +
    // allTeamUpdateModel.getTeamSeq()));

    // if (allTeamUpdateModel.getImageFile() != null &&
    // !allTeamUpdateModel.getImageFile().isEmpty()) {
    // String imageSrc;
    // try {
    // imageSrc = fileUploadService.store(FileType.IMAGE, FileContentType.AVATAR,
    // EntityType.TEAM,
    // allTeamUpdateModel.getImageFile());
    // } catch (UserException e) {
    // String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
    // throw new RestUserException(message);
    // }
    // team.get().setImage(imageSrc);
    // }

    // team.get().setIntroduction(allTeamUpdateModel.getIntroduction());

    // teamService.save(team.get());
    // allTeamUpdateModel.setImageFile(null);
    // if (error.hasErrors())
    // return "redirect:/teams/view/edit/" + allTeamUpdateModel.getTeamSeq();

    // return "redirect:/teams";
    // }

    @GetMapping("/list")
    public String viewTeamList(Model model) {
        PageModel pageModel = new PageModel();
        long totalCount = teamService.countAllTeam();
        pageModel.setSubheader(Subheader.TEAM);
        pageModel.setMutedHeader(totalCount + " total");
        model.addAttribute("model", pageModel);
        model.addAttribute("teamType", TeamType.values());
        return "pages/teams/team-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        EditTeamModel viewModel = teamService.buildEditTeamModel(id);
        model.addAttribute("model", viewModel);
        model.addAttribute("dataModel", viewModel.getModel());
        List<DivisionDto> divisionList = divisionService.findAll();
        model.addAttribute("divisionList", divisionList);
        return "pages/teams/team-edit-details";
    }

    @PostMapping("/save")
    public String saveTeam(@Validated TeamUpdateFormModel updateFormModel, BindingResult error) {
        if (error.hasErrors())
            return "rediect:/teams/edit/" + updateFormModel.getTeamSeq();
        teamService.updateFormModel(updateFormModel);
        return "redirect:/teams/list";
    }

    @GetMapping("/add")
    public String addTeam(Model model) {
        model.addAttribute("subheader", Subheader.ADD_TEAM);
        TeamAddModel teamAddModel = new TeamAddModel();
        model.addAttribute("dataModel", teamAddModel);
        List<DivisionDto> divisionDto = divisionService.findAll();
        model.addAttribute("divisionDto", divisionDto);
        return "pages/teams/team-add";
    }

    @PostMapping(value = "/add")
    public String addTeam(@ModelAttribute TeamAddModel teamAddModel) {
        Team team = teamService.addTeam(teamAddModel);
        switch (teamAddModel.getAction()) {
        case "saveAndAddNew":
            return "redirect:/teams/add";
        case "saveAndAddMember":
            return "redirect:/teams/change-members/" + team.getTeamSeq();
        case "saveAndExit":
            return "redirect:/teams/list";
        default:
            return "redirect:/teams/list";
        }
    }
    // @GetMapping("/history/{id}")
    // public String viewTeamHistory(@PathVariable Integer id, Model model) {
    // TeamHistoriesModel teamHistoriesModel =
    // teamHistoryService.buildTeamHistoriesModel(id);
    // model.addAttribute("model", teamHistoriesModel);
    // // model.addAttribute("dataModel", teamHistoriesModel.getTeamHistories());
    // return "pages/teams/team_history";
    // }
}
