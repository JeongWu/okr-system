package com.eximbay.okr.service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.dto.feedback.FeedbackForOkrViewDetailsDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.enumeration.ObjectiveType;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.model.common.LikeModel;
import com.eximbay.okr.model.okr.OkrKeyResultDetailModel;
import com.eximbay.okr.model.okr.OkrObjectiveDetailModel;
import com.eximbay.okr.service.Interface.*;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OkrServiceImpl implements IOkrService {

    private final MapperFacade mapper;
    private final IObjectiveService objectiveService;
    private final IFeedbackService feedbackService;
    private final ILikeService likeService;
    private final IMemberService memberService;
    private final IKeyResultService keyResultService;

    @Override
    public OkrObjectiveDetailModel buildOkrObjectiveDetailModel(String type, Integer seq) {
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        OkrObjectiveDetailModel model = new OkrObjectiveDetailModel();
        ObjectiveDto objective = objectiveService.findById(seq).orElseThrow(()-> new RestUserException(ErrorMessages.resourceNotFound + seq));
        mapper.map(objective , model);
        model.setMutedHeader(getMutedHeader(objective));

        model.setCurrentMember(currentMember.orElse(null));

        List<FeedbackDto> feedbackDtos = feedbackService.getFeedbackByObjectiveSeq(objective.getObjectiveSeq());
        List<FeedbackForOkrViewDetailsDto> feedbacks =
                mapper.mapAsList(feedbackDtos, FeedbackForOkrViewDetailsDto.class);
        List<Integer> feedbacksSeq = feedbackDtos.stream().map(FeedbackDto::getFeedbackSeq).collect(Collectors.toList());
        List<LikeDto> feedbacksLikes = likeService.getLikeByListFeedbacksSeq(feedbacksSeq);
        mapLikesAndDeletableIntoFeedbackDto(feedbacks, feedbacksLikes, currentMember.orElse(null));

        mapSubFeedbacks(feedbacks);
        model.setFeedbacks(feedbacks.stream().filter(e-> e.getDepth().equals(0)).collect(Collectors.toList()));

        List<LikeDto> likes = likeService.getLikesByObjectiveSeq(objective.getObjectiveSeq());
        model.setLikeModel(new LikeModel(likes, currentMember.orElse(null)));

        return model;
    }

    private String getMutedHeader(ObjectiveDto objective) {
        String type = objective.getObjectiveType();
            if (ObjectiveType.COMPANY.name().equals(type)) return AppConst.COMPANY_NAME;
            if (ObjectiveType.TEAM.name().equals(type)) return Optional.ofNullable(objective.getTeam()).map(TeamDto::getLocalName)
                    .orElseThrow(()-> new RestUserException("TEAM is null when Objective Type = TEAM"));
            if (ObjectiveType.MEMBER.name().equals(type)) return Optional.ofNullable(objective.getMember()).map(MemberDto::getName)
                    .orElseThrow(()-> new RestUserException("MEMBER is null when Objective Type = MEMBER"));
        return "";
    }

    private void mapLikesAndDeletableIntoFeedbackDto(List<FeedbackForOkrViewDetailsDto> feedbacks, List<LikeDto> likes, MemberDto currentMember) {
        Map<Integer, List<LikeDto>> likesMap = likes.stream().collect(Collectors.groupingBy(LikeDto::getSourceSeq));
        for (FeedbackForOkrViewDetailsDto feedback: feedbacks){
            feedback.setLikeModel(new LikeModel(likesMap.getOrDefault(feedback.getFeedbackSeq(), new ArrayList<>()), currentMember));
            feedback.setDeletable(
                    currentMember != null && (currentMember.getMemberSeq().equals(feedback.getMember().getMemberSeq()) ||
                            currentMember.getAdminFlag().equals(FlagOption.Y))
            );
        }
    }

    private void mapSubFeedbacks(List<FeedbackForOkrViewDetailsDto> feedbacks) {
        Map<Integer, FeedbackForOkrViewDetailsDto> map = feedbacks.stream().collect(Collectors.toMap(FeedbackForOkrViewDetailsDto::getFeedbackSeq, m-> m));
        for (FeedbackForOkrViewDetailsDto feedback: feedbacks){
            FeedbackForOkrViewDetailsDto parentFeedback = feedback.getParentFeedback();
            if (parentFeedback != null && map.containsKey(parentFeedback.getFeedbackSeq()))
                parentFeedback.getSubFeedbacks().add(feedback);
        }
    }

    @Override
    public OkrKeyResultDetailModel buildOkrKeyResultDetailModel(String type, Integer seq) {
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        OkrKeyResultDetailModel model = new OkrKeyResultDetailModel();
        KeyResultDto keyResult = keyResultService.findById(seq).orElseThrow(()-> new RestUserException(ErrorMessages.resourceNotFound + seq));
        mapper.map(keyResult , model);
        model.setMutedHeader(getMutedHeader(keyResult.getObjective()));

        model.setCurrentMember(currentMember.orElse(null));

        List<FeedbackDto> feedbackDtos = feedbackService.getFeedbackByKeyResultsSeq(keyResult.getKeyResultSeq());
        List<FeedbackForOkrViewDetailsDto> feedbacks =
                mapper.mapAsList(feedbackDtos, FeedbackForOkrViewDetailsDto.class);
        List<Integer> feedbacksSeq = feedbackDtos.stream().map(FeedbackDto::getFeedbackSeq).collect(Collectors.toList());
        List<LikeDto> feedbacksLikes = likeService.getLikeByListFeedbacksSeq(feedbacksSeq);
        mapLikesAndDeletableIntoFeedbackDto(feedbacks, feedbacksLikes, currentMember.orElse(null));

        mapSubFeedbacks(feedbacks);
        model.setFeedbacks(feedbacks.stream().filter(e-> e.getDepth().equals(0)).collect(Collectors.toList()));

        List<LikeDto> likes = likeService.getLikesByKeyResultSeq(keyResult.getKeyResultSeq());
        model.setLikeModel(new LikeModel(likes, currentMember.orElse(null)));

        return model;
    }
}
