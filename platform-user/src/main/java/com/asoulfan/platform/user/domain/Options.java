package com.asoulfan.platform.user.domain;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-09-05
 **/
@Data
public class Options {

    /**
     * 选项名称
     */
    private String name;

    /**
     * 选项，ABCD
     */
    private String code;
}
