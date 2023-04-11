package com.peintre.config;


import com.peintre.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.peintre.enums.StatusCode.SYSTEM_ERROR;


/**
 * 自定义异常配置类
 *
 * @author Peintre
 * @date 2021/07/27
 */
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public BizException(){
        this.message = SYSTEM_ERROR.getDesc();
        this.code = SYSTEM_ERROR.getCode();
    }

    public BizException(String message) {
        this.code = SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public BizException(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getDesc();
    }


}
