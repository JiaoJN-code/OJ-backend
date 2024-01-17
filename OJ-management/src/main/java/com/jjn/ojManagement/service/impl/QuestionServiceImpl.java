package com.jjn.ojManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.constant.CommonConstant;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.exception.ThrowUtils;
import com.jjn.ojManagement.mapper.QuestionMapper;
import com.jjn.ojManagement.model.dto.Question.QuestionQueryRequest;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.vo.QuestionVo;
import com.jjn.ojManagement.service.QuestionService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 焦久宁
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2024-01-06 20:15:53
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, answer, judgeCase, judgeConfig), ErrorCode.PARAMS_ERROR);
        }
        // 参数校验
        // 题目不能过长
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        // 内容不能过长
        if (StringUtils.isNotBlank(content) && content.length() > 8129) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(questionQueryWrapper)) {
            return questionQueryWrapper;
        }
        String title = questionQueryRequest.getTitle();
        String content = questionQueryRequest.getContent();
        List<String> tags = questionQueryRequest.getTags();
        Long userId = questionQueryRequest.getUserid();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        // 拼接查询条件
        questionQueryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        questionQueryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        if (!CollectionUtils.isEmpty(tags)) {
            for (String tag : tags) {
                questionQueryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        questionQueryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        questionQueryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return questionQueryWrapper;
    }

    @Override
    public Page<QuestionVo> getQuestionVoPage(Page<Question> questionPage, HttpServletRequest request) {
        List<Question> questionList = questionPage.getRecords();
        Page<QuestionVo> questionVoPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVoPage;
        }
        // 填充信息
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionVo questionVo = QuestionVo.objToVo(question);
            questionVoList.add(questionVo);
        }
        questionVoPage.setRecords(questionVoList);
        return questionVoPage;
    }
}




