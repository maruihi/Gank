package com.mr.gank.net.httplibrary;


import com.mr.gank.model.respone.BaseModel;
import com.mr.gank.net.httplibrary.exception.RequestErrorException;
import com.mr.gank.utils.MLog;

import rx.functions.Func1;

/**
 * 转化服务器返回数据为BaseModel
 */
public class HttpFunc<T> implements Func1<BaseModel<T>,T> {
    @Override
    public T call(BaseModel<T> response) {

        if(response == null){
            throw new RequestErrorException(-100,"数据异常");
        }

        //服务器返回非正确结果。抛出异常
        if (!response.isSuccess()){
            MLog.i("response:%s", "网络请求错误:" + response.toString());
            throw new RequestErrorException(response.getCode(),response.getMessage());
        }



        return response.getResult();
    }
}
