package com.jjn.codeSandbox.model;

import lombok.Data;

/**
 * 判题信息
 *
 * @author 焦久宁
 * @date 2024/1/8
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 程序执行时间
     */
    private Long time;

    /**
     * 程序执行内存
     */
    private Long memory;

}
