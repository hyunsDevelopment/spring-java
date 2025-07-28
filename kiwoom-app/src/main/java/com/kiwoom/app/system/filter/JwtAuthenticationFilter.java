package com.kiwoom.app.system.filter;

import com.kiwoom.app.system.config.jwt.JwtProvider;
import com.kiwoom.app.system.dto.TokenUser;
import com.kiwoom.app.system.dto.TokenValid;
import com.kiwoom.app.system.type.TokenType;
import com.kiwoom.app.system.util.HttpSupportUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String[] excludePaths;

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(String[] excludePaths, JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        this.excludePaths = excludePaths;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!HttpSupportUtil.isIncludedPath(excludePaths, request.getRequestURI())) {
            String aToken = HttpSupportUtil.getRequestCookie(jwtProvider.getNamePrefix(TokenType.A), request);
            String rToken = HttpSupportUtil.getRequestCookie(jwtProvider.getNamePrefix(TokenType.R), request);

            TokenValid tokenValid = this.isValid(aToken, rToken);
            if(tokenValid.isValid()) {
                if(tokenValid.getAToken() != null && !tokenValid.getAToken().isEmpty()) {
                    aToken = tokenValid.getAToken();
                    HttpSupportUtil.setResponseCookie(jwtProvider.getNamePrefix(TokenType.A), tokenValid.getAToken(), true, request.isSecure(), "/", -1, response);
                }
                initUserDetails(jwtProvider.decode(aToken).getId());
            }
        }

        filterChain.doFilter(request, response);
    }

    private TokenValid isValid(String aToken, String rToken) {
        String accessToken = "";
        boolean isValid = false;

        boolean isValidAccessToken = jwtProvider.isValid(aToken);
        boolean isValidRefreshToken = jwtProvider.isValid(rToken);

        if(aToken != null && rToken != null) {
            if(isValidAccessToken && isValidRefreshToken) {
                log.info("Access and Refresh 토큰 유효");
                isValid = true;
            }else if(isValidRefreshToken) {
                log.info("Access 토큰 만료 and Refresh 토큰 유효");
                accessToken = jwtProvider.createToken(
                        TokenUser.builder()
                                .id(jwtProvider.decode(rToken).getId())
                                .type("A")
                                .build()
                );
                isValid = true;
            }
        }

        return TokenValid.builder()
                .aToken(accessToken)
                .isValid(isValid)
                .build();
    }

    private void initUserDetails(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
