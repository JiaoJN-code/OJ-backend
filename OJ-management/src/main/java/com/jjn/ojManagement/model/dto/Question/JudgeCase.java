package com.jjn.ojManagement.model.dto.Question;

import lombok.Data;

import java.util.List;

/**
 * 题目用例
 *
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
