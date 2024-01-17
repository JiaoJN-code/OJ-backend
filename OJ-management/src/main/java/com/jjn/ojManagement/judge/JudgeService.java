package com.jjn.ojManagement.judge;

import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.vo.QuestionVo;

/**
 * 判题服务
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public interface JudgeService {
    ExecuteCodeResponse doJudge(QuestionSubmit questionSubmit);
}
