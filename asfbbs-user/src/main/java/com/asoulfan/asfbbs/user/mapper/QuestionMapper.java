package com.asoulfan.asfbbs.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.user.dto.QuestionDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Mapper
public interface QuestionMapper extends BaseMapper<QuestionDto> {

}
