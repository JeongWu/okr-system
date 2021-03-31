package com.eximbay.okr.utils;

import com.eximbay.okr.constant.PagingConst;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PagingUtils {

    public static PageRequest buildPageRequest(Pageable pageable) {
        int pageSize = Integer.min(Integer.max(PagingConst.MINIMUM_PAGE_SIZE, pageable.getPageSize()), PagingConst.MAXIMUM_PAGE_SIZE);
        return PageRequest.of(pageable.getPageNumber(), pageSize);
    }

    /**
     * @param currentPage: index of current page, starting from 0, this is not the number shown on UI
     * @param totalPages:  total number of pages
     * @return a list of navigation number should be shown
     */
    public static List<Long> createNavigationPageNumbers(long currentPage, long totalPages) {
        List<Long> navigationNumbers = new ArrayList<>();
        long minPageNumberToRender = Math.max(0, currentPage - 5);
        long maxPageNumberToRender = Math.min(totalPages - 1, minPageNumberToRender + 9);
        for (long i = minPageNumberToRender; i <= maxPageNumberToRender; i++) {
            navigationNumbers.add(i);
        }
        return navigationNumbers;
    }
}
