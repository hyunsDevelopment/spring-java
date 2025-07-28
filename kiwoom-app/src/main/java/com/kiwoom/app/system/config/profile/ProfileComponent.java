package com.kiwoom.app.system.config.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ProfileComponent {

    private final Environment environment;

    public boolean isLocal() {
        return Arrays.asList(environment.getActiveProfiles()).contains("local");
    }

    public boolean isDev() {
        return Arrays.asList(environment.getActiveProfiles()).contains("dev");
    }

    public boolean isProd() {
        return Arrays.asList(environment.getActiveProfiles()).contains("prod");
    }

    public boolean isLocalOrDev() {
        return Arrays.stream(environment.getActiveProfiles())
                .anyMatch(p -> p.equals("local") || p.equals("dev"));
    }

    public boolean isActiveProfile(String profile) {
        return Arrays.asList(environment.getActiveProfiles()).contains(profile);
    }
}
