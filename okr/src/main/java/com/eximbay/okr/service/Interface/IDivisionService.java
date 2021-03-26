package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.dto.DivisionMemberDto;
import com.eximbay.okr.model.*;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.model.division.DivisionAddModel;

import java.util.List;

public interface IDivisionService extends ISerivce<DivisionDto, Integer> {

    DivisionsModel buildDivisionsModel();
    List<DivisionMemberDto> findCurrentlyValid(List<DivisionMemberDto> divisionMemberDtos);
    EditDivisionModel buildEditDivisionModel(Integer id);
    void updateFormModel(DivisionUpdateFormModel updateFormModel);
    DivisionChangeMembersModel buildDivisionChangeMembersModel(Integer id);
    DivisionAddModel buildDefaultDivisionAddModel();
    Division addDivision(DivisionAddModel divisionAddModel);
    List<MemberForDivisionChangeMembersModel> getMembersForDivisionChangeMembersModel(Integer id);
}
