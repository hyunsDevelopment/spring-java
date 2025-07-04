package com.foresys.app2.nice.properties.inner;

import lombok.Data;

@Data
public class NiceServerPart {

    private String serverName;

    private int serverPort10;

    private int serverPort;

    private int serverTimeout;
}
