package com.jjn.ojManagement.judge.codeSandBox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱接收参数
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 一组输入
     */
    private List<String> inputList;

    private String code;

    private String language;

}
