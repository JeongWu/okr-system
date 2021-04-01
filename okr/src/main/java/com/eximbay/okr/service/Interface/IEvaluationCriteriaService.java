package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.evaluationcriteria.EvaluationCriteriaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IEvaluationCriteriaService extends IService<EvaluationCriteriaDto, String> {
    List<EvaluationCriteriaDto> findByGroupCode(String groupCode);
    List<EvaluationCriteriaDto> findByGroupCodeLike(String groupCodeLike);
}
