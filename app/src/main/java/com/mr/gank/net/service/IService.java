package com.mr.gank.net.service;

import com.mr.gank.global.Api;
import com.mr.gank.model.bean.ExampleBean;
import com.mr.gank.model.respone.BaseModel;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * 返回值是普通数据
 * Created by MR on 2016/8/5.
 */
public interface IService {

    //=======例子========

    /**
     * 返回值为bean、上传服务器类型为bean
     */
    @POST(Api.GET_BEAN)
    Observable<BaseModel<ExampleBean>> getGetBean(@Body ExampleBean bean);

    /**
     * 返回值为list、上传服务器类型为键值对
     */
    @FormUrlEncoded
    @POST(Api.GET_LIST)
    Observable<BaseModel<ArrayList<ExampleBean>>> getGetList(@Field("phone") String phone);

    /**
     * 获取验证码
     * phone     手机号
     * authType  msg:短信 voice：语音 不传默认短信
     * type      1 注册 2 找回密码 3 绑定更换绑定
     */
    @FormUrlEncoded
    @POST(Api.GET_AUTH_CODE)
    Observable<BaseModel<String>> getAuthCode(@Field("phone") String phone, @Field("authType") String authType, @Field("type") String type);

    /**
     * 无参
     *
     * @return
     */
    @POST(Api.EXAMPLE_API)
    Observable<BaseModel<String>> getCropBaseData();

}