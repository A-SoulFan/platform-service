package com.asoulfan.platform.common.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author asuka
 * @date 2021/10/02
 */
public class SuccessWithExtraInfoResult<T> extends CommonResult<T> {
    @Getter
    @Setter
    private List<ExtraInfo> extraInfo = new ArrayList<>();

    public SuccessWithExtraInfoResult(T data) {
        super(ResultCode.SUCCESS_THEN_STORE_INFO.getCode(), ResultCode.SUCCESS_THEN_STORE_INFO.getMessage(), data);
    }

    public SuccessWithExtraInfoResult<T> addExtraInfo(String key, Object value) {
        extraInfo.add(new ExtraInfo(key, value));
        return this;
    }

    public SuccessWithExtraInfoResult<T> addExtraInfo(String key, Object value, long ttlSeconds) {
        ExtraInfo extraInfo = new ExtraInfo(key, value);
        extraInfo.setTtlSeconds(ttlSeconds);
        this.extraInfo.add(extraInfo);
        return this;
    }

    public SuccessWithExtraInfoResult<T> addExtraInfo(String key, Object value, long ttlSeconds, Map<String, String> properties) {
        ExtraInfo extraInfo = new ExtraInfo(key, value);
        extraInfo.setTtlSeconds(ttlSeconds);
        extraInfo.setProperties(properties);
        this.extraInfo.add(extraInfo);
        return this;
    }

    @Data
    public static class ExtraInfo {
        @NonNull
        private String key;

        @NonNull
        private Object value;

        /**
         * 过期时间，单位为秒
         */
        private long ttlSeconds = -1;

        /**
         * 拓展属性
         */
        private Map<String, String> properties = new HashMap<>();
    }
}
