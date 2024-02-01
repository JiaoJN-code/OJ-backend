package com.jjn.ojManagement.judge.codeSandBox.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("调用远程代码沙箱");
        String url = "http://localhost:9528/codeSandbox/executeCode";
        String executeCodeRequestJson = JSON.toJSONString(executeCodeRequest);
        String response = HttpUtil.createPost(url).body(executeCodeRequestJson).execute().body();
        if (StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "远程代码沙箱错误");
        }
        return JSON.parseObject(response, ExecuteCodeResponse.class);
    }
}
