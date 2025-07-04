package com.foresys.app2.fulltext.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NetworkInfoVO {

    String ip;

    int port;

    String proxyIp;

    int proxyPort;

    int timeout;

    String charset;

}
