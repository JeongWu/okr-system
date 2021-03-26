package com.eximbay.okr.service;

import com.eximbay.okr.dto.GenericFilter;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.query.EqualQueryDto;
import com.eximbay.okr.dto.query.InQueryDto;
import com.eximbay.okr.entity.Like;
import com.eximbay.okr.entity.Like_;
import com.eximbay.okr.entity.Member_;
import com.eximbay.okr.enumeration.SourceTable;
import com.eximbay.okr.repository.LikeRepository;
import com.eximbay.okr.service.Interface.ILikeService;
import com.eximbay.okr.service.specification.GenericQuery;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ILikeServiceImpl implements ILikeService {
    private final LikeRepository likeRepository;
    private final GenericQuery genericQuery;
    private final MapperFacade mapper;

    @Override
    public List<LikeDto> findAll() {
        List<Like> likes = likeRepository.findAll();
        return mapper.mapAsList(likes, LikeDto.class);
    }

    @Override
    public Optional<LikeDto> findById(Integer id) {
        Optional<Like> like = likeRepository.findById(id);
        return like.map(m-> mapper.map(m, LikeDto.class));
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
    public List<LikeDto> getLikeForCompanyDashboard(List<Integer> feedbacksSeq) {
        GenericFilter filter = GenericFilter.builder()
                .equalQuery(List.of(List.of(new EqualQueryDto(Like_.SOURCE_TABLE, SourceTable.FEEDBACK.name()))))
                .inQuery(List.of(List.of(new InQueryDto<>(Like_.SOURCE_SEQ, feedbacksSeq))))
                .build();

        List<Like> likes = likeRepository.findAll(genericQuery.buildQuery(Like.class, filter));
        return mapper.mapAsList(likes, LikeDto.class);
    }
}
