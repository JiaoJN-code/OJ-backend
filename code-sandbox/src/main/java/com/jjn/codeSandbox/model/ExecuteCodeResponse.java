package com.jjn.codeSandbox.model;

import lombok.Data;

import java.util.List;

/**
 * 代码沙箱返回参数
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
@Data
public class ExecuteCodeResponse {
    /**
     * 输出列表
     */
    private List<String> outputList;

    private String message;

    private Integer status;

    private JudgeInfo judgeInfo;
}
