package com.jjn.ojManagement.model.vo;

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
     * 程序执行状态 0-成功 1-失败
     */
    private Integer state;

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
