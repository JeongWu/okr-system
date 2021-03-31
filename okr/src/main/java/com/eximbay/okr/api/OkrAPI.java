package com.eximbay.okr.api;

import com.eximbay.okr.api.response.RestResponse;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.service.Interface.IKeyResultService;
import com.eximbay.okr.service.Interface.IObjectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/okrs")
public class OkrAPI {

    private final IObjectiveService objectiveService;
    private final IKeyResultService keyResultService;

    @GetMapping("/objective/{objectiveSeq}/active-key-results")
    public List<KeyResultDto> getActiveKeyResultByObjectiveSeq(@PathVariable(name = "objectiveSeq") int objectiveSeq) {
        return keyResultService.findByCloseFlagAndObjectiveSeq(FlagOption.N, objectiveSeq);
    }

    @PutMapping("/objective/{objectiveId}")
    public RestResponse updateObjective(@RequestBody ObjectiveDto objectiveUpdateDto, @PathVariable("objectiveId") Integer objectiveId) {
        if (!Objects.equals(objectiveId, objectiveUpdateDto.getObjectiveSeq())) {
            RestResponse.error().message("Invalid request param");
        }
        objectiveService.updateObjective(objectiveUpdateDto);
        return RestResponse.success().message("Updated successfully");
    }

    @PutMapping("/objective/{objectiveSeq}/keyresults")
    public RestResponse updateKeyResult(@RequestBody List<KeyResultDto> keyResultUpdateDtos) {
        keyResultService.updateOrAddKeyResults(keyResultUpdateDtos);
        return RestResponse.success().message("Updated successfully");
    }
}
