package com.jjn.ojManagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.jjn.ojManagement.annotation.AuthCheck;
import com.jjn.ojManagement.common.BaseResponse;
import com.jjn.ojManagement.common.DeleteRequest;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.common.ResultUtils;
import com.jjn.ojManagement.constant.UserConst;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.exception.ThrowUtils;
import com.jjn.ojManagement.model.dto.Question.*;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.enums.UserRoleEnum;
import com.jjn.ojManagement.model.vo.LoginUserVo;
import com.jjn.ojManagement.model.vo.QuestionVo;
import com.jjn.ojManagement.service.QuestionService;
import com.jjn.ojManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 题目接口
 *
 * @author 焦久宁
 * @date 2024/1/6
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {
    private final static Gson GSON = new Gson();

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param questionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        JudgeCase judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 校验输入数据
        questionService.validQuestion(question, true);

        // 获取登录用户
        LoginUserVo loginUser = userService.getLoginUser(request);

        question.setUserId(loginUser.getId());
        question.setFavourNum(0);
        question.setThumbNum(0);

        boolean result = questionService.save(question);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据库失败");
        }

        return ResultUtils.success(question.getId());
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVo loginUser = userService.getLoginUser(request);
        long deleteId = deleteRequest.getId();
        Question question = questionService.getById(deleteId);
        // 判断删除的是否存在
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!Objects.equals(loginUser.getId(), question.getUserId()) && !loginUser.getUserRole().equals(UserRoleEnum.ADMIN.getValue())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.removeById(deleteId);
        return ResultUtils.success(result);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConst.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest, HttpServletRequest request) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        // 判断是否存在
        Long questionId = question.getId();
        Question oldQuestion = questionService.getById(questionId);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * 根据id获取
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/byId")
    public BaseResponse<QuestionVo> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(QuestionVo.objToVo(question));
    }

    @PostMapping("/list/question")
    public BaseResponse<Page<QuestionVo>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        int current = questionQueryRequest.getCurrent();
        int pageSize = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, pageSize), questionService.getQueryWrapper(questionQueryRequest));
        Page<QuestionVo> questionVoPage = questionService.getQuestionVoPage(questionPage, request);
        return ResultUtils.success(questionVoPage);
    }

    @PostMapping("/my/list/question")
    public BaseResponse<Page<QuestionVo>> myListQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVo loginUser = userService.getLoginUser(request);
        questionQueryRequest.setUserid(loginUser.getId());
        int current = questionQueryRequest.getCurrent();
        int pageSize = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, pageSize), questionService.getQueryWrapper(questionQueryRequest));
        Page<QuestionVo> questionVoPage = questionService.getQuestionVoPage(questionPage, request);
        return ResultUtils.success(questionVoPage);
    }
}
