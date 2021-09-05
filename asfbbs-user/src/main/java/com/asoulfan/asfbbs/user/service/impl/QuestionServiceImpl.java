package com.asoulfan.asfbbs.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.asoulfan.asfbbs.constant.UserConstant;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.domain.Answer;
import com.asoulfan.asfbbs.user.domain.Options;
import com.asoulfan.asfbbs.user.dto.QuestionDto;
import com.asoulfan.asfbbs.user.dto.QuestionsVo;
import com.asoulfan.asfbbs.user.dto.ScoreVo;
import com.asoulfan.asfbbs.user.mapper.QuestionMapper;
import com.asoulfan.asfbbs.user.service.IQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionsVo> getList(String username) {
        // Object o = redisTemplate.opsForValue().get(UserConstant.REGISTER_REDIS_KEY + username);
        // if (o == null || !"1".equals(o.toString())) {
        //     Asserts.fail("已超过答题时限，本次注册失败");
        // }
        //TODO:设计随机生成题库算法
        List<QuestionDto> questionList = questionMapper.selectList(new QueryWrapper<>());
        List<QuestionsVo> vos = new ArrayList<>();
        questionList.forEach(a -> {
            List<Options> options = JSONUtil.toList(a.getOptions(), Options.class);
            QuestionsVo vo = new QuestionsVo();
            vo.setQuestion(a.getQuestion());
            vo.setId(a.getId());
            vo.setOptions(options);
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public boolean score(ScoreVo vo) {
        Object o = redisTemplate.opsForValue().get(UserConstant.REGISTER_REDIS_KEY + vo.getUsername());
        if (o == null || !"1".equals(o.toString())) {
            Asserts.fail("已超过答题时限，本次注册失败");
        }
        //TODO: 1.判断是否是同一套题
        List<QuestionDto> questions = questionMapper.selectList(
                new QueryWrapper<QuestionDto>()
                        .in("id", vo.getAnswers().stream().map(Answer::getId).collect(Collectors.toList())));
        AtomicInteger num = new AtomicInteger();
        questions.forEach(a -> {
            Optional<Answer> any = vo.getAnswers().stream().filter(b -> a.getId().equals(b.getId())).findAny();
            if (any.isPresent() && any.get().getAnswer().equals(a.getAnswer())) {
                num.getAndIncrement();
            }
        });
        //答对80%则认为成功
        if (NumberUtil.div(num.get(), vo.getAnswers().size()) >= 0.8) {
            redisTemplate.opsForValue().set(UserConstant.EMAIL_VALID_REDIS_KEY + vo.getUsername(), "1", 5, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

}
