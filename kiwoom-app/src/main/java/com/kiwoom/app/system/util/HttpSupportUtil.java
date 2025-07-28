package com.kiwoom.app.system.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

public class HttpSupportUtil {

    public static String getRequestCookie(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void setResponseCookie(String key, String value, boolean isHttpOnly, boolean isSecure, String path, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(isHttpOnly);         // 자바스크립트에서 쿠키 접근 차단 (XSS 대응)
        cookie.setSecure(isSecure);             // HTTPS 환경에서만 쿠키 전송 (MITM 방지용)
        cookie.setPath(path);                   // 쿠키가 전송될 URI 경로 (예: "/"는 모든 경로에 전송됨)
        cookie.setMaxAge(maxAge);               // 쿠키의 만료 시간 (초 단위)s
        response.addCookie(cookie);             // 응답에 쿠키 추가
    }

    public static boolean isIncludedPath(String[] paths, String path) {
        return Arrays.stream(paths)
                .anyMatch(exclude -> new AntPathMatcher().match(exclude, path));
    }
}
