package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
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

    @ManyToOne
    @JoinColumn(name = "PARENT_FEEDBACK_SEQ")
    private Feedback parentFeedback;

    @Column(name = "DEPTH", length = 11)
    private Integer depth =0 ;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;

    @CreatedDate
    @Column(name = "REG_DT", nullable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    protected Instant updatedDate;

    @Column(name = "DEL_DT")
    protected Instant deletedDate;
}
