package com.jjn.codeSandbox.controller;

import com.jjn.codeSandbox.CodeSandbox;
import com.jjn.codeSandbox.model.ExecuteCodeRequest;
import com.jjn.codeSandbox.model.ExecuteCodeResponse;
import org.aopalliance.reflect.Code;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 焦久宁
 * @date 2024/1/30
 */
@RestController
@RequestMapping("/codeSandbox")
public class MainController {
    @Resource
    private CodeSandbox codeSandbox;

    @PostMapping("/executeCode")
    public ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        return codeSandbox.executeCode(executeCodeRequest);
    }
}
