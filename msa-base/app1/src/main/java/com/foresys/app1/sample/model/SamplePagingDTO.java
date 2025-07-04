package com.foresys.app1.sample.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SamplePagingDTO {

    @Schema(name = "pageNum", title = "페이지번호", example = "1")
    @NotNull(message = "페이지 번호를 확인해 주세요.")
    private int pageNum;

    @Schema(name = "pageSize", title = "페이지크기", example = "10")
    @NotNull(message = "페이지 크기를 확인해 주세요.")
    private int pageSize;

    @Schema(name = "navigatePages", title = "탐색페이지개수", example = "10")
    private int navigatePages = 10;

    @Schema(name = "orderBy", title = "order by String", example = "USER_NM DESC")
    private String orderBy;

}
