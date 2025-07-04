package com.foresys.app2.nice.model.body.tr800;

import com.foresys.app2.fulltext.annotation.FullTextField;
import com.foresys.app2.fulltext.annotation.FullTextInfo;
import com.foresys.app2.fulltext.annotation.HeadValue;
import com.foresys.app2.fulltext.type.PadType;
import com.foresys.app2.nice.model.body.tr800.inner.DATA1;
import com.foresys.app2.nice.model.body.tr800.inner.DATA2;
import com.foresys.app2.nice.model.body.tr800.inner.DATA3;
import com.foresys.app2.nice.model.header.Common10;
import lombok.Data;

import java.util.List;

@Data
@FullTextInfo(
    reqHeadClass = Common10.class
    ,resHeadClass = Common10.class
    ,resBodyClass = VsTR800.class
    ,headValues = {
        @HeadValue(name = "trnCd", value = "TR800")
    }
)
public class VrTR800 {

    @FullTextField(length = 13)
    private String regNo;

    @FullTextField(length = 2)
    private String applyReason;

    @FullTextField(length = 8, padChar = '0', pad = PadType.LEFT)
    private String svcCode;

    @FullTextField(length = 12)
    private String certNum;

    @FullTextField(length = 8)
    private String applyDate;

    @FullTextField(length = 2, padChar = '0', pad = PadType.LEFT)
    private String addReqCnt;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT, tgtListFieldName = "data1")
    private int applyCnt1;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT, tgtListFieldName = "data2")
    private int applyCnt2;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT)
    private int filteringResCnt;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT)
    private int filteringReqCnt = 999;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT)
    private int assResCnt;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT)
    private int assReqCnt = 999;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT)
    private int scoreResCnt;

    @FullTextField(length = 3, padChar = '0', pad = PadType.LEFT, tgtListFieldName = "data3")
    private int scoreReqCnt;

    @FullTextField(length = 31)
    private String filler;

    private List<DATA1> data1;

    private List<DATA2> data2;

    private List<DATA3> data3;
}
