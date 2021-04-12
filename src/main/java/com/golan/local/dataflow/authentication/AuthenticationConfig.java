package com.golan.local.dataflow.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationConfig {
    public static final String INTERNAL_TOKEN_HEADER = "X-Internal-Token";
    private boolean enabled;
    private String internalToken;
}
