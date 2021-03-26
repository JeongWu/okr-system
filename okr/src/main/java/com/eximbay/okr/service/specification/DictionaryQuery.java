package com.eximbay.okr.service.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.entity.Dictionary_;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class DictionaryQuery {

    public Specification<Dictionary> findByDictionaryType(String type) {
        return (root, query, cb) -> cb.equal(root.get(Dictionary_.DICTIONARY_TYPE), type);

    }

    public Specification<Dictionary> findActiveDictionaryOnly() {
        return (root, query, cb) -> cb.equal(root.get(Dictionary_.USE_FLAG), FlagOption.Y);
    }

    
    // public Specification<Dictionary> findActiveKeyResult() {
    //     Specification<Dictionary> result = (root, query, cb) -> cb.equal(root.get(Dictionary_.DICTIONARY_TYPE),
    //             DictionaryType.KEY_RESULT);
    //     result = result.and((root, query, cb) -> cb.equal(root.get(Dictionary_.USE_FLAG), FlagOption.Y));
    //     return result;

    // }

}
