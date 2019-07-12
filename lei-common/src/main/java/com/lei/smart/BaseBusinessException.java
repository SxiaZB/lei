package com.lei.smart;

public class BaseBusinessException extends RuntimeException{
    private Integer code;

    // 给子类用的方法
    public BaseBusinessException(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }

    private BaseBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
