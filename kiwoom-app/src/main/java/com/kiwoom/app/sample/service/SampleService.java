package com.kiwoom.app.sample.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kiwoom.app.fsb.FsbClient;
import com.kiwoom.app.fsb.model.FsbApiDTO;
import com.kiwoom.app.mois.MoisService;
import com.kiwoom.app.mois.model.SearchJusoReqDTO;
import com.kiwoom.app.mois.model.SearchJusoResDTO;
import com.kiwoom.app.sample.mapper.SampleMapper;
import com.kiwoom.app.sample.model.SamplePagingDTO;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final StringEncryptor stringEncryptor;

    private final SampleMapper sampleMapper;

    private final FsbClient fsbClient;

    private final MoisService moisService;

    public String jasyptEncoding(String params) {
        return stringEncryptor.encrypt(params);
    }

    public String jasyptDecoding(String params) {
        return stringEncryptor.decrypt(params);
    }

    public List<Map<String, Object>> select() {
        return sampleMapper.getMemberList();
    }

    public PageInfo<Map<String, Object>> selectPage(SamplePagingDTO params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize(), params.getOrderBy());
        return new PageInfo<>(sampleMapper.getMemberList(), params.getNavigatePages());
    }

    @SuppressWarnings("rawtypes")
    public HashMap fsbApi(FsbApiDTO params) throws Exception {
        return fsbClient.executeApi(params.getServiceId(), params.getParameter());
    }

    public SearchJusoResDTO addrMois(SearchJusoReqDTO params) throws Exception {
        return moisService.executeSearchAddr(params);
    }
}
