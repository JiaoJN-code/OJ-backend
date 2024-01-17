package com.jjn.ojManagement.constant;

/**
 * 用户常量
 *
 * @author 焦久宁
 * @date 2023/11/20
 */
public interface UserConst {
    /**
     * 用户登录态
     */
    String USER_LOGIN_STATE = "user_logon";

    // region 权限
    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号
     */
    String BAN_ROLE = "ban";

    // endregion
}
