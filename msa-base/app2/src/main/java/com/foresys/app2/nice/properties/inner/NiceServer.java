package com.foresys.app2.nice.properties.inner;

import lombok.Data;

@Data
public class NiceServer {

    private String useProxy;

    private String proxyName;

    private int proxyPort;

    private int maxReqCount;

    private NiceServerPart real;

    private NiceServerPart dev;
}
