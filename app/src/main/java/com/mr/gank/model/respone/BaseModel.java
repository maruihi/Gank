package com.mr.gank.model.respone;

/**
 * 服务器返回通用格式
 * {}
 */
public class BaseModel<T> {

    private int code;
    private String message;
    private T result;

    /**
     * 服务器成功响应
     *
     * @return the boolean
     */
    public boolean isSuccess(){
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
