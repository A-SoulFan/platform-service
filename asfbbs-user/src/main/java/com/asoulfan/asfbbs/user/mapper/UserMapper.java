package com.asoulfan.asfbbs.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.user.dto.UserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @program: ASFBBS
 * @description: user DAO
 * @packagename: com.asoulfan.asfbbs.user.mapper
 * @author: fengling
 * @date: 2021-08-27
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserDto> {

}
