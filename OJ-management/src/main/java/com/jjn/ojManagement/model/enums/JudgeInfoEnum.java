package com.jjn.ojManagement.model.enums;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 判题信息枚举值
 *
 * @author 焦久宁
 * @date 2024/1/8
 */
public enum JudgeInfoEnum {
    ACCEPTED("Accepted", "成功"),
    WRONG_ANSWER("Wrong Answer", "答案错误"),
    COMPILE_ERROR("Compile Error", "编译错误"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "内存溢出"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "超时"),
    PRESENTATION_ERROR("Presentation Error", "展示错误"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "输出溢出"),
    WAITING("Waiting", "等待中"),
    DANGEROUS_OPERATION("Dangerous Operation", "危险操作"),
    RUNTIME_ERROR("Runtime Error", "运行错误"),
    SYSTEM_ERROR("System Error", "系统错误");

    private final String value;

    private final String message;

    JudgeInfoEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * 根据 value 值获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoEnum judgeInfoEnum : values()) {
            if (judgeInfoEnum.value.equals(value)) {
                return judgeInfoEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
