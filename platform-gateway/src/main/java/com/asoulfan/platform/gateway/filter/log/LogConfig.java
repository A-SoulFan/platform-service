package com.asoulfan.platform.gateway.filter.log;

import lombok.Data;

/**
 * @author asuka
 * @date 2021/12/18
 */
@Data
public class LogConfig {
    private boolean logEnable = true;

    private boolean logRoute = true;

    private boolean logUser = true;

    private boolean logRequest = false;

    private boolean logResponse = false;
}
