package com.eximbay.okr.listener;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditable {
    @CreatedBy
    @Column(name = "REG_USER_ID", nullable = false)
    protected String createdBy;

    @LastModifiedBy
    @Column(name = "MOD_USER_ID")
    protected String updatedBy;

    @CreatedDate
    @Column(name = "REG_DT", nullable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    protected Instant updatedDate;

}