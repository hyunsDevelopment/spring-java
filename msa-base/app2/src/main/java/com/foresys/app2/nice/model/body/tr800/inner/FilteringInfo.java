package com.foresys.app2.nice.model.body.tr800.inner;

import com.foresys.app2.fulltext.annotation.FullTextField;
import lombok.Data;

@Data
public class FilteringInfo {

    @FullTextField(length = 2)
    private String type;

    @FullTextField(length = 1)
    private String inviteType;

    @FullTextField(length = 14)
    private String code;

    @FullTextField(length = 15)
    private String min;

    @FullTextField(length = 15)
    private String max;

    @FullTextField(length = 15)
    private String value;

    @FullTextField(length = 2)
    private String match;

    @FullTextField(length = 16)
    private String filler1;
}
