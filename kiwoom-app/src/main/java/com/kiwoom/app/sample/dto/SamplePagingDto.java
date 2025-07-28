package com.kiwoom.app.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SamplePagingDto {

    @Schema(name = "userStcd", title = "사용자상태코드", example = "01")
    @NotEmpty
    private String userStcd;
}
