package com.asoulfan.asfbbs.user.domain;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description: 例子
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-09-05
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
