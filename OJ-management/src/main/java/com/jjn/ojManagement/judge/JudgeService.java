package com.jjn.ojManagement.judge;

import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.vo.JudgeInfo;

/**
 * 判题服务
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public interface JudgeService {
    JudgeInfo doJudge(QuestionSubmit questionSubmit);
}
