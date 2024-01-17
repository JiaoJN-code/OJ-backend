package com.jjn.ojManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jjn.ojManagement.model.dto.User.UserRegisterRequest;
import com.jjn.ojManagement.model.entity.User;
import com.jjn.ojManagement.model.vo.LoginUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 焦久宁
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-11-20 20:06:30
 */
public interface UserService extends IService<User> {
    LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    Long userRegister(UserRegisterRequest userRegisterRequest);

    LoginUserVo getLoginUser(HttpServletRequest request);

    Boolean userLogout(HttpServletRequest request);
}
