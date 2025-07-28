package com.kiwoom.app.system.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kiwoom.app.system.dto.PageReq;
import com.kiwoom.app.system.dto.PageRes;
import com.kiwoom.app.system.type.SortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

public class PageUtil {

    public static void setPageHelper(PageReq<?> params) {
        if(params.getSortColumn() != null && !params.getSortColumn().isEmpty()) {
            PageHelper.startPage(params.getPageIndex(), params.getPageSize(), params.getSortColumn() + " " + params.getSortOrder());
        }else {
            PageHelper.startPage(params.getPageIndex(), params.getPageSize());
        }
    }

    public static Pageable getPageable(PageReq<?> pageReq) {
        if(pageReq.getSortColumn() != null && !pageReq.getSortColumn().isEmpty()) {
            return PageRequest.of(
                    pageReq.getPageIndex() - 1,
                    pageReq.getPageSize(),
                    Sort.by((pageReq.getSortOrder() != null && pageReq.getSortOrder() == SortType.DESC) ? Sort.Order.desc(pageReq.getSortColumn()) : Sort.Order.asc(pageReq.getSortColumn()))
            );
        }else {
            return PageRequest.of(
                    pageReq.getPageIndex(),
                    pageReq.getPageSize()
            );
        }
    }

    public static <T> PageRes<T> fromPage(Page<T> page, int navigatePages) {
        int currentPage = page.getNumber() + 1; // 0-based → 1-based
        int totalPages = page.getTotalPages();

        if(totalPages == 0) {
            return PageRes.<T>builder()
                    .page(currentPage)
                    .size(page.getSize())
                    .totalPages(totalPages)
                    .totalElements(page.getTotalElements())
                    .numberOfElements(page.getNumberOfElements())
                    .isFirst(page.isFirst())
                    .isLast(page.isLast())
                    .hasPrevious(page.hasPrevious())
                    .hasNext(page.hasNext())
                    .navigatePages(navigatePages)
                    .navigateFirstPage(0)
                    .navigateLastPage(0)
                    .navigatePageNums(new int[0])
                    .list(page.getContent())
                    .build();
        }

        int startPage = Math.max(currentPage - navigatePages / 2, 1);
        int endPage = startPage + navigatePages - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = Math.max(endPage - navigatePages + 1, 1);
        }

        int[] navigatePageNums = IntStream.rangeClosed(startPage, endPage).toArray();

        return PageRes.<T>builder()
                .page(currentPage)
                .size(page.getSize())
                .totalPages(totalPages)
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .hasPrevious(page.hasPrevious())
                .hasNext(page.hasNext())
                .navigatePages(navigatePages)
                .navigateFirstPage(navigatePageNums[0])
                .navigateLastPage(navigatePageNums[navigatePageNums.length - 1])
                .navigatePageNums(navigatePageNums)
                .list(page.getContent())
                .build();
    }

    public static <T> PageRes<T> fromPageInfo(PageInfo<T> pageInfo) {
        return PageRes.<T>builder()
                .page(pageInfo.getPageNum())  // 이미 1-based
                .size(pageInfo.getPageSize())
                .totalPages(pageInfo.getPages())
                .totalElements(pageInfo.getTotal())
                .numberOfElements(pageInfo.getList() != null ? pageInfo.getList().size() : 0)
                .isFirst(pageInfo.isIsFirstPage())
                .isLast(pageInfo.isIsLastPage())
                .hasPrevious(pageInfo.isHasPreviousPage())
                .hasNext(pageInfo.isHasNextPage())
                .navigatePages(pageInfo.getNavigatePages())
                .navigateFirstPage(pageInfo.getNavigateFirstPage())
                .navigateLastPage(pageInfo.getNavigateLastPage())
                .navigatePageNums(pageInfo.getNavigatepageNums())
                .list(pageInfo.getList())
                .build();
    }
}
