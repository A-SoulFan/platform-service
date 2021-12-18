package com.asoulfan.platform.user.service;

import java.util.List;

import com.asoulfan.platform.user.dto.QuestionsVo;
import com.asoulfan.platform.user.dto.ScoreVo;

/**
 * @author fengling
 * @since 2021-08-28
 **/

public interface IQuestionService {

    List<QuestionsVo> getList(String username);

    boolean score(ScoreVo vo);
}
