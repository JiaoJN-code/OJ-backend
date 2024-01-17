package com.jjn.ojManagement.judge.codeSandBox.model;

import com.jjn.ojManagement.model.vo.JudgeInfo;
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
