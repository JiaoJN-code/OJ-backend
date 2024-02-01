package com.jjn.ojManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjn.ojManagement.constant.CommonConstant;
import com.jjn.ojManagement.mapper.QuestionSubmitMapper;
import com.jjn.ojManagement.model.dto.Question.QuestionQueryRequest;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitQueryRequest;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.vo.QuestionSubmitVo;
import com.jjn.ojManagement.model.vo.QuestionVo;
import com.jjn.ojManagement.service.QuestionSubmitService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 焦久宁
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2024-01-08 14:25:13
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Override
    public Page<QuestionSubmitVo> getQuestionSubmitVoPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVo> questionSubmitVoPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVoPage;
        }
        // 填充信息
        List<QuestionSubmitVo> questionSubmitVoList = new ArrayList<>();
        for (QuestionSubmit questionSubmit : questionSubmitList) {
            QuestionSubmitVo questionSubmitVo = QuestionSubmitVo.objToVo(questionSubmit);
            questionSubmitVoList.add(questionSubmitVo);
        }
        questionSubmitVoPage.setRecords(questionSubmitVoList);
        return questionSubmitVoPage;
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> questionSubmitQueryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(questionSubmitQueryRequest)) {
            return questionSubmitQueryWrapper;
        }
        Long id = questionSubmitQueryRequest.getId();
        String language = questionSubmitQueryRequest.getLanguage();
        String judgeInfo = questionSubmitQueryRequest.getJudgeInfo();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        Date creatTime = questionSubmitQueryRequest.getCreatTime();
        int current = questionSubmitQueryRequest.getCurrent();
        int pageSize = questionSubmitQueryRequest.getPageSize();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        questionSubmitQueryWrapper.like(StringUtils.isNotBlank(judgeInfo), "judgeInfo", judgeInfo);
        questionSubmitQueryWrapper.like(ObjectUtils.isNotEmpty(status), "status", status);
        questionSubmitQueryWrapper.like(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        questionSubmitQueryWrapper.like(ObjectUtils.isNotEmpty(status), "status", status);
        questionSubmitQueryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        questionSubmitQueryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return questionSubmitQueryWrapper;
    }
}




