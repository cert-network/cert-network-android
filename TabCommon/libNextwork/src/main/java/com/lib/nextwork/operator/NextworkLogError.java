package com.lib.nextwork.operator;


import android.os.Build;

import com.lib.nextwork.BuildConfig;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class NextworkLogError<T> implements Function<Throwable, SingleSource<T>> {

    private String tag;

    public NextworkLogError(String tag) {
        this.tag = tag;
    }

    @Override
    public SingleSource<T> apply(Throwable throwable) throws Exception {
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                throwable.addSuppressed(new Exception(tag));
            }
            NLog.e(tag, throwable);
        }
        return Single.error(throwable);
    }
}
