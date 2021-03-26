package com.eximbay.okr.service.Interface;

import org.springframework.data.jpa.domain.Specification;

public interface IGenericQuery<T> {

    default Specification<T> isNotNull(String field) {
        return (root, query, cb) -> cb.isNotNull(root.get(field));
    }

    default Specification<T> isEqual(String field, Object value) {
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }
}
