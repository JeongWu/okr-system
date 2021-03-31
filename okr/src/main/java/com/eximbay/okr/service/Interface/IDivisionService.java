package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.model.DivisionChangeMembersModel;
import com.eximbay.okr.model.DivisionUpdateFormModel;
import com.eximbay.okr.model.DivisionsModel;
import com.eximbay.okr.model.EditDivisionModel;
import com.eximbay.okr.model.MemberForDivisionChangeMembersModel;
import com.eximbay.okr.model.division.DivisionAddModel;

import java.util.List;

public interface IDivisionService extends IService<DivisionDto, Integer> {

    DivisionsModel buildDivisionsModel();

    EditDivisionModel buildEditDivisionModel(Integer id);

    void updateFormModel(DivisionUpdateFormModel updateFormModel);

    DivisionChangeMembersModel buildDivisionChangeMembersModel(Integer id);

    DivisionAddModel buildDefaultDivisionAddModel();

    Division addDivision(DivisionAddModel divisionAddModel);

    List<MemberForDivisionChangeMembersModel> getMembersForDivisionChangeMembersModel(Integer id);
}
