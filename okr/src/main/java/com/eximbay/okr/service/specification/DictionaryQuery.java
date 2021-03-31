package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.entity.Dictionary_;
import lombok.AllArgsConstructor;
import lombok.Data;
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

}
