package org.example.jworker.common.R;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 通用返回对象
 */
@Data
public class Result<T> {
    private long code;
    private boolean success;
    private String message;
    private T data;

    protected Result() {
    }

    protected Result(long code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return new Result<T>(ResultCodeEnum.SUCCESS.getCode(), true, ResultCodeEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCodeEnum.SUCCESS.getCode(), true, ResultCodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<T>(ResultCodeEnum.SUCCESS.getCode(), true, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param resultCodeEnum 错误码
     */
    public static <T> Result<T> failed(ResultCodeEnum resultCodeEnum, T data) {
        return new Result<T>(resultCodeEnum.getCode(), false, resultCodeEnum.getMsg(), data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> Result<T> failed(Long errorCode, String message) {
        return new Result<T>(errorCode, false, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param resultCodeEnum 错误码
     */
    public static <T> Result<T> failed(ResultCodeEnum resultCodeEnum) {
        return new Result<T>(resultCodeEnum.getCode(), false, resultCodeEnum.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failed(String message) {
        return new Result<T>(ResultCodeEnum.FAILED.getCode(), false, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed() {
        return failed(ResultCodeEnum.FAILED.getMsg());
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<T>(ResultCodeEnum.VALIDATE_FAILED.getCode(), false, message, null);
    }

    public static <T> Result<T> judge(boolean success) {
        if (success) {
            return success();
        } else {
            return failed();
        }
    }
}
