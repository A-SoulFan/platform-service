package com.asoulfan.asfbbs.admin.dto;

import java.util.Set;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**

 * : 更新用户角色参数

 * @author liurd
 * @since 2021-08-26 18:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResetRoleParam {

    @NotNull
    private Set<Long> roleIds;

}
