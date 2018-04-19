package com.mr.gank.net.httplibrary.exception;

/**
 * 服务器响应非正确结果
 * Created by marui on 2016/10/25.
 */

public class RequestErrorException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public RequestErrorException(int errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message+"";
    }
}
