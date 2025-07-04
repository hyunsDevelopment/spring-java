package com.foresys.app2.nice.model.header;

import com.foresys.app2.fulltext.annotation.FullTextField;
import com.foresys.app2.fulltext.type.PadType;
import com.foresys.core.util.DateUtil;
import lombok.Data;

@Data
public class Common10Cert {

    @FullTextField(length = 10, padChar = '0', pad = PadType.LEFT, totLenField = true, exceptLenField = true)
    private int totMsgLen;

    @FullTextField(length = 9)
    private String niceId = "NICEIF";

    @FullTextField(length = 4)
    private String msgId = "0200";

    @FullTextField(length = 5)
    private String trnCd;

    @FullTextField(length = 1)
    private String srFlag = "B";

    @FullTextField(length = 3)
    private String terminal = "503";

    @FullTextField(length = 4, rsltCdField = true)
    private String resultCode;

    @FullTextField(length = 9)
    private String userId = "CJBANCHK";

    @FullTextField(length = 10)
    private String sendMsgId = DateUtil.getDateFormatString("MMddHHmmss");

    @FullTextField(length = 14)
    private String sendDate = DateUtil.getDateFormatString("yyyyMMddHHmmss");

    @FullTextField(length = 10)
    private String recvMsgId;

    @FullTextField(length = 14)
    private String recvDate;

    @FullTextField(length = 16)
    private String bitmap;

    @FullTextField(length = 1)
    private String reqAgree = "1";
}
