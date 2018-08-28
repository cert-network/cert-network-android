package com.lib.nextwork.engine.steam;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;

import com.lib.nextwork.engine.model.NextworkResource;


/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class FetchDataSteam<T extends NextworkResource> extends TriggerDataSteam<Boolean,T> {



    public Boolean getTriggerValue(){
        return triggerLiveData.getValue();
    }


    public void clearTrigger(){
        triggerLiveData.setValue( null );
    }

    public void trigger(){
        trigger( false );
    }


    @Override
    public void trigger( Boolean isForce ){
        super.trigger( isForce );
    }

    @Override
    public FetchDataSteam<T> initSwitchMap(Function<Boolean, LiveData<T>> func ){
        return (FetchDataSteam<T>) super.initSwitchMap( func );

    }

    @Override
    protected boolean isShouldBeSkip( Boolean newForceFetch, Boolean oldForceFetch ){
        return equals( newForceFetch, oldForceFetch ) || ( newForceFetch && !oldForceFetch );
    }

}
