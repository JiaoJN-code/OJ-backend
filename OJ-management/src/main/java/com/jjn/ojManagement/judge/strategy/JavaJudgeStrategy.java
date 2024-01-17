package com.jjn.ojManagement.judge.strategy;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.dto.Question.JudgeConfig;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.enums.JudgeInfoEnum;
import com.jjn.ojManagement.model.vo.JudgeInfo;

import java.util.List;

/**
 * Java判题策略
 *
 * @author 焦久宁
 * @date 2024/1/17
 */
public class JavaJudgeStrategy implements JudgeStrategy {

    private final static Gson GSON = new Gson();

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        // 判断沙箱结果输出数量是否和预期输出数量相等
        if (outputList.size() != judgeCaseList.size()) {
            judgeInfo.setMessage(JudgeInfoEnum.WRONG_ANSWER.getMessage());
            return judgeInfo;
        }

        // 以此判断每一项输出是否和预期输出相等
        for (int i = 0; i < outputList.size(); i++) {
            if (!outputList.get(i).equals(judgeCaseList.get(i).getOutput())) {
                judgeInfo.setMessage(JudgeInfoEnum.WRONG_ANSWER.getMessage());
                return judgeInfo;
            }
        }

        // 判断题目的限制是否符合要求
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = GSON.fromJson(judgeConfigStr, new TypeToken<JudgeConfig>() {
        }.getType());
        if (time > judgeConfig.getTimeLimit()) {
            judgeInfo.setMessage(JudgeInfoEnum.TIME_LIMIT_EXCEEDED.getMessage());
            return null;
        }
        if (memory > judgeConfig.getMemoryLimit()) {
            judgeInfo.setMessage(JudgeInfoEnum.MEMORY_LIMIT_EXCEEDED.getMessage());
            return judgeInfo;
        }
        return judgeInfo;
    }
}
