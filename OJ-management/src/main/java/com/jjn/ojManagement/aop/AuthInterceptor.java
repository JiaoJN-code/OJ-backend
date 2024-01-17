package com.jjn.ojManagement.aop;

import com.jjn.ojManagement.annotation.AuthCheck;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.model.enums.UserRoleEnum;
import com.jjn.ojManagement.model.vo.LoginUserVo;
import com.jjn.ojManagement.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限校验AOP
 *
 * @author 焦久宁
 * @date 2024/1/7
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    /**
     * 权限校验注解执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole =  authCheck.mustRole();
        // 得到request对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取当前登录用户
        LoginUserVo loginUser = userService.getLoginUser(request);
        // 必须有权限才能通过
        if (StringUtils.isNotBlank(mustRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustUserRoleEnum == null ) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getUserRole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.getValue().equals(userRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!mustRole.equals(userRole)) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
