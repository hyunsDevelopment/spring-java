package com.foresys.app2.nice.model.body.tr800.inner;

import com.foresys.app2.fulltext.annotation.FullTextField;
import lombok.Data;

@Data
public class ScoreInfo {

    @FullTextField(length = 2)
    private String type;

    @FullTextField(length = 2)
    private String result;

    @FullTextField(length = 10)
    private String scoreType;

    @FullTextField(length = 4)
    private String exceptReason1;

    @FullTextField(length = 4)
    private String exceptReason2;

    @FullTextField(length = 4)
    private String exceptReason3;

    @FullTextField(length = 4)
    private String scoreReason1;

    @FullTextField(length = 4)
    private String scoreReason2;

    @FullTextField(length = 4)
    private String scoreReason3;

    @FullTextField(length = 4)
    private String scoreReason4;

    @FullTextField(length = 4)
    private String scoreReason5;

    @FullTextField(length = 4)
    private String scoreReason6;

    @FullTextField(length = 4)
    private String score;

    @FullTextField(length = 4)
    private String grade;

    @FullTextField(length = 9)
    private String reference1;

    @FullTextField(length = 9)
    private String reference2;

    @FullTextField(length = 9)
    private String reference3;

    @FullTextField(length = 4)
    private String errorCode;

    @FullTextField(length = 3)
    private String profileCode1;

    @FullTextField(length = 3)
    private String profileCode2;

    @FullTextField(length = 3)
    private String profileCode3;

    @FullTextField(length = 2)
    private String filler2;
}
