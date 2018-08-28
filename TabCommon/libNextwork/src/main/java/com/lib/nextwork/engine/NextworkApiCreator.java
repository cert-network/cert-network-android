package com.lib.nextwork.engine;

import android.util.Log;

import com.lib.nextwork.engine.tools.BooleanIntTypeAdapter;
import com.lib.nextwork.BuildConfig;
import com.lib.nextwork.cookie.NextworkWebkitCookieJar;
import com.lib.nextwork.interceptor.DefaultHttpLoggerInterceptor;
import com.google.gson.GsonBuilder;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.javaer.retrofit2.converter.jaxb.JaxbConverterFactory;
import io.reactivex.annotations.NonNull;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class NextworkApiCreator<T> {
    private static final String TAG = NextworkApiCreator.class.getSimpleName();

    private Class<T> apiClass;

    public NextworkApiCreator(Class<T> apiClass) {
        this.apiClass = apiClass;
    }

    protected boolean isLogger() {
        return BuildConfig.DEBUG;
    }

    protected boolean isUseCookie() {
        return false;
    }

    public T createApi() {
        return createApi(apiClass, getBaseUrl(), null);
    }

    public T createApiWithHeader(Map<String, String> header) {
        return createApiWithHeader(apiClass, getBaseUrl(), header, null);
    }

    public T createApiNoHeader() {
        return createApiNoHeader(apiClass, getBaseUrl(), null);
    }

    public T createApi(T mockApi) {
        return createApi(apiClass, getBaseUrl(), mockApi);
    }

    public T createApiWithHeader(Map<String, String> header, T mockApi) {
        return createApiWithHeader(apiClass, getBaseUrl(), header, mockApi);
    }

    public T createApiNoHeader(T mockApi) {
        return createApiNoHeader(apiClass, getBaseUrl(), mockApi);
    }

    public T createApi(String baseUrl) {
        return createApi(apiClass, baseUrl, null);
    }

    public T createApiWithHeader(String baseUrl, Map<String, String> header) {
        return createApiWithHeader(apiClass, baseUrl, header, null);
    }

    public T createApiNoHeader(String baseUrl) {
        return createApiNoHeader(apiClass, baseUrl, null);
    }

    public T createApi(String baseUrl, T mockApi) {
        return createApi(apiClass, baseUrl, mockApi);
    }

    public T createApiWithHeader(String baseUrl, Map<String, String> header, T mockApi) {
        return createApiWithHeader(apiClass, baseUrl, header, mockApi);
    }

    public T createApiNoHeader(String baseUrl, T mockApi) {
        return createApiNoHeader(apiClass, baseUrl, mockApi);
    }

    public T createApi(Class<T> apiClass) {
        return createApi(apiClass, getBaseUrl(), null);
    }

    public T createApiWithHeader(Class<T> apiClass, Map<String, String> header) {
        return createApiWithHeader(apiClass, getBaseUrl(), header, null);
    }

    public T createApiNoHeader(Class<T> apiClass) {
        return createApiNoHeader(apiClass, getBaseUrl(), null);
    }

    public T createApi(Class<T> apiClass, T mockApi) {
        return createApi(apiClass, getBaseUrl(), mockApi);
    }

    public T createApiWithHeader(Class<T> apiClass, Map<String, String> header, T mockApi) {
        return createApiWithHeader(apiClass, getBaseUrl(), header, mockApi);
    }

    public T createApiNoHeader(Class<T> apiClass, T mockApi) {
        return createApiNoHeader(apiClass, getBaseUrl(), mockApi);
    }

    public T createApi(Class<T> apiClass, String baseUrl, T mockApi) {
        if (mockApi != null) return mockApi;
        return getBaseRetrofitBuilder(new HashMap<>())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(getAdapterFactory())
                .build()
                .create(apiClass);
    }

    public T createApiWithHeader(Class<T> apiClass, String baseUrl, Map<String, String> header, T mockApi) {
        if (mockApi != null) return mockApi;
        return getBaseRetrofitBuilder(header)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(getAdapterFactory())
                .build()
                .create(apiClass);
    }

    public T createApiNoHeader(Class<T> apiClass, String baseUrl, T mockApi) {
        if (mockApi != null) return mockApi;
        return getBaseRetrofitBuilder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(getAdapterFactory())
                .build()
                .create(apiClass);
    }


    public T createXmlApi() {
        return createXmlApi(apiClass, getBaseUrl(), null);
    }

    public T createXmlApi(T mockApi) {
        return createXmlApi(apiClass, getBaseUrl(), mockApi);
    }

    public T createXmlApi(String baseUrl) {
        return createXmlApi(apiClass, baseUrl, null);
    }

    public T createXmlApi(String baseUrl, T mockApi) {
        return createXmlApi(apiClass, baseUrl, mockApi);
    }

    public T createXmlApi(Class<T> apiClass) {
        return createXmlApi(apiClass, getBaseUrl(), null);
    }

    public T createXmlApi(Class<T> apiClass, T mockApi) {
        return createXmlApi(apiClass, getBaseUrl(), mockApi);
    }

    public T createXmlApi(Class<T> apiClass, String baseUrl, T mockApi) {
        if (mockApi != null) return mockApi;
        return getXmlRetrofitBuilder(new HashMap<>())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(apiClass);
    }


    /**
     * return "null" for not use Converter in retrofit.
     */
    protected GsonBuilder addGsonConverter() {
        BooleanIntTypeAdapter boolAdapter = new BooleanIntTypeAdapter();
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, boolAdapter)
                .registerTypeAdapter(boolean.class, boolAdapter)
                .setPrettyPrinting();
    }

    protected Interceptor getOfflineCacheInterceptor() {
        return null;
    }

    protected Cache getCache() {
        return null;
    }

    protected List<Interceptor> getAnotherInterceptor(@NonNull List<Interceptor> listInterceptor) {
        return listInterceptor;
    }

    protected List<Interceptor> getAnotherNetworkInterceptor(@NonNull List<Interceptor> listInterceptor) {
        return listInterceptor;
    }

    protected Map<String, String> getHeader(@NonNull Map<String, String> headerMap) {
        return headerMap;
    }

    protected Authenticator getDefaultProxyAuthenticator() {
        return null;
    }

    protected Proxy getDefaultProxy() {
        return null;
    }

    protected CookieJar getDefaultCookieJar() {
        if (isUseCookie() && !NextworkWebkitCookieJar.getInstance().isInitialized()) {
            Log.e(TAG, "********************************************************************************************************************************");
            Log.e(TAG, "To use cookie, you need to call NextworkWebkitCookieJar.getInstance().init(getApplicationContext()); in application class.");
            Log.e(TAG, "********************************************************************************************************************************");
        }
        if (isUseCookie() && NextworkWebkitCookieJar.getInstance().isInitialized()) {
            return NextworkWebkitCookieJar.getInstance();
        }
        return CookieJar.NO_COOKIES;
    }

    protected CertificatePinner getDefaultCertificatePinner() {
        return new CertificatePinner.Builder().build();
    }

    protected HttpLoggingInterceptor getDefaultHttpLoggingInterceptor(boolean showLog) {
        return DefaultHttpLoggerInterceptor.getInterceptor(showLog);
    }

    /* =========================== Private method =============================================== */
    private Retrofit.Builder getBaseRetrofitBuilder() {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (addGsonConverter() != null) {
            builder.addConverterFactory(GsonConverterFactory.create(addGsonConverter().create()));
        }
        builder.client(getOkHttpClient().build());
        return builder;
    }

    private Retrofit.Builder getBaseRetrofitBuilder(Map<String, String> header) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (addGsonConverter() != null) {
            builder.addConverterFactory(GsonConverterFactory.create(addGsonConverter().create()));
        }
        builder.client(addHeader(getOkHttpClient(), header).build());
        return builder;
    }


    private Retrofit.Builder getXmlRetrofitBuilder(Map<String, String> header) {
        return new Retrofit.Builder()
                .addConverterFactory(JaxbConverterFactory.create())
                .client(addHeader(getOkHttpClient(), header).build());
    }


    private OkHttpClient.Builder getOkHttpClient() {
        OkHttpClient.Builder builder = getClient().newBuilder();
        if (getDefaultProxyAuthenticator() != null) {
            builder.proxyAuthenticator(getDefaultProxyAuthenticator());
        }
        if (getDefaultProxy() != null) {
            builder.proxy(getDefaultProxy());
        }
        if (getOfflineCacheInterceptor() != null) {
            builder.addInterceptor(getOfflineCacheInterceptor());
        }
        if (getCache() != null) {
            builder.cache(getCache());
        }

        List<Interceptor> listInterceptor = getAnotherInterceptor(new ArrayList<>());
        if (listInterceptor != null
                && !listInterceptor.isEmpty()) {
            for (Interceptor interceptor : getAnotherInterceptor(listInterceptor)) {
                builder.addInterceptor(interceptor);
            }
        }


        List<Interceptor> listNetworkInterceptor = getAnotherNetworkInterceptor(new ArrayList<>());
        if (listNetworkInterceptor != null
                && !listNetworkInterceptor.isEmpty()) {
            for (Interceptor interceptor : listNetworkInterceptor) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        return builder
                .addInterceptor(getDefaultHttpLoggingInterceptor(isLogger()))
                .certificatePinner(getDefaultCertificatePinner())
                .cookieJar(getDefaultCookieJar());

    }

    private OkHttpClient.Builder addHeader(OkHttpClient.Builder builder, Map<String, String> header) {
        Map<String, String> mapHeader = getHeader(new HashMap<>());
        if (header != null
                && !header.isEmpty()) {
            if (mapHeader == null) {
                mapHeader = new HashMap<>();
            }

            for (Map.Entry<String, String> entry : header.entrySet()) {
                if (mapHeader.containsKey(entry.getKey())) {
                    mapHeader.replace(entry.getKey(), entry.getValue());
                } else {
                    mapHeader.put(entry.getKey(), entry.getValue());
                }

            }
        }

        if (mapHeader != null
                && !mapHeader.isEmpty()) {
            Map<String, String> finalMapHeader = mapHeader;
            builder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                for (Map.Entry<String, String> entry : finalMapHeader.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
                Request request = requestBuilder.method(original.method(), original.body())
                        .build();

                return chain.proceed(request);

            });
        }
        return builder;
    }

    /* =========================== Abstract method ============================================== */
    //every network service class must inherit this class and set the class type, too
    public abstract String getBaseUrl();

    public abstract OkHttpClient getClient();

    public CallAdapter.Factory getAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}
