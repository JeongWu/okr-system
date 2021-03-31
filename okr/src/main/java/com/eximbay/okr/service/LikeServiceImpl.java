package com.eximbay.okr.service;

import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.dto.GenericFilter;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.like.LikeRequestDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.query.EqualQueryDto;
import com.eximbay.okr.dto.query.InQueryDto;
import com.eximbay.okr.entity.Like;
import com.eximbay.okr.entity.Like_;
import com.eximbay.okr.entity.Member_;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.exception.RestUserException;
import com.eximbay.okr.repository.LikeRepository;
import com.eximbay.okr.service.Interface.ILikeService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.specification.GenericQuery;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements ILikeService {

    private final MapperFacade mapper;
    private final LikeRepository likeRepository;
    private final GenericQuery genericQuery;

    @Autowired
    private IMemberService memberService;

    @Override
    public List<LikeDto> findAll() {
        List<Like> likes = likeRepository.findAll();
        return mapper.mapAsList(likes, LikeDto.class);
    }

    @Override
    public Optional<LikeDto> findById(Integer id) {
        Optional<Like> like = likeRepository.findById(id);
        return like.map(m -> mapper.map(m, LikeDto.class));
    }

    @Override
    public void remove(LikeDto likeDto) {
        Like like = mapper.map(likeDto, Like.class);
        likeRepository.delete(like);
    }

    @Override
    public LikeDto save(LikeDto likeDto) {
        Like like = mapper.map(likeDto, Like.class);
        like = likeRepository.save(like);
        return mapper.map(like, LikeDto.class);
    }

    @Override
    public List<LikeDto> getLikeForViewOkr(List<Integer> objectivesSeq, List<Integer> keyResultsSeq) {
        GenericFilter objectiveFilter = GenericFilter.builder()
                .equalQuery(List.of(List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.OBJECTIVE.name()))))
                .inQuery(List.of(List.of(new InQueryDto<>(Like_.SOURCE_SEQ, objectivesSeq))))
                .build();
        GenericFilter keyResultFilter = GenericFilter.builder()
                .equalQuery(List.of(List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.KEY_RESULT.name()))))
                .inQuery(List.of(List.of(new InQueryDto<>(Like_.SOURCE_SEQ, keyResultsSeq))))
                .build();

        List<Like> likes = likeRepository.findAll(
                genericQuery.buildQuery(Like.class, objectiveFilter)
                        .or(genericQuery.buildQuery(Like.class, keyResultFilter)));
        return mapper.mapAsList(likes, LikeDto.class);
    }

    @Override
    public List<LikeDto> getLikeByListFeedbacksSeq(List<Integer> feedbacksSeq) {
        GenericFilter filter = GenericFilter.builder()
                .equalQuery(List.of(List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.FEEDBACK.name()))))
                .inQuery(List.of(List.of(new InQueryDto<>(Like_.SOURCE_SEQ, feedbacksSeq))))
                .build();

        List<Like> likes = likeRepository.findAll(genericQuery.buildQuery(Like.class, filter));
        return mapper.mapAsList(likes, LikeDto.class);
    }

    @Override
    public List<LikeDto> getLikesByObjectiveSeq(Integer objectiveSeq) {
        GenericFilter filter = GenericFilter.builder()
                .equalQuery(List.of(
                        List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.OBJECTIVE.name())),
                        List.of(new EqualQueryDto(Like_.SOURCE_SEQ, objectiveSeq))))
                .build();

        List<Like> likes = likeRepository.findAll(genericQuery.buildQuery(Like.class, filter));
        return mapper.mapAsList(likes, LikeDto.class);
    }

    @Override
    public void handleLikeRequest(LikeRequestDto likeRequest) {
        MemberDto currentMember = memberService.getCurrentMember().orElseThrow(() -> new RestUserException(ErrorMessages.loginRequired));
        String likeAction = "like";
        if (likeAction.equals(likeRequest.getAction()))
            doLikeAction(likeRequest, currentMember);
        else doUnlikeRequest(likeRequest, currentMember);
    }

    private void doLikeAction(LikeRequestDto likeRequest, MemberDto currentMember) {
        LikeDto likeDto = mapper.map(likeRequest, LikeDto.class);
        likeDto.setMember(currentMember);
        save(likeDto);
    }

    private void doUnlikeRequest(LikeRequestDto likeRequest, MemberDto currentMember) {
        GenericFilter filter = GenericFilter.builder()
                .equalQuery(List.of(
                        List.of(new EqualQueryDto(Like_.SOURCE_TABLE, likeRequest.getSourceTable())),
                        List.of(new EqualQueryDto(Like_.SOURCE_SEQ, likeRequest.getSourceSeq())),
                        List.of(new EqualQueryDto(Like_.MEMBER + "," + Member_.MEMBER_SEQ, currentMember.getMemberSeq()))))
                .build();

        Like like = likeRepository.findAll(genericQuery.buildQuery(Like.class, filter))
                .stream().findFirst().orElseThrow(() -> new RestUserException("You do not like it yet"));
        likeRepository.delete(like);
    }

    @Override
    public List<LikeDto> getLikesByKeyResultSeq(Integer keyResultSeq) {
        GenericFilter filter = GenericFilter.builder()
                .equalQuery(List.of(
                        List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.KEY_RESULT.name())),
                        List.of(new EqualQueryDto(Like_.SOURCE_SEQ, keyResultSeq))))
                .build();

        List<Like> likes = likeRepository.findAll(genericQuery.buildQuery(Like.class, filter));
        return mapper.mapAsList(likes, LikeDto.class);
    }
}
