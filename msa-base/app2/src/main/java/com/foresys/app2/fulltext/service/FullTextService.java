package com.foresys.app2.fulltext.service;

import com.foresys.app2.fulltext.component.FullTextComponent;
import com.foresys.app2.fulltext.component.SocketComponent;
import com.foresys.app2.fulltext.model.*;
import com.foresys.app2.fulltext.type.DataCipherRange;
import com.foresys.app2.fulltext.type.LenType;
import com.foresys.core.util.PadUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class FullTextService {

    private final FullTextComponent fullTextComponent;

    private final SocketComponent socketComponent;

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @NotNull Object reqBody) throws Exception {
        Object reqHead = fullTextComponent.getReqHead(reqBody);
        Class<?> resHeadClass = fullTextComponent.getResHeadClass(reqBody);
        Class<?> resBodyClass = fullTextComponent.getResBodyClass(reqBody);
        return this.get(networkInfo, null, reqHead, reqBody, resHeadClass, resBodyClass);
    }

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @Valid DataCipherVO dataCipher, @NotNull Object reqBody) throws Exception {
        Object reqHead = fullTextComponent.getReqHead(reqBody);
        Class<?> resHeadClass = fullTextComponent.getResHeadClass(reqBody);
        Class<?> resBodyClass = fullTextComponent.getResBodyClass(reqBody);
        return this.get(networkInfo, dataCipher, reqHead, reqBody, resHeadClass, resBodyClass);
    }

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @NotNull Object reqHead, @NotNull Object reqBody) throws Exception {
        Class<?> resHeadClass = fullTextComponent.getResHeadClass(reqBody);
        Class<?> resBodyClass = fullTextComponent.getResBodyClass(reqBody);
        return this.get(networkInfo, null, reqHead, reqBody, resHeadClass, resBodyClass);
    }

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @Valid DataCipherVO dataCipher, @NotNull Object reqHead, @NotNull Object reqBody) throws Exception {
        Class<?> resHeadClass = fullTextComponent.getResHeadClass(reqBody);
        Class<?> resBodyClass = fullTextComponent.getResBodyClass(reqBody);
        return this.get(networkInfo, dataCipher, reqHead, reqBody, resHeadClass, resBodyClass);
    }

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @NotNull Object reqHead, @NotNull Object reqBody, Class<?> resHeadClass, Class<?> resBodyClass) throws Exception {
        return this.get(networkInfo, null, reqHead, reqBody, resHeadClass, resBodyClass);
    }

    public FullTextResVO get(@Valid NetworkInfoVO networkInfo, @Valid DataCipherVO dataCipher, @NotNull Object reqHead, @NotNull Object reqBody, Class<?> resHeadClass, Class<?> resBodyClass) throws Exception {
        ReqMessageVO reqMessageVO = this.getReqMessage(dataCipher, reqHead, reqBody, networkInfo.getCharset());
        return this.getResMessage(
                dataCipher
                ,this.sendReceive(networkInfo, reqMessageVO)
                ,reqMessageVO.getHeadExceptLen()
                ,reqMessageVO.getBodyExceptLen()
                ,resHeadClass
                ,resBodyClass
                ,networkInfo.getCharset());
    }

    public ReqMessageVO getReqMessage(@Valid DataCipherVO dataCipher, @NotNull Object reqHead, @NotNull Object reqBody, String charset) throws Exception {
        String reqHeadString = "";
        String reqBodyString = "";

        ByteArrayVO reqHeadByteArray = fullTextComponent.getByteArray(reqHead, charset);
        ByteArrayVO reqBodyByteArray = fullTextComponent.getByteArray(reqBody, charset);
        byte[] reqHeadBytes = reqHeadByteArray.getBytes();
        byte[] reqBodyBytes = reqBodyByteArray.getBytes();
        int reqHeadExceptLen = reqHeadByteArray.getExceptLen();
        int reqBodyExceptLen = reqBodyByteArray.getExceptLen();

        reqBodyString = new String(reqBodyBytes, charset);

        if(dataCipher != null && dataCipher.getDataCipherRange() == DataCipherRange.BODY)
            reqBodyBytes = dataCipher.getDataCipher().encrypt(reqBodyBytes);
        int reqHeadBytesLen = reqHeadBytes.length - reqHeadExceptLen;
        int reqBodyBytesLen = reqBodyBytes.length - reqBodyExceptLen;

        int totLenFieldLen = fullTextComponent.getTotLenFieldLen(reqHead);
        int prefixFieldLen = fullTextComponent.getPrefixFieldLen(reqHead);
        for(LenType lenType : LenType.values()) {
            if(lenType == LenType.TOTAL)
                reqHead = fullTextComponent.setLenField(lenType, reqHead, reqHeadBytesLen + reqBodyBytesLen);
            else if(lenType == LenType.HEAD)
                reqHead = fullTextComponent.setLenField(lenType, reqHead, reqHeadBytesLen);
            else if(lenType == LenType.BODY)
                reqHead = fullTextComponent.setLenField(lenType, reqHead, reqBodyBytesLen);
        }
        reqHeadBytes = fullTextComponent.getByteArray(reqHead, charset).getBytes();
        reqHeadString = new String(reqHeadBytes, charset);

        if(totLenFieldLen == 0)
            throw new RuntimeException("Doesn't exist length of the total length field");

        byte[] reqMessage = PadUtil.assemblyBytes(reqHeadBytes, reqBodyBytes);
        if(dataCipher != null && dataCipher.getDataCipherRange() == DataCipherRange.TOTAL)
            reqMessage = dataCipher.getDataCipher().decrypt(reqMessage);

        log.info("REQ>{}", (reqHeadString + reqBodyString));

        return ReqMessageVO.builder()
                .prefixFieldLen(prefixFieldLen)
                .headExceptLen(reqHeadExceptLen)
                .bodyExceptLen(reqBodyExceptLen)
                .totLenFieldLen(totLenFieldLen)
                .message(reqMessage)
                .charset(charset)
                .build();
    }

    public FullTextResVO getResMessage(@Valid DataCipherVO dataCipher, @NotNull byte[] message, int headExceptLen, int bodyExceptLen, Class<?> resHeadClass, Class<?> resBodyClass, String charset) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String resHeadString = "";
        String resBodyString = "";

        Object resHead = (resHeadClass == null || resBodyClass == Void.class) ? null : resHeadClass.getConstructor().newInstance();
        Object resBody = (resBodyClass == null || resBodyClass == Void.class) ? null : resBodyClass.getConstructor().newInstance();
        String rsltCd = "";

        try {
            if(dataCipher != null && dataCipher.getDataCipherRange() == DataCipherRange.TOTAL)
                message = dataCipher.getDataCipher().decrypt(message);

            if(resHead != null) {
                ObjectVO headObj = fullTextComponent.getObject(resHead, message, 0, charset);
                resHead = headObj.getObj();
                rsltCd = fullTextComponent.getRsltCd(resHead);

                int totSize = fullTextComponent.getLenField(LenType.TOTAL, resHead) > 0 ? (fullTextComponent.getLenField(LenType.TOTAL, resHead) + headExceptLen + bodyExceptLen) : message.length;
                int headSize = fullTextComponent.getLenField(LenType.HEAD, resHead) > 0 ? (fullTextComponent.getLenField(LenType.HEAD, resHead) + headExceptLen) : (headObj.getIndex());
                int bodySize = fullTextComponent.getLenField(LenType.BODY, resBody) > 0 ? (fullTextComponent.getLenField(LenType.BODY, resHead) + bodyExceptLen) : (totSize - headSize);

                byte[] tmpHeadBytes = new byte[headSize];
                System.arraycopy(message, 0, tmpHeadBytes, 0, headSize);
                resHeadString = new String(tmpHeadBytes, charset);

                if(resBody != null && bodySize > 0) {
                    byte[] tmpBodyBytes = new byte[bodySize];
                    System.arraycopy(message, headSize, tmpBodyBytes, 0, bodySize);
                    if(dataCipher != null && dataCipher.getDataCipherRange() == DataCipherRange.BODY)
                        tmpBodyBytes = dataCipher.getDataCipher().decrypt(tmpBodyBytes);
                    resBodyString = new String(tmpBodyBytes, charset);
                    resBody = fullTextComponent.getObject(resBody, tmpBodyBytes, 0, charset).getObj();
                    if(rsltCd.isEmpty())
                        rsltCd = fullTextComponent.getRsltCd(resBody);
                }
            }else if(resBody != null) {
                resBody = fullTextComponent.getObject(resBody, message, 0, charset).getObj();
                rsltCd = fullTextComponent.getRsltCd(resBody);
            }
        }catch (Exception e) {
            log.error("# getResMessage :: ", e);
            throw new RuntimeException("Error occurred during parsing. Please check the response object.");
        }

        log.info("RES<{}", (resHeadString + resBodyString));

        return FullTextResVO.builder()
                .head(resHead)
                .body(resBody)
                .rsltCd(rsltCd)
                .build();
    }

    private byte[] sendReceive(@Valid NetworkInfoVO networkInfo, ReqMessageVO reqMessage) throws IOException {
        return socketComponent.sendReceive(
                this.connect(networkInfo)
                ,reqMessage.getMessage()
                ,reqMessage.getPrefixFieldLen()
                ,reqMessage.getTotLenFieldLen()
                ,(reqMessage.getHeadExceptLen() + reqMessage.getBodyExceptLen())
                ,networkInfo.getCharset()
        );
    }

    private Socket connect(@Valid NetworkInfoVO networkInfo) throws IOException {
        return socketComponent.connect(
                networkInfo.getIp()
                ,networkInfo.getPort()
                ,networkInfo.getProxyIp()
                ,networkInfo.getProxyPort()
                ,networkInfo.getTimeout()
        );
    }

}
