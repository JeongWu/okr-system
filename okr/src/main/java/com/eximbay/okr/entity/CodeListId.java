package com.eximbay.okr.entity;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

@Data
@Embeddable
public class CodeListId implements Serializable {
    @Column(name = "CODE", length = 50)
    String code;

    @ManyToOne
    @JoinColumn(name = "GROUP_CODE")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private CodeGroup groupCode;
}
