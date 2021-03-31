package com.eximbay.okr.dto.codegroup;

import com.eximbay.okr.dto.codelist.CodeListDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class CodeGroupDto {

    private String groupCode;
    private String groupCodeName;
    private int codeSize;
    private String useFlag;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CodeListDto> codeLists;
}
