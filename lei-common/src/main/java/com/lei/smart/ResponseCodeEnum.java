package com.lei.smart;

public enum ResponseCodeEnum {
    // 系统通用
    SUCCESS(200, "操作成功"),

    UNLOGIN_ERROR(233, "没有登录"),

    OPERATE_FAIL(666, "操作失败"),
    PARAM_NEED(1000,"缺少必要参数"),
    // 用户
    SAVE_USER_INFO_FAILED(2001, "保存用户信息失败"),

    GET_USER_INFO_FAILED(2002, "保存用户信息失败"),

    WECHAT_VALID_FAILED(2003, "微信验证失败"),

    GET_USER_AUTH_INFO_FAILED(2004, "根据条件获取用户授权信息失败"),

    SAVE_USER_AUTH_INFO_FAILED(2005, "保存用户授权失败");

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }

}
