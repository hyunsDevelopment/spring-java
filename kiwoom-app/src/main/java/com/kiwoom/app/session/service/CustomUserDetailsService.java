package com.kiwoom.app.session.service;

import com.kiwoom.app.session.dto.CustomUserDetails;
import com.kiwoom.app.session.entity.UsrMng;
import com.kiwoom.app.session.repository.UsrMngRepository;
import com.kiwoom.app.system.exception.BusinessException;
import com.kiwoom.app.system.exception.BusinessExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsrMngRepository usrMngRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsrMng userDetails = usrMngRepository.findById(username).orElseThrow(() ->
                new BusinessException(BusinessExceptionCode.USER_NOT_FOUND)
        );

        // TODO 사용자에 대한 권한 설정
        // 메서드 앞에 @PreAuthorize("hasAuthority('A9')") 와 같은 방식으로 사용가능
//        List<GrantedAuthority> authorities = Collections.singletonList(
//                new SimpleGrantedAuthority(userDetails.getRoleCd())
//        );
        List<GrantedAuthority> authorities = null;

        return new CustomUserDetails(
                userDetails.getUserId(),
                userDetails.getPwd(),
                userDetails.getUserNm(),
                userDetails.getConIp(),
                authorities
        );
    }
}
