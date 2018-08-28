package com.lib.nextwork.engine;

import com.lib.nextwork.engine.model.NextworkResource;

import javax.annotation.Nullable;

/**
 * Created by「 The Khaeng 」on 13 Oct 2017 :)
 */

public interface NextworkResourceCreator<T, R extends NextworkResource<T>> {
    R loadingFromNetwork(T data, @Nullable Object payload, Boolean isFetch);

    R loadingFromDatabase(@Nullable Object payload);

    R success(T newData, @Nullable Object payload, boolean isCache, Boolean isFetch);

    R error(Throwable error, T oldData, @Nullable Object payload, Boolean isFetch);
}
