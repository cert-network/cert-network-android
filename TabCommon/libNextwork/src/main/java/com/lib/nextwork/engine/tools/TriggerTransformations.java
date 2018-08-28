package com.lib.nextwork.engine.tools;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.lib.nextwork.engine.model.NextworkResource;
import com.lib.nextwork.engine.steam.TriggerDataSteam;


/**
 * Created by「 The Khaeng 」on 30 Mar 2018 :)
 */
@SuppressWarnings("ALL")
public class TriggerTransformations {

    private TriggerTransformations() {
    }

    @MainThread
    public static <X, Y, Z extends NextworkResource> TriggerDataSteam<X, Z> map(@NonNull TriggerDataSteam source,
                                                                                @NonNull final Function<Y, Z> func) {
        return source.setResultLiveData(Transformations.map(source.getResultLiveData(), func));
    }

    @MainThread
    public static <X, Y, Z extends NextworkResource> TriggerDataSteam<X, Z> switchMap(@NonNull TriggerDataSteam source,
                                                                                      @NonNull final Function<X, LiveData<Y>> func) {
        return source.setResultLiveData(Transformations.map(Transformations.switchMap(source.getResultLiveData(), func), func));
    }

}
