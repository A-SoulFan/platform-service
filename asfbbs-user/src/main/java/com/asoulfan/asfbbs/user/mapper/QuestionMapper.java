package com.asoulfan.asfbbs.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.user.dto.QuestionDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Mapper
public interface QuestionMapper extends BaseMapper<QuestionDto> {

}
