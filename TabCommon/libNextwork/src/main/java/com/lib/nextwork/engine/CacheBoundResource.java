
package com.lib.nextwork.engine;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lib.nextwork.engine.model.NextworkResource;
import com.lib.nextwork.operator.NLog;


/**
 * A generic class that can provide a resource backed by both the sqlite database.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 */
public abstract class CacheBoundResource<ResultType , ResourceType extends NextworkResource<ResultType>> {
    private final MediatorLiveData<ResourceType> result = new MediatorLiveData<>();
    @MainThread
    public CacheBoundResource(NextworkResourceCreator<ResultType, ResourceType> creator) {
        result.setValue(creator.<ResultType>loadingFromDatabase(setPayloadBack()));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            NLog.i(setPrefixLog(), "Load from database (old): " + data);
            result.removeSource(dbSource);
            result.addSource(dbSource, oldData -> result.setValue(creator.success(oldData, setPayloadBack(), true, false)));
        });
    }


    public LiveData<ResourceType> asLiveData() {
        return result;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    public String setPrefixLog() {
        return null;
    }

    @Nullable
    public Object setPayloadBack() {
        return null;
    }

}
