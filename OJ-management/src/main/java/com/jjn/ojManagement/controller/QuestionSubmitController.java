package com.jjn.ojManagement.controller;

import com.jjn.ojManagement.common.BaseResponse;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.common.ResultUtils;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.model.dto.QuestionSubmit.QuestionSubmitAddRequest;
import com.jjn.ojManagement.model.enums.JudgeInfoEnum;
import com.jjn.ojManagement.model.vo.JudgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 * @author 焦久宁
 * @date 2024/1/15
 */
@RestController
@RequestMapping("/question/submit")
@Slf4j
public class QuestionSubmitController {

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

        // 返回结果
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(512L);
        judgeInfo.setMemory(1024L);
        judgeInfo.setMessage(JudgeInfoEnum.ACCEPTED.getMessage());
        return ResultUtils.success(judgeInfo);
    }
}
