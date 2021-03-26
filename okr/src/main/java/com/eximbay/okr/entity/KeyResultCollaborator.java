package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "key_result_collaborator")
@Entity
public class KeyResultCollaborator extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CB_SEQ", length = 11)
    private Integer cbSeq;

    @ManyToOne
    @JoinColumn(name = "KEY_RESULT_SEQ", nullable = false)
    private KeyResult keyResult;

    @ManyToOne
    @JoinColumn(name = "RELATED_KEY_RESULT_SEQ")
    private KeyResult relatedKeyResult;

    @ManyToOne
    @JoinColumn(name = "COLLABORATOR_SEQ")
    private Member collaborator;

    @Column(name = "COLLABORATION")
    private String collaboration;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;
}
