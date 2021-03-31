package com.eximbay.okr.model;

import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.member.MemberDto;
import lombok.Data;

import java.util.List;

@Data
public class WireframeModel {
    List<TeamForWireframeModel> teams;
    CompanyDto company;
    MemberDto currentMember;

    @Override
    public String toString() {
        return "WireframeModel{" +
                "teams" +
                ", company" +
                ", member" +
                '}';
    }
}
