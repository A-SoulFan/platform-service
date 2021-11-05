package com.asoulfan.asfbbs.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.user.dto.UserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * user DAO
 *
 * @author fengling
 * @since 2021-08-27
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserDto> {

}
