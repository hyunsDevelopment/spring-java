package com.foresys.app2.nice.model.body.tr800;

import com.foresys.app2.fulltext.annotation.FullTextField;
import com.foresys.app2.nice.model.body.tr800.inner.AssInfo;
import com.foresys.app2.nice.model.body.tr800.inner.FilteringInfo;
import com.foresys.app2.nice.model.body.tr800.inner.ScoreInfo;
import lombok.Data;

import java.util.List;

@Data
public class VsTR800 {

    @FullTextField(length = 13)
    private String regNo;

    @FullTextField(length = 8)
    private String applyDate;

    @FullTextField(length = 12)
    private String recvNo;

    @FullTextField(length = 20)
    private String name;

    @FullTextField(length = 1)
    private String infoScsYn;

    @FullTextField(length = 2)
    private int addReqCnt;

    @FullTextField(length = 1)
    private String prevResult;

    @FullTextField(length = 1)
    private String finalResult;

    @FullTextField(length = 50)
    private String finalResultMsg;

    @FullTextField(length = 1)
    private String strResult;

    @FullTextField(length = 13)
    private String strAmt;

    @FullTextField(length = 7)
    private String strInterest;

    @FullTextField(length = 7)
    private String strLtv;

    @FullTextField(length = 4)
    private String score1;

    @FullTextField(length = 4)
    private String grade1;

    @FullTextField(length = 4)
    private String score2;

    @FullTextField(length = 4)
    private String grade2;

    @FullTextField(length = 4)
    private String assScore;

    @FullTextField(length = 4)
    private String assGrade;

    @FullTextField(length = 8)
    private String svcCode;

    @FullTextField(length = 3)
    private int filteringResTotCnt;

    @FullTextField(length = 3, tgtListFieldName = "filteringInfo")
    private int filteringResCnt;

    @FullTextField(length = 3)
    private int filteringResCmsCnt;

    @FullTextField(length = 3)
    private int assResTotCnt;

    @FullTextField(length = 3, tgtListFieldName = "assInfo")
    private int assResCnt;

    @FullTextField(length = 3)
    private int assResCmsCnt;

    @FullTextField(length = 3)
    private int scoreResTotCnt;

    @FullTextField(length = 3, tgtListFieldName = "scoreInfo")
    private int scoreResCnt;

    @FullTextField(length = 3)
    private int scoreResCmsCnt;

    @FullTextField(length = 55)
    private String filler;

    List<FilteringInfo> filteringInfo;

    List<AssInfo> assInfo;

    List<ScoreInfo> scoreInfo;
}
