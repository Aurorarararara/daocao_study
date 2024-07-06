package com.daocao.common.exception;

/*
    自定义的业务异常，当我们的系统出现异常时，返回该异常给前端
 */

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;
    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException(HttpStatus unauthorized, String message) {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }
}
