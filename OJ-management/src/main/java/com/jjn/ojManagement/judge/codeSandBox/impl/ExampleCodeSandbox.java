package com.jjn.ojManagement.judge.codeSandBox.impl;

import com.jjn.ojManagement.judge.codeSandBox.CodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jjn.ojManagement.model.vo.JudgeInfo;

import java.util.List;

/**
 * 示例代码沙箱
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("调用程序成功");
        executeCodeResponse.setStatus(1);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("程序执行成功");
        judgeInfo.setTime(1000L);
        judgeInfo.setMemory(1000L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
