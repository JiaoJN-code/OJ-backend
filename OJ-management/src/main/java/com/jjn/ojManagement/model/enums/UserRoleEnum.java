package com.jjn.ojManagement.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 焦久宁
 * @date 2023/11/20
 */
public enum UserRoleEnum {
    USER("用户", "user"),
    ADMIN("管理员", "admin"),
    BAN("被封禁", "ban");


    private final String text;

    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取所有枚举值
     *
     * @return 枚举值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据枚举值返回描述
     *
     * @param value
     * @return 枚举描述
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
            if (userRoleEnum.value.equals(value)) {
                return userRoleEnum;
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
