package com.jjn.ojManagement.model.dto.User;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求参数
 *
 * @author 焦久宁
 * @date 2023/11/20
 */
@Data
public class UserLoginRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
    private static final long serialVersionUID = 3191241716373120793L;
}
