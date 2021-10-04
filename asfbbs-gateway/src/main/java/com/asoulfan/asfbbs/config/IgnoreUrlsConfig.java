package com.asoulfan.asfbbs.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网关白名单配置
 *
 * @author Cscar
 * @since 2021-07-26 11:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@RefreshScope
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
