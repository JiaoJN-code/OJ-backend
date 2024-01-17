package com.jjn.ojManagement.judge.codeSandBox;

import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
