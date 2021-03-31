package com.eximbay.okr.model.common;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.like.LikeDto;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class LikeModel {
    private String content = "";
    private boolean liked = false;

    public LikeModel(List<LikeDto> likes, MemberDto currentMember) {
        if (likes.size() == 0) return;

        this.liked = Optional.ofNullable(currentMember).map(m->
                likes.stream().map(e -> e.getMember().getMemberSeq()).collect(Collectors.toSet()).contains(m.getMemberSeq()))
                .orElse(false);

        Integer memberSeq = Optional.ofNullable(currentMember).map(MemberDto::getMemberSeq).orElse(null);
        String members = likes.stream().filter(m -> !m.getMember().getMemberSeq().equals(memberSeq))
                .map(m -> m.getMember().getName())
                .collect(Collectors.joining(", "));
        if (this.liked)
            if (members.equals(""))members = "You";
            else members = "You, " + members;
        members = members.replaceFirst("(?s)(.*)" + ",", "$1" + " and");
        this.content = members + " like this";

    }
}
