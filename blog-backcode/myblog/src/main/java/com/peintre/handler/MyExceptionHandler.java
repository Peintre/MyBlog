package com.peintre.handler;

import com.peintre.config.BizException;
import com.peintre.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Program: myblog
 * @Description: 统一异常处理
 * @Author: Peintre
 * @Date: 2022/12/18 11:26
 **/
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return Result 返回结果类
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result exceptionHandler(BizException e) {
        log.error(e.getMessage());
        return Result.fail(e.getCode(),e.getMessage());
    }
}
