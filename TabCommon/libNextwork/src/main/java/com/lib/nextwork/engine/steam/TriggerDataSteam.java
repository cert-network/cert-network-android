package com.lib.nextwork.engine.steam;


import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lib.nextwork.engine.tools.TriggerTransformations;
import com.lib.nextwork.engine.AbsentLiveData;
import com.lib.nextwork.engine.model.NetworkStatus;
import com.lib.nextwork.engine.model.NextworkResource;

import org.jetbrains.annotations.NotNull;


/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class TriggerDataSteam<TRIGGER, RESULT extends NextworkResource> {

    protected MutableLiveData<TRIGGER> triggerLiveData = createTriggerLiveData();
    protected LiveData<RESULT> liveData;

    private Function<TRIGGER, LiveData<RESULT>> func;


    private Function<RESULT, ? extends NextworkResource> mapFunc = null;

    public void clearTrigger() {
        triggerLiveData.setValue(null);
    }


    public void trigger(TRIGGER trigger) {
        if (getTriggerValue() != null && isShouldBeSkip(getTriggerValue(), trigger)) return;
        triggerLiveData.setValue(trigger);
    }


    @NotNull
    public TriggerDataSteam<TRIGGER, RESULT> initSwitchMap(Function<TRIGGER, LiveData<RESULT>> func) {
        this.func = func;
        this.liveData = getSwitchLiveData(triggerLiveData, func);
        return this;
    }

    public void observeData(LifecycleOwner owner, Observer<RESULT> observer) {
        if (liveData == null) throw new NullPointerException("initSwitchMap(...) before use.");

        liveData.observe(owner, result -> {
            if (result != null) {
                observer.onChanged(result);
                if (result.getStatus() == NetworkStatus.SUCCESS
                        && !(result.isCached() && result.isFetched())) {
                    triggerLiveData.setValue(null); // clear trigger
                } else if (result.getStatus() == NetworkStatus.ERROR) {
                    triggerLiveData.setValue(null); // clear trigger
                }

            }
        });

    }

    public void observeDataOnlyOne(LifecycleOwner owner, Observer<RESULT> observer) {
        if (!hasObserver()) {
            this.observeData(owner, observer);
        }
    }

    public boolean hasObserver() {
        return liveData != null && liveData.hasObservers();
    }

    public TriggerDataSteam<TRIGGER, RESULT> newDataSteam() {
        triggerLiveData = createTriggerLiveData();
        liveData = getSwitchLiveData(triggerLiveData, func);

        if (mapFunc != null) {
            liveData = (LiveData<RESULT>) map(mapFunc).liveData;
        }
        return this;
    }

    private MutableLiveData<TRIGGER> createTriggerLiveData() {
        return new MutableLiveData<>();
    }

    @NonNull
    protected LiveData<RESULT> getSwitchLiveData(LiveData<TRIGGER> triggerLiveData, Function<TRIGGER, LiveData<RESULT>> func) {
        return Transformations.switchMap(
                triggerLiveData,
                trigger -> {
                    if (trigger == null) {
                        return AbsentLiveData.create();
                    } else {
                        return func.apply(trigger);
                    }
                }
        );
    }

    public <NEW extends NextworkResource> TriggerDataSteam<?, NEW> map(Function<RESULT, NEW> mapFunc) {
        this.mapFunc = mapFunc;
        return TriggerTransformations.map(this, mapFunc);
    }

    public TriggerDataSteam setResultLiveData(LiveData<RESULT> liveData) {
        this.liveData = liveData;
        return this;
    }

    public LiveData<RESULT> getResultLiveData() {
        return liveData;
    }

    public TRIGGER getTriggerValue() {
        return triggerLiveData.getValue();
    }

    @Nullable
    public RESULT getValue() {
        if (liveData == null) return null;
        return liveData.getValue();
    }

    protected boolean isShouldBeSkip(TRIGGER newTrigger, TRIGGER oldTrigger) {
        return equals(newTrigger, oldTrigger);
    }

    boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }


    public void removeObserver(Observer<RESULT> observer) {
        liveData.removeObserver(observer);
    }

    public void removeObservers(LifecycleOwner lifecycleOwner) {
        liveData.removeObservers(lifecycleOwner);
    }


}
