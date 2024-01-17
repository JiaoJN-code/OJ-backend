package com.jjn.ojManagement.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目提交编程语言枚举值
 *
 * @author 焦久宁
 * @date 2024/1/8
 */
public enum QuestionSubmitLanguageEnum {
    JAVA("java", "java"),
    CPLUSPLUS("c++", "c++"),
    GOLANG("golang", "golang");

    private final String text;

    private final String value;

    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        List<String> valueList = new ArrayList<>();
        for (QuestionSubmitLanguageEnum value : values()) {
            valueList.add(value.value);
        }
        return valueList;
    }

    /**
     * 根据 value 值获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitLanguageEnum questionSubmitLanguageEnum : values()) {
            if (questionSubmitLanguageEnum.value.equals(value)) {
                return questionSubmitLanguageEnum;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
