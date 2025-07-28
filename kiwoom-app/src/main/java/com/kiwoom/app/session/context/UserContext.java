package com.kiwoom.app.session.context;

import com.kiwoom.app.session.dto.CustomUserDetails;
import com.kiwoom.app.system.exception.BusinessException;
import com.kiwoom.app.system.exception.BusinessExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    public CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new BusinessException(BusinessExceptionCode.UNAUTHORIZED);
        }
        return (CustomUserDetails) auth.getPrincipal();
    }

    public String getMemberNo() {
        return this.getUserDetails().getUsername();
    }

    public String getMemberNm() {
        return this.getUserDetails().getName();
    }

    public String getMemberIp() {
        return this.getUserDetails().getIp();
    }
}
