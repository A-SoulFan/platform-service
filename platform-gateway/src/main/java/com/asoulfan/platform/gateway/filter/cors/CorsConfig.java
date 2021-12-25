package com.asoulfan.platform.gateway.filter.cors;

import java.util.Collections;
import java.util.Set;

import lombok.Data;

/**
 * @author asuka
 * @date 2021/12/25
 */
@Data
public class CorsConfig {
    private Set<String> allowOrigin = Collections.singleton("*");

    private Set<String> allowHeader = Collections.singleton("*");

    private Set<String> allowMethod = Collections.singleton("*");

    private String allowCredentials = "true";
}
