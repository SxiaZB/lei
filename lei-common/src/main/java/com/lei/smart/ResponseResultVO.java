package com.lei.smart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResultVO{
    /**
     * @description 响应码
     */
    private int code;

    /**
     * @description 响应消息
     */
    private String message;

    /**
     * @description 数据
     */
    private Object data;

    public ResponseResultVO(int code, String message,Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
