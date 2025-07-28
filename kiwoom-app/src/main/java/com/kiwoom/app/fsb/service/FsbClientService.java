package com.kiwoom.app.fsb.service;

import com.kiwoom.app.fsb.dto.FsbApiToken;
import com.kiwoom.app.fsb.exception.FsbApiException;
import com.kiwoom.app.fsb.util.SafeE2EUtils;
import com.kiwoom.app.system.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class FsbClientService {

    private final int timeoutInt = 1200000;
    private final int apiRetryMaxCount = 5;

    @Value("${FSB_API_URL}")
    private String apiUrl;

    @Value("${FSB_API_CLIENT_ID}")
    private String clientId;

    @Value("${FSB_API_CLIENT_SECRET}")
    private String clientSecret;

    @Getter
    @Setter
    @Value("${FSB_API_RETRYCOUNT}")
    private int apiRetryCount;

    @SuppressWarnings("rawtypes")
    public HashMap executeApi(String serviceId, HashMap paramsMap) throws Exception {
        return executeApi(serviceId, paramsMap, false);
    }

    @SuppressWarnings("rawtypes")
    private HashMap executeApi(String serviceId, HashMap paramsMap, boolean retryFlag) throws Exception {
        HashMap resultMap = new HashMap();

        if (retryFlag)
            this.setApiRetryCount(this.getApiRetryCount() + 1);
        else
            this.setApiRetryCount(0);

        log.info("===================================================================");
        log.info("API.URL: {}", apiUrl);
        log.info("API.CLIENT.ID: {}", clientId);
        log.info("API.CLIENT.SECRET: {}", clientSecret);
        log.info("API.RETRYCOUNT: {}", apiRetryCount);
        log.info("paramsMap: {}", paramsMap);
        log.info("===================================================================");

        try (CloseableHttpClient httpClient = getFsbApiHttpClient()) {
            getApiAccessToken(httpClient);
            getE2EPublicKey(httpClient);

            HashMap apiResponseMap = callApi(httpClient, serviceId, paramsMap);
            resultMap.put("rsCode", "200");
            resultMap.put("rsMsg", "success");
            resultMap.put("rsData", apiResponseMap);
        } catch (FsbApiException e) {
            log.error("### FsbApiException: ", e);

            if (102 == e.getErrorCode() && apiRetryCount < apiRetryMaxCount) {
                FsbApiToken fsbApiToken = FsbApiToken.getInstance();
                fsbApiToken.setTokenValue("");
                System.setProperty("AUTHORIZATION", "");
                System.setProperty("SAFEE2E.HELLO.KEY", "");
                return executeApi(serviceId, paramsMap, true);
            } else {
                resultMap.put("rsCode", e.getErrorCode());
                resultMap.put("rsMsg", e.getMessage());
                return resultMap;
            }
        } finally {
            log.info("===================================================================");
            log.info("resultMap: {}", resultMap);
            log.info("===================================================================");
        }

        return resultMap;
    }

    public CloseableHttpClient getFsbApiHttpClient() throws Exception {
        System.setProperty("https.protocols", "TLSv1.2");
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeoutInt)
                .setSocketTimeout(timeoutInt)
                .setConnectTimeout(timeoutInt)
                .build();
        TrustStrategy acceptingTrustStrategy = new TrustSelfSignedStrategy();
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

        return HttpClients.custom()
                .setSSLSocketFactory(csf)
                .setDefaultRequestConfig(reqConfig)
                .build();
    }

    public void getApiAccessToken(CloseableHttpClient httpClient) throws Exception {
        FsbApiToken fsbApiToken = FsbApiToken.getInstance();
        String accessTokenValue = fsbApiToken.getTokenValue();

        if (accessTokenValue != null && !accessTokenValue.isEmpty()) {
            log.info("### API accessToken is not empty value: {}", accessTokenValue);
            return;
        }

        try {
            HttpUriRequest request = RequestBuilder.post(apiUrl + "/oauth2/token")
                    .setHeader("content-type", "application/x-www-form-urlencoded")
                    .addParameter("client_id", clientId).addParameter("client_secret", clientSecret)
                    .addParameter("grant_type", "client_credentials").addParameter("scope", "apis")
                    .build();

            CloseableHttpResponse response = httpClient.execute(request);
            String resStr = EntityUtils.toString(response.getEntity());

            log.info("### getToken: {}", resStr);

            JSONParser parser = new JSONParser();
            JSONObject tokenObj = (JSONObject) parser.parse(resStr);
            String tokenType = tokenObj.get("token_type").toString();
            String tokenValue = tokenObj.get("access_token").toString();
            accessTokenValue = tokenType + " " + tokenValue;
            fsbApiToken.setTokenValue(accessTokenValue);
        } catch (Exception e) {
            log.error("### getApiAccessToken Exception: ", e);
            fsbApiToken.setTokenValue("");
            throw new FsbApiException(100, "Access Token 발급 중 오류가 발생했습니다."); //에러코드 상수화 필요
        }
    }

    public void getE2EPublicKey(CloseableHttpClient httpClient) throws Exception {
        String e2ePublicKey = StringUtil.nvl(System.getProperty("SAFEE2E.HELLO.KEY"));
        FsbApiToken fsbApiToken = FsbApiToken.getInstance();

        if (!e2ePublicKey.isEmpty()) {
            log.info("### API e2ePublicKey is not empty value: {}", e2ePublicKey);
            return;
        }

        try {
            HttpUriRequest request = RequestBuilder.post(apiUrl + "/API-CMMN?id=API_PubKeyIssu")
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setHeader("x-ibm-client-id", clientId)
                    .setHeader("Authorization", fsbApiToken.getTokenValue())
                    .setHeader("accept", "application/json")
                    .build();
            CloseableHttpResponse response = httpClient.execute(request);
            JSONParser parser = new JSONParser();
            String resStr = EntityUtils.toString(response.getEntity());

            log.info("### getPubKeyReq: {}", resStr);

            JSONObject rsObj = (JSONObject) parser.parse(resStr);
            e2ePublicKey = StringUtil.nvl(rsObj.get("PUB_KEY"));

            if (StringUtil.nvl(rsObj.get("httpCode")).equals("500") || StringUtil.nvl(rsObj.get("httpCode")).equals("404"))
                throw new FsbApiException(101, "암호화 공유키 발급 중 오류가 발생했습니다.(stop)");

            if (e2ePublicKey.isEmpty()) {
                if (apiRetryCount < apiRetryMaxCount) {
                    log.info("### recall token try count: {}", apiRetryCount);
                    apiRetryCount++;
                    fsbApiToken.setTokenValue("");
                    getApiAccessToken(httpClient);
                    getE2EPublicKey(httpClient);
                } else {
                    throw new FsbApiException(101, "암호화 공유키 발급 중 오류가 발생했습니다.(stop)");
                }
            } else {
                System.setProperty("SAFEE2E.HELLO.KEY", e2ePublicKey);
            }
        } catch (FsbApiException e) {
            log.error("### getE2EPublicKey Exception: ", e);
            fsbApiToken.setTokenValue("");
            throw new FsbApiException(e.getErrorCode(), e.getMessage()); //에러코드 상수화 필요
        }
    }

    @SuppressWarnings("rawtypes")
    public HashMap callApi(CloseableHttpClient httpClient, String serviceId, HashMap paramMap) throws Exception {
        JSONObject result = new JSONObject();

        try {
            FsbApiToken fsbApiToken = FsbApiToken.getInstance();
            String accessToken = fsbApiToken.getTokenValue();
            String e2ePublicKey = System.getProperty("SAFEE2E.HELLO.KEY");
            String btId = StringUtil.nvl(serviceId);

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // E2E Encryption
            SafeE2EUtils e2eUtils = new SafeE2EUtils();
            log.info("### callAPi request: {}", paramMap);
            e2eUtils.setClientPack(paramMap, e2ePublicKey, params);

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            HttpUriRequest request = RequestBuilder
                    .post(apiUrl + "/API-CMMN?id=" + btId)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setHeader("x-ibm-client-id", clientId)
                    .setHeader("Authorization", accessToken)
                    .setHeader("accept", "application/json")
                    .setEntity(entity)
                    .build();

            CloseableHttpResponse response = httpClient.execute(request);
            String resStr = EntityUtils.toString(response.getEntity());
            response.close();
            log.info("### getApiProgression: {}", resStr);

            if (!resStr.isEmpty()) {
                JSONParser parser = new JSONParser();
                org.json.simple.JSONObject encRsObj = (org.json.simple.JSONObject) parser.parse(resStr);

                if (StringUtil.nvl(encRsObj.get("httpCode")).equals("401")) {
                    if (apiRetryCount < apiRetryMaxCount) {
                        log.error("401 Unauthorized");
                        fsbApiToken.setTokenValue("");
                        System.setProperty("AUTHORIZATION", "");
                        System.setProperty("SAFEE2E.HELLO.KEY", "");
                        return executeApi(serviceId, paramMap, true);
                    } else {
                        throw new FsbApiException(102, "API 접속중 오류가 발생했습니다.");
                    }
                }
            } else {
                throw new FsbApiException(103, "API 수신 메시지가 없습니다.");
            }

            e2eUtils.setClientUnPack(result, resStr);
            log.info("### getApiProgressionPlain: {}", result);
        } catch (FsbApiException e) {
            log.error("### getApiProgression Exception: ", e);
            throw new FsbApiException(e.getErrorCode(), e.getMessage());
        }

        return result;
    }
}