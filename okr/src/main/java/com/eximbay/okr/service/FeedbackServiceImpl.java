package com.eximbay.okr.service;

import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.entity.Feedback;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.repository.FeedbackRepository;
import com.eximbay.okr.service.Interface.IFeedbackService;
import com.eximbay.okr.service.specification.FeedbackQuery;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackQuery feedbackQuery;
    private final MapperFacade mapper;

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }

    @Override
    public Optional<FeedbackDto> findById(Integer id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(m-> mapper.map(m, FeedbackDto.class));
    }

    @Override
    public void remove(FeedbackDto feedbackDto) {
        Feedback feedback = mapper.map(feedbackDto, Feedback.class);
        feedbackRepository.delete(feedback);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = mapper.map(feedbackDto, Feedback.class);
        feedback = feedbackRepository.save(feedback);
        return mapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public List<FeedbackDto> findTop10ByOrderByCreatedDateDesc() {
        List<Feedback> feedbacks = feedbackRepository.findTop10ByOrderByCreatedDateDesc();
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }

    @Override
    public List<FeedbackForViewOkrModel> getFeedbackForViewOkr(List<Integer> objectiveSeqList, List<Integer> keyResultSeqList){
        List<Feedback> feedbacks = feedbackRepository.findAll(
                feedbackQuery.getFeedbackForCompanyViewOkr(objectiveSeqList, keyResultSeqList)
        );
        return mapper.mapAsList(feedbacks, FeedbackForViewOkrModel.class);
    }

    @Override
    public List<FeedbackDto> findByMember(Member member) {
        List<Feedback> feedbacks = feedbackRepository.findAll(
            feedbackQuery.findByMember(member)
        );
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }
}
