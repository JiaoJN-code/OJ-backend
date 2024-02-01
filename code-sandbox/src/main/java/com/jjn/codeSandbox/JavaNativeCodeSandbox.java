package com.jjn.codeSandbox;

import com.jjn.codeSandbox.model.ExecuteCodeRequest;
import com.jjn.codeSandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Java 原生实现代码沙箱
 *
 * @author 焦久宁
 * @date 2024/1/25
 */
@Slf4j
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
