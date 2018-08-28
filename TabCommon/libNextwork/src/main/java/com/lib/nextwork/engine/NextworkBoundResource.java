
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
public abstract class NextworkBoundResource<ResultType, RequestType, ResponseApiType extends NextworkResponse<RequestType>, ResourceType extends NextworkResource<ResultType>> {
    private final static String TAG = NextworkBoundResource.class.getSimpleName();
    private final AppExecutors appExecutors;
    private final NextworkResourceCreator<ResultType, ResourceType> creator;

    private final MediatorLiveData<ResourceType> result = new MediatorLiveData<>();

    @MainThread
    public NextworkBoundResource(AppExecutors appExecutors,
                                 NextworkResourceCreator<ResultType, ResourceType> creator) {
        this.appExecutors = appExecutors;
        this.creator = creator;
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<ResponseApiType> apiResponse = createCall();
        NLog.i(setPrefixLog(), "CreateCall: " + apiResponse);
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.setValue(creator.<ResultType>loadingFromNetwork(null, setPayloadBack(), true));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    ResultType data = convertToResultType(processResponse(response));
                    saveCallResult(data);
                    NLog.i(setPrefixLog(), "Convert To ResultType: " + data);
                    appExecutors.mainThread().execute(() -> {
                                // we specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                result.setValue(creator.success(data, setPayloadBack(), false, true));
                            }
                    );
                });
            } else {
                onFetchFailed(response.error);
                result.setValue(creator.error(response.error, null, setPayloadBack(), true));
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
    protected void saveCallResult(@NonNull ResultType item) {
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResponseApiType> createCall();


    public abstract ResultType convertToResultType(RequestType requestNewData);

    public String setPrefixLog() {
        return null;
    }

    @Nullable
    public Object setPayloadBack() {
        return null;
    }

}
