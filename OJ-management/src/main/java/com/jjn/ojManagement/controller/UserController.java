package com.jjn.ojManagement.controller;

import com.jjn.ojManagement.common.BaseResponse;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.common.ResultUtils;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.model.dto.User.UserLoginRequest;
import com.jjn.ojManagement.model.dto.User.UserRegisterRequest;
import com.jjn.ojManagement.model.vo.LoginUserVo;
import com.jjn.ojManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 用户接口
 *
 * @author 焦久宁
 * @date 2023/11/20
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userName = userRegisterRequest.getUserName();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userProfile = userRegisterRequest.getUserProfile();

        if (StringUtils.isAnyBlank(userName, userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 注册
        Long id = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (Objects.isNull(userLoginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVo loginUserVo = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVo);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVo> getLoginUser(HttpServletRequest request) {
        LoginUserVo currentUser = userService.getLoginUser(request);
        return ResultUtils.success(currentUser);
    }

    @GetMapping("logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
}
