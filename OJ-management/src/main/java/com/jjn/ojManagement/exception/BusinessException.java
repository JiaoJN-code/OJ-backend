package com.jjn.ojManagement.exception;

import com.jjn.ojManagement.common.ErrorCode;

/**
 * 自定义异常类
 *
 * @author 焦久宁
 * @date 2023/11/20
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
