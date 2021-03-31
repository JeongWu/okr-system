package com.eximbay.okr.service.specification;

import com.eximbay.okr.dto.GenericFilter;
import com.eximbay.okr.dto.query.EqualQueryDto;
import com.eximbay.okr.dto.query.GreaterThanQueryDto;
import com.eximbay.okr.dto.query.InQueryDto;
import com.eximbay.okr.dto.query.LessThanQueryDto;
import com.eximbay.okr.dto.query.LikeQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class GenericQuery {

    private <T> Path getPath(Root<T> root, String fieldsString) {
        String[] fields = fieldsString.split(",");
        Path path = root.get(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }
        return path;
    }

    public <T> Specification<T> greaterThanQuery(String field, Comparable value, boolean isEqual) {
        if (isEqual) return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), value);
        return (root, query, cb) -> cb.greaterThan(getPath(root, field), value);
    }

    public <T> Specification<T> lessThanQuery(String field, Comparable value, boolean isEqual) {
        if (isEqual) return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(field), value);
        return (root, query, cb) -> cb.lessThan(getPath(root, field), value);
    }

    public <T> Specification<T> equalQuery(String field, Object value) {
        return (root, query, cb) -> cb.equal(getPath(root, field), value);
    }

    public <T> Specification<T> likeQuery(String field, String value) {
        return (root, query, cb) -> cb.like(getPath(root, field), "%" + value + "%");
    }

    public <T> Specification<T> notNullQuery(String field) {
        return (root, query, cb) -> cb.isNotNull(getPath(root, field));
    }

    public <T> Specification<T> inQuery(String field, List<Object> in) {
        return (root, query, cb) -> getPath(root, field).in(in);
    }

    public <T> Specification<T> buildQuery(Class<T> T, GenericFilter filter) {
        Specification<T> query = Specification.where(null);
        if (filter.getLikeQuery() != null) query = query.and(buildLikeQueryFromFilter(filter));
        if (filter.getEqualQuery() != null) query = query.and(buildEqualQueryFromFilter(filter));
        if (filter.getLessThanQuery() != null) query = query.and(buildLessThanQueryFromFilter(filter));
        if (filter.getGreaterThanQuery() != null) query = query.and(buildGreaterThanQueryFromFilter(filter));
        if (filter.getNotNullQuery() != null) query = query.and(buildNotNullQueryFromFilter(filter));
        if (filter.getInQuery() != null) query = query.and(buildInQueryFromFilter(filter));
        return query;
    }

    private <T> Specification<T> buildInQueryFromFilter(GenericFilter filter) {
        List<List<InQueryDto>> listQuery = filter.getInQuery();
        Specification<T> result = Specification.where(null);
        for (List<InQueryDto> query : listQuery) {
            result = result.and(buildInQuery(query));
        }
        return result;
    }

    private <T> Specification<T> buildInQuery(List<InQueryDto> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0) result = result.and(inQuery(query.get(i).getField(), query.get(i).getIn()));
            else result = result.or(inQuery(query.get(i).getField(), query.get(i).getIn()));
        }
        return result;
    }

    private <T> Specification<T> buildNotNullQueryFromFilter(GenericFilter filter) {
        List<List<String>> listQuery = filter.getNotNullQuery();
        Specification<T> result = Specification.where(null);
        for (List<String> query : listQuery) {
            result = result.and(buildNotNullQuery(query));
        }
        return result;
    }

    private <T> Specification<T> buildNotNullQuery(List<String> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0) result = result.and(notNullQuery(query.get(i)));
            else result = result.or(notNullQuery(query.get(i)));
        }
        return result;
    }

    private <T> Specification<T> buildGreaterThanQueryFromFilter(GenericFilter filter) {
        List<List<GreaterThanQueryDto>> listQuery = filter.getGreaterThanQuery();
        Specification<T> result = Specification.where(null);
        for (List<GreaterThanQueryDto> query : listQuery) {
            result = result.and(buildGreaterThanQuery(query));
        }
        return result;
    }

    private <T> Specification<T> buildGreaterThanQuery(List<GreaterThanQueryDto> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0)
                result = result.and(greaterThanQuery(query.get(i).getField(), query.get(i).getValue(), query.get(i).isEqual()));
            else
                result = result.or(greaterThanQuery(query.get(i).getField(), query.get(i).getValue(), query.get(i).isEqual()));
        }
        return result;
    }

    private <T> Specification<T> buildLessThanQueryFromFilter(GenericFilter filter) {
        List<List<LessThanQueryDto>> listQuery = filter.getLessThanQuery();
        Specification<T> result = Specification.where(null);
        for (List<LessThanQueryDto> query : listQuery) {
            result = result.and(buildLessThanQuery(query));
        }
        return result;
    }

    private <T> Specification<T> buildLessThanQuery(List<LessThanQueryDto> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0)
                result = result.and(lessThanQuery(query.get(i).getField(), query.get(i).getValue(), query.get(i).isEqual()));
            else
                result = result.or(lessThanQuery(query.get(i).getField(), query.get(i).getValue(), query.get(i).isEqual()));
        }
        return result;
    }

    private <T> Specification<T> buildEqualQueryFromFilter(GenericFilter filter) {
        List<List<EqualQueryDto>> listQuery = filter.getEqualQuery();
        Specification<T> result = Specification.where(null);
        for (List<EqualQueryDto> query : listQuery) {
            result = result.and(buildEqualQuery(query));
        }
        ;
        return result;
    }

    private <T> Specification<T> buildEqualQuery(List<EqualQueryDto> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0) result = result.and(equalQuery(query.get(i).getField(), query.get(i).getValue()));
            else result = result.or(equalQuery(query.get(i).getField(), query.get(i).getValue()));
        }
        return result;
    }

    private <T> Specification<T> buildLikeQueryFromFilter(GenericFilter filter) {
        List<List<LikeQueryDto>> listQuery = filter.getLikeQuery();
        Specification<T> result = Specification.where(null);
        for (List<LikeQueryDto> query : listQuery) {
            result = result.and(buildLikeQuery(query));
        }
        ;
        return result;
    }

    private <T> Specification<T> buildLikeQuery(List<LikeQueryDto> query) {
        Specification<T> result = Specification.where(null);
        for (int i = 0; i < query.size(); i++) {
            if (i == 0) result = result.and(likeQuery(query.get(i).getField(), query.get(i).getValue()));
            else result = result.or(likeQuery(query.get(i).getField(), query.get(i).getValue()));
        }
        return result;
    }
}
