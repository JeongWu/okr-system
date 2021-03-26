package com.eximbay.okr.service;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.keyResultCollaborator.KeyResultCollaboratorDto;
import com.eximbay.okr.dto.like.LikeDto;
import com.eximbay.okr.dto.objectiveRelation.ObjectiveRelationDto;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.AllDetailsMemberModel;
import com.eximbay.okr.model.MemberForAllDetailsMemberModel;
import com.eximbay.okr.model.feedback.FeedbackForViewOkrModel;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;
import com.eximbay.okr.model.member.MemberViewOkrModel;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import com.eximbay.okr.model.profile.EditProfileModel;
import com.eximbay.okr.model.profile.ProfileUpdateModel;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.service.specification.MemberQuery;
import com.eximbay.okr.utils.PagingUtils;

import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberQuery memberQuery;
    private final MapperFacade mapper;
    private final IObjectiveService objectiveService;
    private final IKeyResultService keyResultService;
    private final IFeedbackService feedbackService;
    private final ILikeService likeService;
    private final IObjectiveRelationService objectiveRelationService;
    private final IKeyResultCollaboratorService keyResultCollaboratorService;

    @Autowired
    private ITeamMemberService teamMemberService;

    @Override
    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return mapper.mapAsList(members, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findById(Integer id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public void remove(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        memberRepository.delete(member);
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        member = memberRepository.save(member);
        return mapper.map(member, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public List<MemberDto> findActiveMembers() {
        return mapper.mapAsList(memberRepository.findAll(memberQuery.findActiveMember()), MemberDto.class);
    }

    @Override
    public List<MemberDto> findActiveLeadOrDirectorMembers() {
        List<Member> members = memberRepository.findAll(
                memberQuery.findActiveMember()
                .and(memberQuery.findLeadOrDirectorMember())
        );
        return mapper.mapAsList(members, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> getCurrentMember() {
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserDetails))
            return Optional.empty();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(((MyUserDetails) principal).getMemberDto());
    }

    @Override
    public EditProfileModel buildEditProfileModel(Integer id) {
        EditProfileModel dataModel = new EditProfileModel();
        dataModel.setSubheader(Subheader.EDIT_PROFILE);
        Optional<Member> member = memberRepository.findById(id);
        Optional<ProfileUpdateModel> model = member.map(m -> mapper.map(m, ProfileUpdateModel.class));
        if (model.isEmpty())
            throw new UserException(new NotFoundException("Not found Object with Id = " + id));
        dataModel.setModel(model.get());
        return dataModel;
    }


    @Override
    public MemberViewOkrModel buildMemberViewOkrModel(Integer memberSeq, String quarter) {
        MemberViewOkrModel model = new MemberViewOkrModel();
        MemberDto member = mapper.map(memberRepository.findById(memberSeq)
                .orElseThrow(()-> new UserException(ErrorMessages.resourceNotFound + memberSeq)), MemberDto.class);
        model.setMember(member);
        model.setMutedHeader(member.getLocalName());
        model.setQuarter(quarter);
        model.setEditable(getCurrentMember().map(m-> m.getMemberSeq().equals(memberSeq)).orElse(false));

        List<ObjectiveViewOkrModel> objectives = objectiveService.findMemberObjectivesOkrInQuarterByMemberSeq(memberSeq, quarter);
        List<Integer> objectivesSeq = objectives.stream().map(ObjectiveViewOkrModel::getObjectiveSeq).collect(Collectors.toList());

        List<KeyResultViewOkrModel> keyResults = keyResultService.findByObjectiveSeqIn(objectivesSeq);
        List<Integer> keyResultsSeq = keyResults.stream().map(KeyResultViewOkrModel::getKeyResultSeq).collect(Collectors.toList());

        List<FeedbackForViewOkrModel> feedbacks = feedbackService.getFeedbackForViewOkr(objectivesSeq, keyResultsSeq);
        List<ObjectiveRelationDto> objectiveRelations = objectiveRelationService.findByObjectiveSeqInAndRelatedObjectiveNotNull(objectivesSeq);
        List<KeyResultCollaboratorDto> keyResultCollaborators = keyResultCollaboratorService.findByKeyResultSeqIn(keyResultsSeq);
        List<LikeDto> likes = likeService.getLikeForViewOkr(objectivesSeq, keyResultsSeq);

        objectives.forEach(m-> {
            m.setKeyResults(keyResults.stream().filter(k-> m.getObjectiveSeq().equals(k.getObjective().getObjectiveSeq())).collect(Collectors.toList()));
        });

        objectiveService.mapFeedbackIntoObjectiveModel(objectives, feedbacks);
        objectiveService.mapLikesIntoObjectiveModel(objectives, likes);
        objectiveService.mapObjectiveRelationsIntoObjectiveModel(objectives, objectiveRelations);
        objectiveService.mapKeyResultCollaborators(objectives, keyResultCollaborators);

        model.setObjectives(objectives);
        return model;
    }

    @Override
    public AllDetailsMemberModel buildAllDetailsMemberModel(Pageable pageable) {
        AllDetailsMemberModel allDetailsMemberModel = new AllDetailsMemberModel();
        Page<Member> memberPage = memberRepository.findAll(memberQuery.findActiveMember(), PagingUtils.buildPageRequest(pageable));
        Page<MemberWithActiveDto> members = teamMemberService.addActiveMember(memberPage);
        Page<MemberForAllDetailsMemberModel> memberDtoPage = members.map(member -> {
            MemberForAllDetailsMemberModel memberForAllDetailsMemberModel = mapper.map(member, MemberForAllDetailsMemberModel.class);
            if (memberForAllDetailsMemberModel.getObjectives() != null)
                memberForAllDetailsMemberModel.setObjectives(memberForAllDetailsMemberModel.getObjectives().stream()
                .filter(m -> FlagOption.N.equals(m.getCloseFlag())).collect(Collectors.toList()));
            memberForAllDetailsMemberModel.setKeyResults(member.getKeyResults());
            return memberForAllDetailsMemberModel;
        });

        allDetailsMemberModel.setMembersPage(memberDtoPage);
        allDetailsMemberModel.setNavigationPageNumbers(PagingUtils.createNavigationPageNumbers(
        allDetailsMemberModel.getMembersPage().getNumber(), allDetailsMemberModel.getMembersPage().getTotalPages()));
        allDetailsMemberModel.setSubheader("Quarterly OKRs | Members");
        allDetailsMemberModel.setMutedHeader(memberDtoPage.getTotalElements() + "Total");
        return allDetailsMemberModel;
    }


}
