package com.foresys.app2.nice.service;

import com.foresys.app2.fulltext.model.FullTextResVO;
import com.foresys.app2.fulltext.model.NetworkInfoVO;
import com.foresys.app2.fulltext.service.FullTextService;
import com.foresys.app2.nice.properties.NiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NiceService {

    private final NiceProperties niceProperties;

    private final FullTextService fullTextService;

    public FullTextResVO get(Object reqObj) throws Exception {
        return get(reqObj, false);
    }

    public FullTextResVO get(Object reqObj, boolean isTest) throws Exception {
        return fullTextService.get(
                NetworkInfoVO.builder()
                        .ip(isTest ? niceProperties.getServer().getDev().getServerName() : niceProperties.getServer().getReal().getServerName())
                        .port(isTest ? niceProperties.getServer().getDev().getServerPort10() : niceProperties.getServer().getReal().getServerPort10())
                        .proxyIp("Y".equals(niceProperties.getServer().getUseProxy()) ? niceProperties.getServer().getProxyName() : null)
                        .proxyPort("Y".equals(niceProperties.getServer().getUseProxy()) ? niceProperties.getServer().getProxyPort() : 0)
                        .timeout(isTest ? niceProperties.getServer().getDev().getServerTimeout() : niceProperties.getServer().getReal().getServerTimeout())
                        .charset(niceProperties.getEncode().getCharSet())
                        .build()
                ,reqObj);
    }
}
