package com.foresys.app2.nice.properties;

import com.foresys.app2.nice.properties.inner.NiceEncode;
import com.foresys.app2.nice.properties.inner.NiceServer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("nice")
public class NiceProperties {

    private NiceServer server;

    private NiceEncode encode;
}
