package com.foresys.app2.nice.model.header;

import com.foresys.app2.fulltext.annotation.FullTextField;
import com.foresys.app2.fulltext.type.PadType;
import com.foresys.core.util.DateUtil;
import lombok.Data;

@Data
public class Common10 {

    /**
     * 총전문 길이
     */
    @FullTextField(length = 10, padChar = '0', pad = PadType.LEFT, totLenField = true, exceptLenField = true)
    private int totMsgLen;

    /**
     * 회원사코드
     */
    @FullTextField(length = 9)
    private String niceId = "NICEIF";

    /**
     * 전문구분
     */
    @FullTextField(length = 4)
    private String msgId = "0200";

    /**
     * TR 코드
     */
    @FullTextField(length = 5)
    private String trnCd;

    /**
     * 송수신 Flag
     */
    @FullTextField(length = 1)
    private String srFlag = "B";

    /**
     * 단말기 구분
     */
    @FullTextField(length = 3)
    private String terminal = "503";

    /**
     * 응답코드
     */
    @FullTextField(length = 4, rsltCdField = true)
    private String resultCode;

    /**
     * 나이스가 부여한 ID
     */
    @FullTextField(length = 9)
    private String userId = "CJSB01";

    /**
     * 전문거래고유번호
     */
    @FullTextField(length = 10)
    private String sendMsgId = DateUtil.getDateFormatString("MMddHHmmss");

    /**
     * 전문요청발생시간
     */
    @FullTextField(length = 14)
    private String sendDate = DateUtil.getDateFormatString("yyyyMMddHHmmss");

    /**
     * 전문추적번호
     */
    @FullTextField(length = 10)
    private String recvMsgId;

    /**
     * 전문응답발생시간
     */
    @FullTextField(length = 14)
    private String recvDate;

    /**
     * Primary Bitmap
     */
    @FullTextField(length = 16)
    private String bitmap;

    /**
     * 조회동의사유코드
     */
    @FullTextField(length = 1)
    private String reqAgree = "1";
}
