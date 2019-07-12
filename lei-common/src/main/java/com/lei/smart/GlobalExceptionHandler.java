package com.lei.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @param e     异常
     * @description 处理所有不可知的异常
     */
    @ExceptionHandler({Exception.class})    //申明捕获那个异常类
    public ResponseResultVO globalExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseResultUtil().error(ResponseCodeEnum.OPERATE_FAIL);
    }

    /**
     * @date
     * @param e 异常
     * @description 处理所有业务异常
     */
    @ExceptionHandler({BaseBusinessException.class})
    public ResponseResultVO BusinessExceptionHandler(BaseBusinessException e) {
        return new ResponseResultUtil().error(e.getCode(), e.getMessage());
    }
}
