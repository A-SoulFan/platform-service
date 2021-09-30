package com.asoulfan.asfbbs.admin.dto;

import java.util.Set;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**

 * : 更新角色权限参数

 * @author liurd
 * @since 2021-08-27 23:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResetRolePermissionParam {

    @NotNull
    private Set<Long> permissionIds;

}
