package com.jjn.codeSandbox;

import com.jjn.codeSandbox.model.ExecuteCodeRequest;
import com.jjn.codeSandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 *
 * @author 焦久宁
 * @date 2024/1/25
 */
public interface CodeSandbox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
