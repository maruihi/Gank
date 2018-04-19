package com.mr.gank.net.httplibrary;


import com.mr.gank.model.bean.LoadBean;
import com.mr.gank.model.respone.BaseModel;

/**
 * 请求结果转换器
 */
public class HttpLoadFunc<T> extends HttpFunc<T> {
    @Override
    public T call(BaseModel<T> response) {
        super.call(response);
        return (T) new LoadBean(true,"");
    }
}
