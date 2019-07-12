package com.lei.smart;

public class UserException extends BaseBusinessException{
    public UserException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
