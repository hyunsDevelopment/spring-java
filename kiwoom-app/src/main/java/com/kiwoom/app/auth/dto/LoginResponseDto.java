package com.kiwoom.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    @Schema(name = "passwordChangeRequired", title = "비밀번호 변경 필요 여부", example = "")
    private String passwordChangeRequired;
}
