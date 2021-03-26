package com.eximbay.okr.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Data
@Table(name = "feedback_thread")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FeedbackThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "THREAD_SEQ", length = 11)
    private Integer threadSeq;

    @ManyToOne
    @JoinColumn(name = "FEEDBACK_SEQ", nullable = false)
    private Feedback feedback;

    @Column(name = "LIKES",length = 11, nullable = false)
    private Integer likes = 0;

    @Column(name = "THREAD", length = 1000)
    private String thread;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ", nullable = false)
    private Member member;

    @CreatedDate
    @Column(name = "REG_DT", nullable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    protected Instant updatedDate;
}
