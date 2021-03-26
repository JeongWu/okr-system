package com.eximbay.okr.service.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Data
@AllArgsConstructor
public class GenericQuery {

    public <T> Specification<T> datePropertyAfter( Class<T> T, String field, Instant instant){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), instant);
    }

    public <T> Specification<T> datePropertyBefore( Class<T> T, String field, Instant instant){
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(field), instant);
    }

}
