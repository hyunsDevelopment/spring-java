package com.foresys.app2.nice.model.body.tr800.inner;

import com.foresys.app2.fulltext.annotation.FullTextField;
import lombok.Data;

@Data
public class DATA1 {

    @FullTextField(length = 2)
    private String type;

    @FullTextField(length = 2)
    private String applyType;

    @FullTextField(length = 20)
    private String applyDesc;

    @FullTextField(length = 6)
    private String filler;
}
