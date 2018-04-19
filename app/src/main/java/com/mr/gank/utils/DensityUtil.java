package com.mr.gank.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据屏幕分辨率转变值工具类
 * Author： by MR on 2017/3/15.
 */
public class DensityUtil {

    public static <T> ArrayList<T> copyList(List<T> list) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T t : list) {
            arrayList.add(t);
        }
        return arrayList;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

//    public static float dpToPx(Context context, float valueInDp) {
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
//    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


}