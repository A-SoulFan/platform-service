package com.asoulfan.platform.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.platform.user.dto.QuestionDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Mapper
public interface QuestionMapper extends BaseMapper<QuestionDto> {

}
