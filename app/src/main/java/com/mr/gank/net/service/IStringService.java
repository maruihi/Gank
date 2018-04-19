package com.mr.gank.net.service;

import com.mr.gank.global.Api;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 返回值为String类型
 * Created by MR on 2016/8/5.
 */
public interface IStringService {

    /**
     * 例子
     * @param id
     * @return
     */
   @FormUrlEncoded
   @POST(Api.EXAMPLE_API)
   Observable<String> example(@Field("id") String id);

}