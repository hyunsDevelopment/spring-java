package com.kiwoom.app.mois.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchJusoResDto {
    @Schema(name = "results", title = "검색 결과")
    private Results results;

    @Size(max = 15)
    @Schema(name = "svcReqPk", title = "서비스키")
    private String svcReqPk;

    @Getter
    @Setter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {
        @Schema(name = "common", title = "공통 정보")
        private Common common;

        @Schema(name = "juso", title = "주소 정보 목록")
        private List<Juso> juso;
    }

    @Getter
    @Setter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Common {
        @Schema(name = "totalCount", title = "총 검색 데이터수")
        private String totalCount;

        @Schema(name = "currentPage", title = "페이지 번호")
        private String currentPage;

        @Schema(name = "countPerPage", title = "페이지당 출력할 결과 Row수")
        private String countPerPage;

        @Schema(name = "errorCode", title = "에러 코드")
        private String errorCode;

        @Schema(name = "errorMessage", title = "에러 메시지")
        private String errorMessage;
    }

    @Getter
    @Setter
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Juso {
        @Schema(name = "roadAddr", title = "전체 도로명주소")
        private String roadAddr;

        @Schema(name = "roadAddrPart1", title = "도로명주소(참고항목 제외)")
        private String roadAddrPart1;

        @Schema(name = "roadAddrPart2", title = "도로명주소 참고항목")
        private String roadAddrPart2;

        @Schema(name = "jibunAddr", title = "지번 정보")
        private String jibunAddr;

        @Schema(name = "engAddr", title = "도로명주소(영문)")
        private String engAddr;

        @Schema(name = "zipNo", title = "우편번호")
        private String zipNo;

        @Schema(name = "admCd", title = "행정구역코드")
        private String admCd;

        @Schema(name = "rnMgtSn", title = "도로명코드")
        private String rnMgtSn;

        @Schema(name = "bdMgtSn", title = "건물관리번호")
        private String bdMgtSn;

        @Schema(name = "detBdNmList", title = "상세건물명")
        private String detBdNmList;

        @Schema(name = "bdNm", title = "건물명")
        private String bdNm;

        @Schema(name = "bdKdcd", title = "공동주택여부")
        private String bdKdcd;

        @Schema(name = "siNm", title = "시도명")
        private String siNm;

        @Schema(name = "sggNm", title = "시군구명")
        private String sggNm;

        @Schema(name = "emdNm", title = "읍면동명")
        private String emdNm;

        @Schema(name = "liNm", title = "법정리명")
        private String liNm;

        @Schema(name = "rn", title = "도로명")
        private String rn;

        @Schema(name = "udrtYn", title = "지하여부")
        private String udrtYn;

        @Schema(name = "buldMnnm", title = "건물본번")
        private String buldMnnm;

        @Schema(name = "buldSlno", title = "건물부번")
        private String buldSlno;

        @Schema(name = "mtYn", title = "산여부")
        private String mtYn;

        @Schema(name = "lnbrMnnm", title = "지번본번")
        private String lnbrMnnm;

        @Schema(name = "lnbrSlno", title = "지번부번")
        private String lnbrSlno;

        @Schema(name = "emdNo", title = "읍면동일련번호")
        private String emdNo;

        @Schema(name = "hstryYn", title = "변동이력여부")
        private String hstryYn;

        @Schema(name = "relJibun", title = "관련지번")
        private String relJibun;

        @Schema(name = "hemdNm", title = "관할주민센터")
        private String hemdNm;
    }
}