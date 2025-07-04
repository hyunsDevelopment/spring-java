package com.foresys.app2.fulltext.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqMessageVO {

    private int prefixFieldLen;

    private int headExceptLen;

    private int bodyExceptLen;

    private int totLenFieldLen;

    private byte[] message;

    private String charset;

}
