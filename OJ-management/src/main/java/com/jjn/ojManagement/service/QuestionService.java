package com.jjn.ojManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjn.ojManagement.model.dto.Question.QuestionQueryRequest;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.vo.QuestionVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 焦久宁
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-01-06 20:15:53
*/
public interface QuestionService extends IService<Question> {
    void validQuestion(Question question, boolean add);

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    Page<QuestionVo> getQuestionVoPage(Page<Question> questionPage, HttpServletRequest request);

}
