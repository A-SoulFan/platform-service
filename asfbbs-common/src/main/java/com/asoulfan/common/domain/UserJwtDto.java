package com.asoulfan.common.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-10-03
 **/
@Data
public class UserJwtDto {

    @JsonProperty("user_name")
    private String userName;

    private List<String> scope;

    private long id;

    private long exp;

    private List<String> authorities;

    private String jti;

    @JsonProperty("client_id")
    private String clientId;
}
