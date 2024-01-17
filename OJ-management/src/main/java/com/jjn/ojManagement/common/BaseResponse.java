package com.jjn.ojManagement.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 焦久宁
 * @date 2023/11/20
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String msg;

    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMsg());
    }
}
