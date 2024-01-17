package com.jjn.ojManagement.judge.codeSandBox;

import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 静态代理，代码沙箱代理类
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱输入：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱输出：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
