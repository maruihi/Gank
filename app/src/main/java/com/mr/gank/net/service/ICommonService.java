package com.mr.gank.net.service;


import com.mr.gank.global.Api;
import com.mr.gank.model.bean.LoadBean;
import com.mr.gank.model.bean.ResultResult;
import com.mr.gank.model.respone.BaseModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 大文件上传
 * Author： by MR on 2017/4/21.
 */
public interface ICommonService {
    /**
     * 通用的单个文件上传
     *
     * @return
     */
    @Multipart
    @POST("upload/file.json")
    Observable<BaseModel<LoadBean>> uploadFile(
            @Part MultipartBody.Part file,
            @Part("type") RequestBody type);

    @Multipart
    @POST("{url}")
    Observable<BaseModel<String>> uploadFiles(
            @Path(value = "url", encoded = true) String url,
            @Part List<MultipartBody.Part> file);

    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    /**
     * 将键值对、bitmap封装到一个body后上传
     *
     * @return
     */
    @POST(Api.EXAMPLE_API)
    Observable<ResultResult> example1(@Body MultipartBody builder);

    /**
     * 将键值对、bitmap分开上传
     *
     * @return
     */
    @Multipart
    @POST(Api.EXAMPLE_API)
    Observable<ResultResult> example2(
            @Part("productionUnitID") RequestBody productionUnitID,
            @Part("varietyid") RequestBody varietyid,
            @Part("growthPid") RequestBody growthPid,
            @Part("surveyTime") RequestBody surveyTime,
            @Part("leafAreaIndex") RequestBody leafAreaIndex,
            @Part("image1") RequestBody tempImgFileName1,
            @Part("image2") RequestBody tempImgFileName2,
            @Part("image3") RequestBody tempImgFileName3,
            @Part("tempFile1\"; filename=\"img.jpg\"") RequestBody tempImg1,
            @Part("tempFile2\"; filename=\"img.jpg\"") RequestBody tempImg2,
            @Part("tempFile3\"; filename=\"img.jpg\"") RequestBody tempImg3);


}

