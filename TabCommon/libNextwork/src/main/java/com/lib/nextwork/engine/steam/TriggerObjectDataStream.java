package com.lib.nextwork.engine.steam;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;

import com.lib.nextwork.engine.Trigger;
import com.lib.nextwork.engine.model.NextworkResource;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

@Nullable
public class TriggerObjectDataStream<TRIGGER extends Trigger, RESULT extends NextworkResource>
        extends TriggerDataSteam<TRIGGER, RESULT> {

    @Override
    public void trigger(TRIGGER trigger) {
        if (trigger != null && trigger.isForceFetch()) clearTrigger();
        super.trigger(trigger);
    }


    @NotNull
    @Override
    public TriggerObjectDataStream<TRIGGER, RESULT> initSwitchMap(Function<TRIGGER, LiveData<RESULT>> func) {
        return (TriggerObjectDataStream<TRIGGER, RESULT>) super.initSwitchMap(func);
    }



}
