package com.kiwoom.app.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRes <T> {

    private int page;                 // 현재 페이지 (1-based)

    private int size;                // 요청한 페이지 크기

    private int totalPages;          // 전체 페이지 수

    private long totalElements;      // 전체 항목 수

    private int numberOfElements;    // 현재 페이지에 포함된 항목 수

    private boolean isFirst;         // 첫 페이지 여부

    private boolean isLast;          // 마지막 페이지 여부

    private boolean hasPrevious;     // 이전 페이지 존재 여부

    private boolean hasNext;         // 다음 페이지 존재 여부

    private int navigatePages;      // 네비게이션 페이지 수

    private int navigateFirstPage;  // 네비게이션 첫 페이지

    private int navigateLastPage;   // 네비게이션 마지막 페이지

    private int[] navigatePageNums; // 네비게이션 페이지 배열

    private List<T> list;
}
