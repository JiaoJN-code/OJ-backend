package com.jjn.ojManagement.controller;

import com.jjn.ojManagement.common.BaseResponse;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.common.ResultUtils;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.judge.JudgeService;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.enums.QuestionSubmitStatusEnum;
import com.jjn.ojManagement.model.vo.JudgeInfo;
import com.jjn.ojManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // TODO 运行代码
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
}
