package com.foresys.core.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ComRes <T> {

    @Schema(description = "응답 코드", nullable = false, defaultValue = "0")
    @Builder.Default
    private int rsltCd = 0;

    @Schema(description = "응답 메세지", nullable = false, defaultValue = "정상처리 되었습니다.")
    @Builder.Default
    private String rsltMsg = "정상처리 되었습니다.";

    @Schema(description = "data 응답부")
    private T data;

    public ComRes(T obj) {
        this.rsltCd = 0;
        this.rsltMsg = "정상처리 되었습니다.";
        this.data = obj;
    }

    public ComRes(int rsltCd, String rsltMsg, T obj) {
        this.rsltCd = rsltCd;
        this.rsltMsg = rsltMsg;
        this.data = obj;
    }

}
