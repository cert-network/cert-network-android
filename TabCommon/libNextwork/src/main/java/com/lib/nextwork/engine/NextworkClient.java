package com.lib.nextwork.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

public abstract class NextworkClient{
    private OkHttpClient okHttpClient;

    public NextworkClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        onConfigBuilder( builder );

        List<Interceptor> interceptorList = setupInterceptorList();
        if( interceptorList != null && !interceptorList.isEmpty() ){
            for( Interceptor interceptor : interceptorList ){
                builder.addInterceptor( interceptor );
            }
        }

        okHttpClient = builder
                .readTimeout( getDefaultTimeoutMillis(), TimeUnit.MILLISECONDS )
                .writeTimeout( getDefaultTimeoutMillis(), TimeUnit.MILLISECONDS )
                .connectTimeout( getDefaultTimeoutMillis(), TimeUnit.MILLISECONDS )
                .build();
    }


    abstract public long getDefaultTimeoutMillis();

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public void onConfigBuilder(OkHttpClient.Builder builder ){
    }

    public List<Interceptor> setupInterceptorList(){
        return new ArrayList<>();
    }

}
