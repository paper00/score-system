package com.score.exception;

/**
 * @author paper
 * @date 2019/11/11
 * @description 自定义异常类
 */
public class UserException extends Exception{
	
	public int exception_code;
    public String message;
	
	public UserException() {
        super();
    }

    public UserException(EXCEPTION_CODE exception_code) {
        this.exception_code = exception_code.getCode();
        this.message = exception_code.getMessage();
    }
}
