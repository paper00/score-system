package com.score.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author paper
 * @date 2019/11/11
 */
@AllArgsConstructor
@Getter
public enum EXCEPTION_CODE {

    // 参数异常
    WRONG_USERNAME_OR_PASSWORD(1001, "用户名或密码错误"),

    // 数据库信息异常
    NO_SUCH_USER_EXCEPTION(2001, "无此用户"),
    NO_SUCH_STUDENT_EXCEPTION(2002, "无此学生"),
    NO_SUCH_TEACHER_EXCEPTION(2003, "无此老师"),
    NO_SUCH_COURCE_EXCEPTION(2004, "无此课程"),

    // 验证异常
    AUTHORIZATION_EXCEPTION(3001, "验证错误"),
    NOT_LOGIN(3002, "未登录"),
    TOKEN_IS_EXPIRATION(3003, "Token过期"),
    TOKEN_PARSE_EXCEPTION(3004, "Token解析异常"),
    JWT_SIGN_EXCEPTION(3005, "JWT签名异常"),

    // 程序错误
    NOT_INITIALIZE(4001, "常量未被初始化"),
    CREATE_KEYPAIR_ERROR(4002, "创建公钥密钥失败"),
    ALREADY_USED_GETWRITER(4003, "getWriter函数已经被调用过"),
    
    // 其他错误
    UNKNOWN_ERROR(5000,"未知错误");
	
	private int code;
	private String message;

}