package com.eximbay.okr.service;

import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.feedback.AddFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.DeleteFeedbackRequestDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.entity.Feedback;
import com.eximbay.okr.entity.Feedback_;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.repository.FeedbackRepository;
import com.eximbay.okr.service.Interface.IFeedbackService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.specification.FeedbackQuery;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {

    private final MapperFacade mapper;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackQuery feedbackQuery;

    @Autowired
    private IMemberService memberService;

    @Override
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }

    @Override
    public Optional<FeedbackDto> findById(Integer id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(m -> mapper.map(m, FeedbackDto.class));
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
    public List<FeedbackForViewOkrModel> getFeedbackForViewOkr(List<Integer> objectiveSeqList, List<Integer> keyResultSeqList) {
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

    @Override
    public List<FeedbackDto> getFeedbackByObjectiveSeq(Integer objectiveSeq) {
        List<Feedback> feedbacks = feedbackRepository.findAll(
                feedbackQuery.isEqual(Feedback_.SOURCE_TABLE, SourceTable.OBJECTIVE.name())
                        .and(feedbackQuery.isEqual(Feedback_.SOURCE_SEQ, objectiveSeq)), Sort.by("createdDate")
        );
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }

    @Override
    public void delete(DeleteFeedbackRequestDto request) {
        MemberDto memberDto = memberService.getCurrentMember().orElseThrow(() -> new RestUserException(ErrorMessages.authorizationRequired));
        Feedback feedback = feedbackRepository.findById(request.getFeedbackSeq())
                .orElseThrow(() -> new RestUserException(ErrorMessages.resourceNotFound + request.getFeedbackSeq()));
        if (!memberDto.getMemberSeq().equals(feedback.getMember().getMemberSeq()) && !memberDto.getAdminFlag().equals(FlagOption.Y))
            throw new RestUserException(ErrorMessages.authorizationRequired);
        feedback.setUseFlag(FlagOption.N);
        feedback.setDeletedDate(Instant.now());
        feedbackRepository.save(feedback);
    }

    @Override
    public FeedbackDto addFeedback(AddFeedbackRequestDto request) {
        validate(request);
        MemberDto currentMember = memberService.getCurrentMember().orElseThrow(() -> new RestUserException(ErrorMessages.loginRequired));
        FeedbackDto feedback = mapper.map(request, FeedbackDto.class);
        if (request.getParentFeedbackSeq() != null) {
            FeedbackDto parentFeedback = findById(request.getParentFeedbackSeq())
                    .orElseThrow(() -> new RestUserException(ErrorMessages.resourceNotFound + request.getParentFeedbackSeq()));
            feedback.setParentFeedback(parentFeedback);
            feedback.setDepth(parentFeedback.getDepth() + 1);
        }
        feedback.setMember(currentMember);
        FeedbackDto saveFeedback = save(feedback);
        return saveFeedback;
    }

    private void validate(AddFeedbackRequestDto request) {
        if (request.getSourceSeq() == null || request.getSourceTable() == null || request.getFeedback() == null)
            throw new RestUserException(ErrorMessages.dataIsNotNull);
        if (request.getSourceTable().equalsIgnoreCase("keyResult"))
            request.setSourceTable("KEY_RESULT");

    }

    @Override
    public List<FeedbackDto> getFeedbackByKeyResultsSeq(Integer keyResultSeq) {
        List<Feedback> feedbacks = feedbackRepository.findAll(
                feedbackQuery.isEqual(Feedback_.SOURCE_TABLE, SourceTable.KEY_RESULT.name())
                        .and(feedbackQuery.isEqual(Feedback_.SOURCE_SEQ, keyResultSeq)), Sort.by("createdDate")
        );
        return mapper.mapAsList(feedbacks, FeedbackDto.class);
    }
}
