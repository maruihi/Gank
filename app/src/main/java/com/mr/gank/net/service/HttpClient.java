package com.mr.gank.net.service;

import com.mr.gank.App;
import com.mr.gank.utils.DeviceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 *
 * Created by MR on 2017/4/28.
 */

class HttpClient {

    private static OkHttpClient httpClient;
    private static OkHttpClient bigDataHttpClient;


    public static synchronized OkHttpClient getBigDataInstance() {
        if (bigDataHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.writeTimeout(180, TimeUnit.SECONDS);
            builder.readTimeout(180, TimeUnit.SECONDS);
            initHttpClient(builder);

            bigDataHttpClient = builder.build();
        }
        return bigDataHttpClient;
    }

    public static synchronized OkHttpClient getClient() {
        if (httpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            initHttpClient(builder);
            httpClient = builder.build();
        }
        return httpClient;
    }


    private static void initHttpClient(OkHttpClient.Builder builder) {
        builder.connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                //请求日志
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //统一请求参数
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        //统一的请求参数
                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("language", "ch")
                                .addQueryParameter("version", ""+ DeviceUtil.getVersionCode(App.appContext))
                                .addQueryParameter("platform", "android")
                                .build();

                        // 统一请求Header
                        Request.Builder requestBuilder = original.newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
    }
}
