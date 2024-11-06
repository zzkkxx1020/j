package org.example.jworker.common.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    //成功返回码
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    VALIDATE_FAILED(404, "参数检验失败"),

    FORBIDDEN(403, "没有相关权限"),
    UNAUTHORIZED(405, "暂未登录"),
    TOKEN_EXP(406, "TOKEN已过期"),
    TOKEN_ERROR(407, "TOKEN错误"),
    INTERNAL_INTERFACE_EXCEPTION(408, "内部接口异常"),



    /**
     * 业务异常
     */
    CAPTCHA_ERROR(10000, "验证码错误"),
    CAPTCHA_EXPIRED(10001, "验证码过期"),
    BUSINESS_SMS_NOT_FOUND(10002, "未发送验证码"),
    BUSINESS_SMS_LIMIT(10003, "短信发送频率超限"),
    BUSINESS_SMS_TOO_MANY_FAILURES(10004, "验证失败次数过多"),
    BUSINESS_ALIYUN_SLIDE_FAIL(10005, "滑动验证失败"),
    PASSWORD_FORM_ERROR(10006, "密码格式错误"),
    USERNAME_EXIST(10007, "用户名已存在"),
    MOBLIE_EXIST(10008, "手机号已存在"),
    ROLE_EXIST(10009, "角色名称或角色编码重复"),
    MENU_EXIST(10010, "菜单名称或编码URl重复"),
    FUNCTION_EXIST(10011, "前台功能名称或编码URl重复"),
    EMAIL_ERROR(10012, "邮箱格式错误"),
    PASSWORD_ERROR(10013, "密码错误"),
    PARAM_DUPLICATE_NAME(10014, "名称重复"),
    BUSINESS_FORBID_UPDATE_PARAM(10015, "非本人用户禁止修改"),
    DATA_NOT_FOUND(10021, "数据不存在"),
    JOB_TYPE_NOT_FOUND(10022, "任务类型不存在"),
    USER_NOT_EXIST(10023, "用户名不存在"),
    UNKNOW_EXCEPTION(10024, "未知异常"),
    FILE_IO_ERROR(10037,"io错误"),


    ;

    private long code;
    private String msg;
}
