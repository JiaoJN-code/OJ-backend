package com.jjn.ojManagement.model.dto.User;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 焦久宁
 * @date 2024/1/4
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userName;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String userProfile;
}
