package com.foresys.app2.nice.model.body.tr800.inner;

import com.foresys.app2.fulltext.annotation.FullTextField;
import lombok.Data;

@Data
public class DATA3 {

    @FullTextField(length = 2)
    private String type;

    @FullTextField(length = 10)
    private String applyType;
}
