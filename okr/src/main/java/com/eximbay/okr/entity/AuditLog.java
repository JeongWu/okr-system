package com.eximbay.okr.entity;

import com.eximbay.okr.enumeration.LogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Data
@Table(name = "audit_log")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOG_SEQ", length = 11)
    private Integer logSeq;

    @Column(name = "LOG_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "TARGET", length = 100)
    private String target;

    @Column(name = "PARAMETER", length = 1000)
    private String parameter;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "ACCESS_IP", length = 15, nullable = false)
    private String accessIp;

    @CreatedDate
    @Column(name = "REG_DT")
    protected Instant createdDate;
}
