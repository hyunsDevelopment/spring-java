package com.kiwoom.app.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @Schema(name = "userId", title = "사용자_ID", example = "12312355")
    @NotEmpty
    private String userId;

    @Schema(name = "password", title = "비밀번호", example = "1234")
    @NotEmpty
    private String password;
}
