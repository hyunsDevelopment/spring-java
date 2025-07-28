package com.kiwoom.app.auth.controller;

import com.kiwoom.app.auth.dto.LoginRequestDto;
import com.kiwoom.app.auth.dto.LoginResponseDto;
import com.kiwoom.app.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "login request", description =
            "- 로그인 API 입니다.\n" +
                    "- 추가 로그인 체크 사항 개발 필요"
    )
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto params) {
        return authService.login(params);
    }

    @Operation(summary = "logout request", description =
            "- 로그아웃 API 입니다.\n" +
                    "- 추가 로그아웃 체크 사항 개발 필요"
    )
    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }
}
