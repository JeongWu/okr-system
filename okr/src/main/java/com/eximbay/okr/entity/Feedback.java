package com.eximbay.okr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Table(name = "feedback")
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = { "feedbackThreads"})
@EqualsAndHashCode(exclude = { "feedbackThreads"})
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FEEDBACK_SEQ", length = 11)
    private Integer feedbackSeq;

    @Column(name = "SOURCE_TABLE", length = 50, nullable = false)
    private String sourceTable;

    @Column(name = "SOURCE_SEQ", length = 11, nullable = false)
    private Integer sourceSeq;

    @Column(name = "FEEDBACK", length = 1000)
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ", nullable = false)
    private Member member;

    @Column(name = "LIKES",length = 11, nullable = false)
    private Integer likes = 0;

    @OneToMany(mappedBy = "feedback")
    private List<FeedbackThread> feedbackThreads;

    @CreatedDate
    @Column(name = "REG_DT", nullable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    protected Instant updatedDate;
}
