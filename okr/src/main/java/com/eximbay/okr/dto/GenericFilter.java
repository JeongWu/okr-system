package com.eximbay.okr.dto;

import com.eximbay.okr.dto.query.EqualQueryDto;
import com.eximbay.okr.dto.query.GreaterThanQueryDto;
import com.eximbay.okr.dto.query.InQueryDto;
import com.eximbay.okr.dto.query.LessThanQueryDto;
import com.eximbay.okr.dto.query.LikeQueryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericFilter {
    List<List<LikeQueryDto>> likeQuery;
    List<List<EqualQueryDto>> equalQuery;
    List<List<LessThanQueryDto>> lessThanQuery;
    List<List<GreaterThanQueryDto>> greaterThanQuery;
    List<List<String>> notNullQuery;
    List<List<InQueryDto>> inQuery;
}
