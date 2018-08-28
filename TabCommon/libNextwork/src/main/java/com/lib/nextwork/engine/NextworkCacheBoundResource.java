
package com.lib.nextwork.engine;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.lib.nextwork.engine.model.NextworkResource;
import com.lib.nextwork.engine.model.NextworkResponse;
import com.lib.nextwork.operator.NLog;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NextworkCacheBoundResource<ResultType, RequestType, ResponseApiType extends NextworkResponse<RequestType>, ResourceType extends NextworkResource<ResultType>> {
    private final static String TAG = NextworkCacheBoundResource.class.getSimpleName();
    private final AppExecutors appExecutors;
    private final NextworkResourceCreator<ResultType, ResourceType> creator;

    private final MediatorLiveData<ResourceType> result = new MediatorLiveData<>();

    @MainThread
    public NextworkCacheBoundResource(AppExecutors appExecutors,
                                      NextworkResourceCreator<ResultType, ResourceType> creator) {
        this.appExecutors = appExecutors;
        this.creator = creator;
        result.setValue(creator.<ResultType>loadingFromDatabase(setPayloadBack()));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            NLog.i(setPrefixLog(), "Load from database (old): " + data);
            result.removeSource(dbSource);
            boolean shouldFetch = shouldFetch(data);
            NLog.i(setPrefixLog(), "ShouldFetch: " + shouldFetch);
            if (shouldFetch) {
                if (setLoadCacheBeforeFetch())
                    result.setValue(creator.success(data, setPayloadBack(), true, true));
                fetchFromNetwork(dbSource, data);
            } else {
                result.addSource(dbSource, oldData -> result.setValue(creator.success(oldData, setPayloadBack(), true, false)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource, ResultType oldData) {
        LiveData<ResponseApiType> apiResponse = createCall();
        NLog.i(setPrefixLog(), "CreateCall: " + apiResponse);
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, data -> result.setValue(creator.<ResultType>loadingFromNetwork(data, setPayloadBack(), true)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    ResultType data = convertToResultType(oldData, processResponse(response));
                    saveCallResult(data);
                    NLog.i(setPrefixLog(), "Save call result: " + data);
                    appExecutors.mainThread().execute(() -> {
                                // we specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                LiveData<ResultType> dataSource = loadFromDb();
                                result.addSource(dataSource,
                                        newData -> {
                                            result.setValue(creator.success(newData, setPayloadBack(), false, true));
                                            NLog.i(setPrefixLog(), "Load from database (new): " + newData);
                                        });

                            }
                    );
                });
            } else {
                onFetchFailed(response.error);
                result.addSource(dbSource,
                        data -> result.setValue(creator.error(response.error, data, setPayloadBack(), true)));
            }
        });
    }

    protected void onFetchFailed(Throwable error) {
        NLog.e(setPrefixLog(), "Load from database: onFetchFailed()");
    }

    public LiveData<ResourceType> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ResponseApiType response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull ResultType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType oldData);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ResponseApiType> createCall();


    public abstract ResultType convertToResultType(ResultType oldData, RequestType requestNewData);

    public String setPrefixLog() {
        return null;
    }

    public boolean setLoadCacheBeforeFetch() {
        return false;
    }

    @Nullable
    public Object setPayloadBack() {
        return null;
    }

}
