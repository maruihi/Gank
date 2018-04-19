package com.mr.gank.net.service;

import com.mr.gank.global.FrameDemoConfig;
import com.mr.gank.net.httplibrary.converter.StringConverterFactory;
import com.mr.gank.net.httplibrary.exception.LoginException;
import com.mr.gank.utils.SharePreferencesUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action0;

/**
 *
 * Created by MR on 2017/4/28.
 */
public class ApiService {
    private static ApiService httpMethods;
    private final IService mIService;
    private final IStringService mIStringService;
    private final ICommonService mICommonService;

    /**
     * 获取一个实例
     *
     * @return the http methods
     */
    public static ApiService getInstance() {
        if (httpMethods == null) {
            synchronized (ApiService.class) {
                if (httpMethods == null) {
                    httpMethods = new ApiService();
                }
            }
        }

        return httpMethods;
    }

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(HttpClient.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(FrameDemoConfig.BASE_URL)
                .build();

        mIService = retrofit.create(IService.class);

        Retrofit stringRetrofit = new Retrofit.Builder()
                .client(HttpClient.getClient())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(FrameDemoConfig.BASE_URL)
                .build();

        mIStringService = stringRetrofit.create(IStringService.class);

        Retrofit bitDataRetrofit = new Retrofit.Builder()
                .client(HttpClient.getBigDataInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(FrameDemoConfig.BASE_URL)
                .build();
        mICommonService = bitDataRetrofit.create(ICommonService.class);
    }

    public IService getIService() {
        return mIService;
    }
    public IStringService getIStringService() {
        return mIStringService;
    }
    public ICommonService getICommonService() {
        return mICommonService;
    }

    /**
     * 验证登录Action封装
     *
     * @param isVerify 是否需要验证登陆
     * @return the action 0
     */
    public static Action0 verifyLogin(final boolean isVerify) {
        return new Action0() {
            @Override
            public void call() {
                if (isVerify) {
                    if (!SharePreferencesUtils.getBoolean(FrameDemoConfig.IS_LOGIN,false))
                        throw new LoginException("请先登录");
                }
            }
        };
    }

}
