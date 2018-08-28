package com.lib.nextwork.engine.model;

import com.lib.nextwork.exception.InternalErrorException;
import com.lib.nextwork.exception.NoInternetConnectionException;
import com.lib.nextwork.exception.NullBodyException;
import com.lib.nextwork.exception.ServerNotFoundException;
import com.lib.nextwork.exception.StatusCodeException;

import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by「 The Khaeng 」on 26 Feb 2018 :)
 */

public class ResultChecker<T extends NextworkResult> implements SingleTransformer<Response<T>, Response<T>> {


    @Override
    public Single<Response<T>> apply(Single<Response<T>> upstream) {
        return upstream
                .flatMap(this::handleExceptionSingle)
                .onErrorResumeNext(checkExceptionSingle());
    }


    @SuppressWarnings("ConstantConditions")
    private Single<Response<T>> handleExceptionSingle(Response<T> response) {
        if (response.code() == 404) {
            return Single.error(new ServerNotFoundException(404));
        } else if (response.code() == 500) {
            return Single.error(new InternalErrorException(500));
        } else if (response.code() >= 400) {
            return Single.error(new StatusCodeException(response.code(), response.message()));
        } else if (response.body() == null) {
            return Single.error(new NullBodyException("Response body() null result please check it."));
        } else if (response.body() != null && response.body().validateResult() != null) {
            return Single.error(response.body().validateResult());
        }
        return Single.just(response);
    }

    private Function<Throwable, SingleSource<Response<T>>> checkExceptionSingle() {
        return throwable -> {
            if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
                return Single.error(new NoInternetConnectionException(throwable.getMessage()));
            }
            return Single.error(throwable);
        };
    }

}
