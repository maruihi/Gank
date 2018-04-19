package com.mr.gank.model.bean;

/**
 *
 * Created by MR on 2016/11/2.
 */

public class LoadBean {
    private boolean isSuccess;
    private String path;

    public LoadBean(boolean isSuccess, String path) {
        this.isSuccess = isSuccess;
        this.path = path;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
