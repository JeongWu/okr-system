package com.eximbay.okr.service.Interface;

import com.eximbay.okr.model.okr.OkrKeyResultDetailModel;
import com.eximbay.okr.model.okr.OkrObjectiveDetailModel;

public interface IOkrService {
    OkrObjectiveDetailModel buildOkrObjectiveDetailModel(String type, Integer seq);

    OkrKeyResultDetailModel buildOkrKeyResultDetailModel(String type, Integer seq);
}
