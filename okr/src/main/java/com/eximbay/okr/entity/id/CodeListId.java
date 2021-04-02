package com.eximbay.okr.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.eximbay.okr.entity.CodeGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
