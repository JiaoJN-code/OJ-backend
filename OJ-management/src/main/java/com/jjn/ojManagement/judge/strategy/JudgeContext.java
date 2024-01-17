package com.jjn.ojManagement.judge.strategy;

import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.vo.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 用于定义judge策略的输入信息
 *
 * @author 焦久宁
 * @date 2024/1/17
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;


    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

}
