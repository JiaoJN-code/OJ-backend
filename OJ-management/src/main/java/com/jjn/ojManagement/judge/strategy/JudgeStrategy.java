package com.jjn.ojManagement.judge.strategy;

import com.jjn.ojManagement.model.vo.JudgeInfo;

/**
 * 判题策略
 *
 * @author 焦久宁
 * @date 2024/1/17
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
