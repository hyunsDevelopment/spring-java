package com.kiwoom.app.system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValid {

    private String aToken;

    private boolean isValid;
}
