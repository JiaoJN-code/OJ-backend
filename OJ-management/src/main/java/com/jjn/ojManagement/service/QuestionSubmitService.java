package com.jjn.ojManagement.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjn.ojManagement.model.dto.Question.QuestionQueryRequest;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitQueryRequest;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.vo.QuestionSubmitVo;
import com.jjn.ojManagement.model.vo.QuestionVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 焦久宁
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service
 * @createDate 2024-01-08 14:25:13
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    Page<QuestionSubmitVo> getQuestionSubmitVoPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);
}
