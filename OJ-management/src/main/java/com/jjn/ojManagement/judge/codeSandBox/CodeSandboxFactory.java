package com.jjn.ojManagement.judge.codeSandBox;

import com.jjn.ojManagement.judge.codeSandBox.impl.ExampleCodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.impl.RemoteCodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂， 根据字符串参数创建指定的代码沙箱示例
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     *
     * @param type 沙箱类型
     * @return 代码沙箱实例
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "example":
            default:
                return new ExampleCodeSandbox();
        }
    }
}
