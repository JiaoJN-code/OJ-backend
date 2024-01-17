package com.jjn.ojManagement.common;

/**
 * 返回工具类
 *
 * @author 焦久宁
 * @date 2023/11/20
 */
public class ResultUtils {
    /**
     * 返回成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMsg());
    }

    /**
     * 返回失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 返回失败
     *
     * @param errorCode
     * @param msg
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String msg) {
        return new BaseResponse<>(errorCode.getCode(), null, msg);
    }

    /**
     * 返回失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static BaseResponse error(int code, String msg) {
        return new BaseResponse<>(code, null, msg);
    }
}
