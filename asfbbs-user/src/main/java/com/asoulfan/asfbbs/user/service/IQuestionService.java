package com.asoulfan.asfbbs.user.service;

import java.util.List;

import com.asoulfan.asfbbs.user.dto.QuestionsVo;
import com.asoulfan.asfbbs.user.dto.ScoreVo;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/

public interface IQuestionService {

    List<QuestionsVo> getList(String username);

    boolean score(ScoreVo vo);
}
