package com.asoulfan.platform.user.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-10-03
 **/
@Data
public class Permission implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    private String componentName;

    /**
     * 路径
     */
    private String url;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 是否叶子节点: 1:是  0:不是
     */
    private boolean isLeaf;

    /**
     * 描述
     */
    private String description;

}
