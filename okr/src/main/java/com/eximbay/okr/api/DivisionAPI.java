package com.eximbay.okr.api;

import com.eximbay.okr.model.DivisionForDivisionsModel;
import com.eximbay.okr.model.MemberForDivisionChangeMembersModel;
import com.eximbay.okr.service.Interface.IDivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/divisions")
public class DivisionAPI {

    private final IDivisionService divisionService;

    @PostMapping("/datatables")
    public List<DivisionForDivisionsModel> getDivisions() {
        List<DivisionForDivisionsModel> divisions = divisionService.buildDivisionsModel().getDivisions();
        return divisions;
    }

    @PostMapping("/change-members/datatables/{id}")
    public List<MemberForDivisionChangeMembersModel> getMembersOfDivision(@PathVariable Integer id) {
        List<MemberForDivisionChangeMembersModel> members = divisionService.getMembersForDivisionChangeMembersModel(id);
        return members;
    }
}
