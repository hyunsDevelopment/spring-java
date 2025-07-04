package com.kiwoom.app.mois;

import com.kiwoom.app.mois.model.SearchJusoReqDTO;
import com.kiwoom.app.mois.model.SearchJusoResDTO;
import com.kiwoom.app.system.util.ObjectUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class MoisService {

    private final ObjectUtil objectUtil;

    @Value( "${juso.base-url}")
    private String baseUrl;

    @Value( "${juso.api-key}")
    private String jusoKey;

    public SearchJusoResDTO executeSearchAddr(@Valid SearchJusoReqDTO jusoReqVO) throws Exception {
        String uri = String.format(
                "/addrlink/addrLinkApi.do?currentPage=%s&countPerPage=%s&keyword=%s&confmKey=%s&hstryYn=%s&firstSort=%s&resultType=json",
                jusoReqVO.getCurrentPage() != null ? jusoReqVO.getCurrentPage() : "1",
                jusoReqVO.getCountPerPage() != null ? jusoReqVO.getCountPerPage() : "10",
                URLEncoder.encode(jusoReqVO.getKeyword(), StandardCharsets.UTF_8),
                jusoKey,
                jusoReqVO.getHstryYn(),
                jusoReqVO.getFirstSort()
        );

        log.info("주소검색 요청 URL: {}", (baseUrl + uri));

        URL url = new URL(baseUrl + uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try {
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("주소 검색 API 연결 실패 (HTTP " + conn.getResponseCode() + ")");

            try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;

                line = br.readLine();
                while(line != null) {
                    sb.append(line);
                    line = br.readLine();
                }

                String responseStr = sb.toString();
                log.info("주소 검색 API 응답: {}", responseStr);

                return objectUtil.toObject(responseStr, SearchJusoResDTO.class);
            }
        } catch (Exception e) {
            log.error("주소 검색 API 호출 중 오류 발생: {}", e.getMessage(), e);
            throw e;
        } finally {
            conn.disconnect();
        }
    }
}
