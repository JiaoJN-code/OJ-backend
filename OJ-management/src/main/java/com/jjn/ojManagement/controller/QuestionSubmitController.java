package com.jjn.ojManagement.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjn.ojManagement.common.BaseResponse;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.common.ResultUtils;
import com.jjn.ojManagement.constant.UserConst;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.exception.ThrowUtils;
import com.jjn.ojManagement.judge.JudgeService;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitQueryByQuestionIdRequest;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitQueryByUserIdRequest;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitQueryRequest;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.enums.QuestionSubmitStatusEnum;
import com.jjn.ojManagement.model.vo.JudgeInfo;
import com.jjn.ojManagement.model.vo.LoginUserVo;
import com.jjn.ojManagement.model.vo.QuestionSubmitVo;
import com.jjn.ojManagement.service.QuestionSubmitService;
import com.jjn.ojManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author 焦久宁
 * @date 2024/1/15
 */
@RestController
@RequestMapping("/question/submit")
@Slf4j
public class QuestionSubmitController {
    @Resource
    private UserService userService;

    @Resource
    private JudgeService judgeService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 运行代码
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return
     */
    @PostMapping("/run")
    public BaseResponse<JudgeInfo> judgeQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        // 判断提交内容是否为空
        if (questionSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());
        questionSubmit.setUserId(userService.getLoginUser(request).getId());

        JudgeInfo judgeInfo = judgeService.doJudge(questionSubmit);

        // 返回结果

        return ResultUtils.success(judgeInfo);
    }

    /**
     * 提交代码
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return
     */
    @PostMapping("/submitCode")
    public BaseResponse<JudgeInfo> submitCode(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        // 判断提交内容是否为空
        if (questionSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 存储提交代码信息
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setQuestionId(questionSubmitAddRequest.getQuestionId());

        // 获取提交用户
        LoginUserVo loginUser = userService.getLoginUser(request);
        questionSubmit.setUserId(loginUser.getId());

        // 插入提交信息
        boolean save = questionSubmitService.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据库失败");
        }

        // 运行代码
        JudgeInfo judgeInfo = judgeService.doJudge(questionSubmit);
        questionSubmit.setJudgeInfo(JSON.toJSONString(judgeInfo));
        questionSubmit.setStatus(QuestionSubmitStatusEnum.END_OF_RUN.getValue());
        boolean endSave = questionSubmitService.updateById(questionSubmit);
        if (!endSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据库失败");
        }
        return ResultUtils.success(judgeInfo);
    }

    /**
     * 根据题目id获取
     *
     * @param questionSubmitQueryByQuestionIdRequest
     * @param request
     * @return
     */
    @GetMapping("/get/byQuestionId")
    public BaseResponse<Page<QuestionSubmitVo>> getQuestionSubmitByQuestionId(QuestionSubmitQueryByQuestionIdRequest questionSubmitQueryByQuestionIdRequest, HttpServletRequest request) {
        if (questionSubmitQueryByQuestionIdRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int current = questionSubmitQueryByQuestionIdRequest.getCurrent();
        int pageSize = questionSubmitQueryByQuestionIdRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("questionId", questionSubmitQueryByQuestionIdRequest.getQuestionId());
        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize), queryWrapper);
        Page<QuestionSubmitVo> questionSubmitVoPage = questionSubmitService.getQuestionSubmitVoPage(page, request);
        return ResultUtils.success(questionSubmitVoPage);
    }

    /**
     * 根据用户id获取
     *
     * @param questionSubmitQueryByUserIdRequest
     * @param request
     * @return
     */
    @GetMapping("/get/byUserId")
    public BaseResponse<Page<QuestionSubmitVo>> getQuestionSubmitByUserId(QuestionSubmitQueryByUserIdRequest questionSubmitQueryByUserIdRequest, HttpServletRequest request) {
        if (questionSubmitQueryByUserIdRequest.getUserId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int current = questionSubmitQueryByUserIdRequest.getCurrent();
        int pageSize = questionSubmitQueryByUserIdRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", questionSubmitQueryByUserIdRequest.getUserId());
        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize), queryWrapper);
        Page<QuestionSubmitVo> questionSubmitVoPage = questionSubmitService.getQuestionSubmitVoPage(page, request);
        return ResultUtils.success(questionSubmitVoPage);
    }

    /**
     * 查询全部提交信息
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/questionSubmit")
    public BaseResponse<Page<QuestionSubmitVo>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        int current = questionSubmitQueryRequest.getCurrent();
        int pageSize = questionSubmitQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize), questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        Page<QuestionSubmitVo> questionSubmitVoPage = questionSubmitService.getQuestionSubmitVoPage(page, request);
        return ResultUtils.success(questionSubmitVoPage);
    }

    @PostMapping("/my/list/questionSubmit")
    public BaseResponse<Page<QuestionSubmitVo>> myListQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVo loginUser = userService.getLoginUser(request);
        questionSubmitQueryRequest.setUserId(loginUser.getId());
        int current = questionSubmitQueryRequest.getCurrent();
        int pageSize = questionSubmitQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize), questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        Page<QuestionSubmitVo> questionSubmitVoPage = questionSubmitService.getQuestionSubmitVoPage(page, request);
        return ResultUtils.success(questionSubmitVoPage);
    }
}
