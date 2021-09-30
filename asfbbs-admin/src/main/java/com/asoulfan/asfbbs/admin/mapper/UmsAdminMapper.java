package com.asoulfan.asfbbs.admin.mapper;

import com.asoulfan.asfbbs.admin.domain.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**

 * :

 * @author Cscar
 * @since 2021-08-01 03:12
 */
@Mapper
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    UmsAdmin selectByUsername(String username);
}
