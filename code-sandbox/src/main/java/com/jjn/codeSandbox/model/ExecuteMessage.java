package com.jjn.codeSandbox.model;

import lombok.Data;

/**
 * 进程执行信息
 *
 * @author 焦久宁
 * @date 2024/1/25
 */
@Data
public class ExecuteMessage {
    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;
}
