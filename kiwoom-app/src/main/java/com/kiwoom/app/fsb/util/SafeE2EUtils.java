package com.kiwoom.app.fsb.util;

import com.kiwoom.app.system.util.StringUtil;
import eightbyte.safee2e.v2.Safee2eException;
import eightbyte.safee2e.v2.Safee2eServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class SafeE2EUtils {

    private Safee2eServer e2eClient;

    private void setE2eClient() throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        String safee2eFilePath = loader.getResource("classpath:config/fsb").getURI().getPath();    //@TODO : resource path 수정 필요

        if (System.getProperty("safee2e.qualifiedName") == null)
            System.setProperty("safee2e.qualifiedName", "fsb.or.kr");
        if (System.getProperty("safee2e.home") == null)
            System.setProperty("safee2e.home", safee2eFilePath);

        try {
            this.e2eClient = Safee2eServer.client();
        } catch (Safee2eException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setHello(String hello) {
        try {
            setE2eClient();
            this.e2eClient.setHello(hello);
        } catch (IOException | Safee2eException e) {
            log.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    public void setClientPack(HashMap paramMap, String hello, List<NameValuePair> params) throws UnsupportedEncodingException {
        this.setHello(hello);

        JSONParser parser = new JSONParser();
        JSONObject bodyJson = new JSONObject();
        Iterator<String> keys = paramMap.keySet().iterator();
        String key = "";
        String val = "";

        while (keys.hasNext()) {
            key = keys.next();
            val = StringUtil.nvl(paramMap.get(key));

            if (val.contains("[")) {
                try {
                    paramMap.put(key, parser.parse(val));
                } catch (ParseException e) {
                    paramMap.put(key, val);
                }
            }
        }

        String jsonBodyStr = bodyJson.toJSONString(paramMap);
        String encBodyStr = this.setClientPack(jsonBodyStr);

        params.add(new BasicNameValuePair("encData", encBodyStr));
    }

    @SuppressWarnings("unchecked")
    public void setClientUnPack(JSONObject result, String resStr) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray arry = new JSONArray();
        JSONObject encRsObj = (JSONObject) parser.parse(resStr);
        Iterator<String> it = encRsObj.keySet().iterator();
        String key = "";
        String val = "";

        while (it.hasNext()) {
            key = it.next();
            if (key.equals("COMMON_HEAD") || key.equals("attribute")) continue;
            val = encRsObj.get(key).toString();
            val = this.setClientUnPack(val);

            if (val.contains("[") && val.indexOf("[") < 2) {
                try {
                    arry = (JSONArray) parser.parse(val);
                    result.put(key, arry);
                } catch (Exception e) {
                    result.put(key, val);
                }
            } else {
                result.put(key, val);
            }
        }
    }

    private String setClientPack(String ms) throws UnsupportedEncodingException {
        try {
            ms = e2eClient.pack(ms.getBytes(StandardCharsets.UTF_8));
        } catch (Safee2eException e) {
            log.error(e.getMessage(), e);
        }

        return ms;
    }

    private String setClientUnPack(String str) throws Exception {
        try {
            str = new String(e2eClient.unpack(str), StandardCharsets.UTF_8);
            str = new String(str.getBytes("EUC-KR"), "EUC-KR");
        } catch (Safee2eException e) {
            log.error(e.getMessage(), e);
        }

        return str;
    }
}
