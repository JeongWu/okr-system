package com.eximbay.okr.api;

import com.eximbay.okr.dto.feedback.AddFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.AddFeedbackResponseDto;
import com.eximbay.okr.dto.feedback.DeleteFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.service.Interface.IFeedbackService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feedbacks")
public class FeedbackAPI {

    private final MapperFacade mapper;
    private final IFeedbackService feedbackService;

    @DeleteMapping
    public ResponseEntity<Void> deleteFeedback(@RequestBody DeleteFeedbackRequestDto deleteFeedbackRequest){
        feedbackService.delete(deleteFeedbackRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<AddFeedbackResponseDto> addFeedback(@RequestBody AddFeedbackRequestDto request){
        FeedbackDto saveFeedback = feedbackService.addFeedback(request);
        AddFeedbackResponseDto response = mapper.map(saveFeedback, AddFeedbackResponseDto.class);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
