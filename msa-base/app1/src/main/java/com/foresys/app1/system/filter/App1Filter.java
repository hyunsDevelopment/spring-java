package com.foresys.app1.system.filter;

import com.foresys.app1.system.user.entity.UserEntity;
import com.foresys.app1.system.user.model.UserInfo;
import com.foresys.app1.system.user.repository.UserRepository;
import com.foresys.core.component.jwt.JwtComponent;
import com.foresys.core.model.jwt.TokenType;
import com.foresys.core.model.jwt.TokenUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class App1Filter implements Filter {

    private final UserInfo userInfo;

    private final UserRepository userRepository;

    private final JwtComponent jwtComponent;

    private String accessToken;

    private String refreshToken;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if(this.isValid(httpRequest, httpResponse)) {
            initUser();
        }else {
            String newResponse = "unauthorized";
            httpResponse.setStatus(401);
            httpResponse.setContentType("application/json");
            httpResponse.setContentLength(newResponse.length());
            httpResponse.getOutputStream().write(newResponse.getBytes());
            return;
        }

//        filterChain.doFilter(new XSSFilterWrapper((HttpServletRequest) servletRequest), servletResponse);	//XSS필터 적용 시
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValid(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        if(isExceptionPath(httpRequest.getRequestURI())) return true;

        String aToken = httpRequest.getHeader(jwtComponent.getNamePrefix(TokenType.A));
        String rToken = httpRequest.getHeader(jwtComponent.getNamePrefix(TokenType.R));

        if(aToken != null) {
            if(jwtComponent.isValid(aToken)) {
                log.info("Access 토큰 유효");
                accessToken = aToken;
                refreshToken = rToken;
                return true;
            }else if(rToken != null) {
                log.info("Access 토큰 만료");
                if(jwtComponent.isValid(rToken)) {
                    log.info("Refresh 토큰 유효");
                    accessToken = jwtComponent.createToken(
                            TokenUser.builder()
                                    .id(jwtComponent.decode(rToken).getId())
                                    .type("A")
                                    .build());
                    refreshToken = rToken;
                    httpResponse.setHeader(jwtComponent.getNamePrefix(TokenType.A), accessToken);
                    return true;
                }else {
                    log.info("Refresh 토큰 만료");
                }
            }else {
                log.info("Refresh 토큰 부재");
            }
        }else {
            log.info("Access 토큰 부재");
        }

        return false;
    }

    private boolean isExceptionPath(String path) {
        //TODO
        return path.startsWith("/");
    }

    private void initUser() throws IOException {
        if(accessToken != null && refreshToken != null) {
            try {
                UserEntity userEntity = userRepository.findById(jwtComponent.decode(accessToken).getId()).orElseThrow(() -> new IOException("해당하는 ID가 없습니다."));
                userInfo.setAToken(accessToken);
                userInfo.setRToken(refreshToken);
                userInfo.setUserId(userEntity.getUserId());
            }catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
