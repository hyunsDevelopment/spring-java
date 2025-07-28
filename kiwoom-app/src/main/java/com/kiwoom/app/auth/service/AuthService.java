package com.kiwoom.app.auth.service;

import com.kiwoom.app.auth.dto.LoginRequestDto;
import com.kiwoom.app.auth.dto.LoginResponseDto;
import com.kiwoom.app.auth.type.AuthAction;
import com.kiwoom.app.session.context.UserContext;
import com.kiwoom.app.session.entity.UsrMng;
import com.kiwoom.app.session.repository.UsrMngRepository;
import com.kiwoom.app.system.config.jwt.JwtProvider;
import com.kiwoom.app.system.dto.TokenUser;
import com.kiwoom.app.system.exception.BusinessException;
import com.kiwoom.app.system.exception.BusinessExceptionCode;
import com.kiwoom.app.system.type.TokenType;
import com.kiwoom.app.system.util.HttpSupportUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserContext userContext;

    private final JwtProvider jwtProvider;

    private final UsrMngRepository usrMngRepository;

    public LoginResponseDto login(LoginRequestDto params) {
        UsrMng user = usrMngRepository.findByUserIdAndPwd(params.getUserId(), params.getPassword()).orElseThrow(() ->
                new BusinessException(BusinessExceptionCode.USER_NOT_FOUND)
        );

        // TODO 로그인 추가 로직

        // response cookie 토큰 세팅
        this.setTokenToResponseCookie(user.getUserId(), AuthAction.LOGIN);

        return LoginResponseDto.builder()
                .passwordChangeRequired("N")
                .build();
    }

    public void logout() {
        String userId = userContext.getMemberNo();

        // TODO 로그아웃 추가 로직

        // response cookie 토큰 세팅
        this.setTokenToResponseCookie(null, AuthAction.LOGOUT);
    }

    private void setTokenToResponseCookie(String userId, AuthAction authAction) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.currentRequestAttributes())).getResponse();

        String accessToken = authAction == AuthAction.LOGIN ? jwtProvider.createToken(TokenUser.builder().id(userId).type("A").build()) : null;
        String refreshToken = authAction == AuthAction.LOGIN ? jwtProvider.createToken(TokenUser.builder().id(userId).type("R").build()) : null;

        boolean isSecure = request.isSecure();
        int maxAge = authAction == AuthAction.LOGIN ? -1 : 0;

        HttpSupportUtil.setResponseCookie(jwtProvider.getNamePrefix(TokenType.A), accessToken, true, isSecure, "/", maxAge, response);
        HttpSupportUtil.setResponseCookie(jwtProvider.getNamePrefix(TokenType.R), refreshToken, true, isSecure, "/", maxAge, response);
    }
}
