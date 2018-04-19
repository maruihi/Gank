package com.mr.gank.net.api;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.mr.gank.model.bean.ExampleBean;
import com.mr.gank.model.bean.LoadBean;
import com.mr.gank.model.bean.ResultResult;
import com.mr.gank.model.respone.BaseModel;
import com.mr.gank.net.httplibrary.HttpFunc;
import com.mr.gank.net.httplibrary.HttpLoadFunc;
import com.mr.gank.net.httplibrary.HttpSchedulers;
import com.mr.gank.net.httplibrary.ProgressRequestBody;
import com.mr.gank.net.httplibrary.callback.SubscribeLoadData;
import com.mr.gank.net.httplibrary.callback.SubscriberCommon;
import com.mr.gank.net.service.ApiService;
import com.mr.gank.utils.CompressBitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * 例子
 * Author： by MR on 2017/9/18.
 */

public class ExampleApi {

    /**
     * 返回值为bean、上传服务器类型为bean
     */
    public static Subscription getMainDate(ExampleBean bean, Subscriber<ExampleBean> subscriber) {
        return ApiService.getInstance().getIService().getGetBean(bean)
                .doOnSubscribe(ApiService.verifyLogin(true))   //如果当前请求需要验证登录，加入这个
                .compose(HttpSchedulers.<BaseModel<ExampleBean>>io_main())          //线程调度
                .map(new HttpFunc<ExampleBean>())         //结果转换
                .subscribe(subscriber);
    }

    /**
     * 获取list、参数为键值对
     */
    public static Subscription getList(String phone, SubscriberCommon<ArrayList<ExampleBean>> subscriber) {
        return ApiService.getInstance().getIService().getGetList(phone)
                .compose(HttpSchedulers.<BaseModel<ArrayList<ExampleBean>>>io_main())
                .map(new HttpFunc<ArrayList<ExampleBean>>())
                .subscribe(subscriber);
    }

    /**
     * 获取验证码例子
     *
     * @param phone       the phone
     * @param isVoiceAuth the is voice auth
     * @param typeInt     the type int
     * @param subscriber  the subscriber
     */
    public static Subscription getAuthCode(String phone, final boolean isVoiceAuth, final int typeInt, Subscriber<String> subscriber) {

        boolean isVerifyLogin = typeInt > 1;            //验证登陆
        String authType = isVoiceAuth ? "voice" : "msg";  //验证码类型
        String type = Integer.toString(typeInt);
        return ApiService.getInstance().getIService().getAuthCode(phone, authType, type)
                .doOnSubscribe(ApiService.verifyLogin(isVerifyLogin))//登陆判断
                .compose(HttpSchedulers.<BaseModel<String>>io_main())   //线程调度
                .map(new HttpFunc<String>())           //将结果转换
                .subscribe(subscriber);
    }


    /**
     * 上传file
     * @param type
     * @param file
     * @param subscribeLoadData
     * @return
     */
    public static Subscription uploadFile(String type, File file, SubscribeLoadData subscribeLoadData) {
        //自定义带进度的RequestBody
        ProgressRequestBody uploadFile = new ProgressRequestBody(RequestBody.create(null, file), subscribeLoadData);
        //构建一个Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), uploadFile);

        //要传给服务器的其他参数
        RequestBody typeBody = RequestBody.create(null, type);

        return ApiService.getInstance().getICommonService()
                .uploadFile(filePart, typeBody)
                .map(new HttpLoadFunc<LoadBean>())    //服务器结果解析
                .compose(HttpSchedulers.<LoadBean>io_main())
                .subscribe(subscribeLoadData);
    }

    /**
     * 上传键值对、bitmap
     */
    public static Subscription uploadWheat(ExampleBean bean,
                                           String tempImgFileName1,
                                           String tempImgFileName2,
                                           String tempImgFileName3,
                                           File tempImg1,
                                           File tempImg2,
                                           File tempImg3,
                                           Observer<ResultResult> observer) {

        //要传给服务器的其他参数
        RequestBody productionUnitIDBody = RequestBody.create(null, bean.getDescription());
        RequestBody varietyIdBody = RequestBody.create(null, bean.getName());
        RequestBody growthPidBody = RequestBody.create(null, bean.getVersion());
        RequestBody surveyTimeBody = RequestBody.create(null, bean.getCode()+"");
        RequestBody leafBody = RequestBody.create(null, bean.getId()+"");

        RequestBody tempImgFileName1Body = null;
        RequestBody filePart1 = null;
        RequestBody tempImgFileName2Body = null;
        RequestBody filePart2 = null;
        RequestBody tempImgFileName3Body = null;
        RequestBody filePart3 = null;
        try {

            if (!TextUtils.isEmpty(tempImgFileName1)) {
                tempImgFileName1Body = RequestBody.create(null, tempImgFileName1);
                Bitmap bitmap = CompressBitmapUtils.compressedPixels(tempImg1.getPath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                byte[] byteArray = bos.toByteArray();
                filePart1 = RequestBody.create(MediaType.parse("multipart/form-data"), byteArray);
            }


            if (!TextUtils.isEmpty(tempImgFileName2)) {
                tempImgFileName2Body = RequestBody.create(null, tempImgFileName2);

                Bitmap bitmap = CompressBitmapUtils.compressedPixels(tempImg2.getPath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                byte[] byteArray = bos.toByteArray();
                filePart2 = RequestBody.create(MediaType.parse("multipart/form-data"), byteArray);
            }


            if (!TextUtils.isEmpty(tempImgFileName3)) {
                tempImgFileName3Body = RequestBody.create(null, tempImgFileName3);
                Bitmap bitmap = CompressBitmapUtils.compressedPixels(tempImg3.getPath());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                byte[] byteArray = bos.toByteArray();
                filePart3 = RequestBody.create(MediaType.parse("multipart/form-data"), byteArray);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return ApiService.getInstance().getICommonService()
                .example2(productionUnitIDBody, varietyIdBody,growthPidBody,
                        surveyTimeBody,  leafBody,
                        tempImgFileName1Body, tempImgFileName2Body, tempImgFileName3Body,
                        filePart1,filePart2,filePart3)
                .compose(HttpSchedulers.<ResultResult>io_main())
                .subscribe(observer);


    }

    /**
     * 将键值对、bitmap封装到一个body后上传
     *
     * @param observer 监听
     * @return
     */
    public static Subscription example1(ExampleBean bean,
                                              List<String> imgNames,
                                              Observer<ResultResult> observer) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("userId",bean.getId()+"");
        builder.addFormDataPart("productionUnitID",bean.getDescription());
        try {
            if (imgNames!=null){
                for (int i = 0; i < imgNames.size(); i++) {
                    builder.addFormDataPart("photoName" + i,imgNames.get(i));

                    Bitmap bitmap = CompressBitmapUtils.compressedPixels(imgNames.get(i));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                    byte[] byteArray = bos.toByteArray();
                    builder.addFormDataPart("photo" + i, imgNames.get(i), RequestBody.create(MediaType.parse("multipart/form-data"), byteArray));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultipartBody build = builder.build();
        return ApiService.getInstance().getICommonService().example1(build)
                .compose(HttpSchedulers.<ResultResult>io_main())
                .subscribe(observer);
    }

}
