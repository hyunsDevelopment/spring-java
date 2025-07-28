package com.kiwoom.app.fsb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class FsbApiDto {

    @Schema(name = "serviceId", title = "서비스ID", example = "API_LogLogn0100_02")
    @NotEmpty(message = "서비스ID를 확인해 주세요.")
    private String serviceId;

    @SuppressWarnings("rawtypes")
    @Schema(name = "parameter", title = "파라미터", example = """
        {
            "FRST_TRNM_CHNL_DVCD": "API",
            "JEX_BANKCODE": "405",
            "USER_ID_12": "qkr900918",
            "SBCD": "405"
        }
        """)
    private HashMap parameter;
}
