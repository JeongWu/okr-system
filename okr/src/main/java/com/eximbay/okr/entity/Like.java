package com.eximbay.okr.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Data
@Table(name = "member_like")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIKE_SEQ", length = 11)
    private Integer likeSeq;

    @Column(name = "SOURCE_TABLE", length = 50, nullable = false)
    private String sourceTable;

    @Column(name = "SOURCE_SEQ", length = 11, nullable = false)
    private Integer sourceSeq;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ", nullable = false)
    private Member member;

    @CreatedDate
    @Column(name = "REG_DT", nullable = false)
    protected Instant createdDate;
}
