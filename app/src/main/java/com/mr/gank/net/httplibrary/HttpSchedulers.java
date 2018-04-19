package com.mr.gank.net.httplibrary;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpSchedulers {
    public static Observable.Transformer transformer;


    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> io_main() {
        return (Observable.Transformer<T, T>) getScheduler();
    }

    @SuppressWarnings("unchecked")
    private static <T> Observable.Transformer<T, T> getScheduler() {
        if (transformer == null) {
            Observable.Transformer<T, T> tempTransformer = new Observable.Transformer<T, T>() {
                @Override
                public Observable<T> call(Observable<T> observable) {
                    return observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
            transformer = tempTransformer;
            return tempTransformer;
        } else {
            return transformer;
        }
    }
}
