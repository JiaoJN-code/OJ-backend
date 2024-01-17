package com.jjn.ojManagement.model.enums;

/**
 * 题目判题状态枚举
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
public enum QuestionSubmitStatusEnum {
    DEFAULT(0, "默认"),
    WAITING(1, "等待中"),
    RUNNING(2, "运行中"),
    END_OF_RUN(3, "运行结束");
    private Integer value;

    private String message;

    QuestionSubmitStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
