package com.score.utils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author paper
 * @date 2019/11/09
 */
@Data
public class Result<T> {
	
    private int status;
    private String message;
    private boolean isSuccess;
    private T data;

    private Result(int status, String message, boolean isSuccess) {
    	this.status = status;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    private Result(int status, String message, boolean isSuccess, T data) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> Result <T> OK() {
        return new Result<T>(200, "success", true);
    }

    public static <T> Result <T> success(T value) {
        return new Result<T>(200, "success", true, value);
    }

    public static <T> Result <T> UserError() {
        return new Result<T>(400, "UserError", false);
    }

    public static <T> Result <T> ServerError() {
        return new Result<T>(500, "ServerError", false);
    }

    public static Result CodeAndMessage(@NotNull int status, @NotNull String message,@NotNull boolean isSuccess) {
        return new Result(status, message, isSuccess);
    }

    public Result<T> code(@NotNull int status) {
        this.status = status;
        return this;
    }

    public Result<T> message(@NotNull String message) {
        this.message = message;
        return this;
    }

    public Result<T> appendInfo(@NotNull T info) {
        this.data = info;
        return this;
    }

}
