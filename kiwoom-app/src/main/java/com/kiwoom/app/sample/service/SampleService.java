package com.kiwoom.app.sample.service;

import com.github.pagehelper.PageInfo;
import com.kiwoom.app.fsb.dto.FsbApiDto;
import com.kiwoom.app.fsb.service.FsbClientService;
import com.kiwoom.app.mois.dto.SearchJusoReqDto;
import com.kiwoom.app.mois.dto.SearchJusoResDto;
import com.kiwoom.app.mois.service.MoisClientService;
import com.kiwoom.app.sample.dto.SamplePagingDto;
import com.kiwoom.app.sample.mapper.SampleMapper;
import com.kiwoom.app.session.context.UserContext;
import com.kiwoom.app.session.dto.CustomUserDetails;
import com.kiwoom.app.session.entity.UsrMng;
import com.kiwoom.app.session.repository.UsrMngRepository;
import com.kiwoom.app.system.dto.PageReq;
import com.kiwoom.app.system.dto.PageRes;
import com.kiwoom.app.system.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final UserContext userContext;

    private final StringEncryptor stringEncryptor;

    private final SampleMapper sampleMapper;

    private final FsbClientService fsbClientService;

    private final MoisClientService moisClientService;

    private final UsrMngRepository usrMngRepository;

    public String jasyptEncoding(String params) {
        return stringEncryptor.encrypt(params);
    }

    public String jasyptDecoding(String params) {
        return stringEncryptor.decrypt(params);
    }

    public Map<String, Object> select(String userId) {
        return sampleMapper.getMember(userId);
    }

    public PageRes<?> selectPage(PageReq<SamplePagingDto> params) {
        PageUtil.setPageHelper(params);
        return PageUtil.fromPageInfo(new PageInfo<>(sampleMapper.getMemberList(params.getParams().getUserStcd()), params.getNavigatePages()));
    }

    public PageRes<?> selectPageWithJpa(PageReq<SamplePagingDto> params) {
        // 임시 - entity 필드가 camel case로 되어있어서...(swagger default value 때문에)
        params.setSortColumn(JdbcUtils.convertUnderscoreNameToPropertyName(params.getSortColumn()));

        Pageable pageable = PageUtil.getPageable(params);

        Example<UsrMng> example = Example.of(UsrMng.builder()
                .userStcd(params.getParams().getUserStcd())
                .build());
        return PageUtil.fromPage(usrMngRepository.findAll(example, pageable), params.getNavigatePages());
    }

    public CustomUserDetails getMe() {
        return userContext.getUserDetails();
    }

    @SuppressWarnings("rawtypes")
    public HashMap fsbApi(FsbApiDto params) throws Exception {
        return fsbClientService.executeApi(params.getServiceId(), params.getParameter());
    }

    public SearchJusoResDto addrMois(SearchJusoReqDto params) throws Exception {
        return moisClientService.executeSearchAddr(params);
    }
}
