package com.asoulfan.asfbbs.admin.dto;

import java.util.Set;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: ASFBBS
 * @description: 更新角色权限参数
 * @packagename: com.asoulfan.asfbbs.admin.dto
 * @author: liurd
 * @date: 2021-08-27 23:10
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ResetRolePermissionParam {

    @NotNull
    private Set<Long> permissionIds;

}
