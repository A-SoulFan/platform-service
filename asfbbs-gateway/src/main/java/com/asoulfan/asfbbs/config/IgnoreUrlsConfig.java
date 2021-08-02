package com.asoulfan.asfbbs.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: ASFBBS
 * @description: 网关白名单配置
 * @packagename: com.asoulfan.asfbbs.config
 * @author: Cscar
 * @date: 2021-07-26 11:32
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
