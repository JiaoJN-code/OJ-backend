package com.jjn.ojManagement.model.dto.Question;

import lombok.Data;

/**
 * 判题配置
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制
     */
    private Long timeLimit;

    /**
     * 内存限制
     */
    private Long memoryLimit;
}
