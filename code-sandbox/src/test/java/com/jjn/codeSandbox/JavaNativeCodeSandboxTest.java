package com.jjn.codeSandbox;

import cn.hutool.core.io.resource.ResourceUtil;
import com.jjn.codeSandbox.model.ExecuteCodeRequest;
import com.jjn.codeSandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author 焦久宁
 * @date 2024/1/25
 */
class JavaNativeCodeSandboxTest {

    @Test
    public void executeCodeTest() {
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        List<String> input = Arrays.asList("1 2", "3 4");
        executeCodeRequest.setInputList(input);
        String code = ResourceUtil.readStr("testCome/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();

        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);

    }

}