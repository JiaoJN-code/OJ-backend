package com.jjn.ojManagement.model.dto.Question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class QuestionAddRequest implements Serializable {
    /**
     * 题目名称
     */
    private String title;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 用例配置(json数组)
     */
    private JudgeCase judgeCase;

    /**
     * 判题配置(json对象)
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}
