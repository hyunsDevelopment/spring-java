package com.kiwoom.app.mois.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchJusoReqDto {

    @Schema(name = "currentPage", title = "현재 페이지 번호", defaultValue = "1")
    private String currentPage;

    @Schema(name = "countPerPage", title = "페이지당 출력 개수", defaultValue = "10")
    private String countPerPage;

    @Schema(name = "keyword", title = "검색어", example = "가산디지털1로 137")
    @NotEmpty(message = "검색어를 확인해 주세요.")
    private String keyword;

    @Schema(name = "hstryYn", title = "변동된 주소정보 포함 여부", defaultValue = "N")
    private String hstryYn;

    @Schema(name = "firstSort", title = "정확도순 정렬(none), 우선정렬(road: 도로명 포함, location: 지번 포함) ※ keyword(검색어)가 우선정렬 항목에 포함된 결과 우선 표출", defaultValue = "none")
    private String firstSort;
}