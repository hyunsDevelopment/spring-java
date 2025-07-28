package com.kiwoom.app.system.dto;

import com.kiwoom.app.system.type.SortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageReq <T> {

    @Schema(name = "pageIndex", example = "1")
    @NotNull
    @Min(1)
    private Integer pageIndex;

    @Schema(name = "pageSize", example = "20")
    @NotNull
    @Min(1)
    private Integer pageSize;

    @Schema(name = "navigatePages", example = "10")
    @NotNull
    @Min(1)
    private Integer navigatePages;

    @Schema(name = "sortColumn", title = "정렬컬럼", example = "USER_ID")
    private String sortColumn;

    @Enumerated(EnumType.STRING)
    @Schema(name = "sortOrder", title = "정렬순서", example = "ASC")
    @Builder.Default
    private SortType sortOrder = SortType.ASC;

    @Schema(name = "params", title = "조건 객체")
    @Valid
    private T params;
}
