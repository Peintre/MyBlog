package com.peintre.config;


import com.peintre.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


import static com.peintre.enums.StatusCode.FAIL;


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
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getDesc();
    }


}
