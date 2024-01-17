package com.jjn.ojManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.constant.UserConst;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.mapper.UserMapper;
import com.jjn.ojManagement.model.dto.User.UserRegisterRequest;
import com.jjn.ojManagement.model.entity.User;
import com.jjn.ojManagement.model.vo.LoginUserVo;
import com.jjn.ojManagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 焦久宁
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-11-20 20:06:30
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "jjn";

    /**
     * 用户登录
     *
     * @param userAccount  账户
     * @param userPassword 密码
     * @param request
     * @return
     */
    @Override
    public LoginUserVo userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        // 用户名最低四位
        if (userAccount.length() < 3) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 密码加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 根据用户名,密码查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", md5Password);
        User user = this.baseMapper.selectOne(queryWrapper);
        // user为空
        if (Objects.isNull(user)) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名密码错误");
        }
        // 4. 将查询的用户信息保存到session中
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(user, loginUserVo);
        request.getSession().setAttribute(UserConst.USER_LOGIN_STATE, loginUserVo);
//        log.info(request.getSession().getId());
        // 5. 返回
        return loginUserVo;
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        String userName = userRegisterRequest.getUserName();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userProfile = userRegisterRequest.getUserProfile();
        // 校验密码和确认密码是否一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户重复");
            }
            // 密码加密
            String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 插入数据
            User user = new User();
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setUserPassword(md5Password);
            user.setUserProfile(userProfile);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    /**
     * 获取当前用户登录信息
     *
     * @param request
     * @return
     */
    @Override
    public LoginUserVo getLoginUser(HttpServletRequest request) {
//        log.info(request.getSession().getId());
        Object userObj = request.getSession().getAttribute(UserConst.USER_LOGIN_STATE);
        LoginUserVo currentUser = (LoginUserVo) userObj;
        if (currentUser == null || currentUser.getId() < 0) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @Override
    public Boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConst.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(UserConst.USER_LOGIN_STATE);
        return true;
    }
}




