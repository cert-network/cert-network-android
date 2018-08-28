package com.lib.nextwork.engine;

/**
 * Created by「 The Khaeng 」on 05 Feb 2018 :)
 */

public abstract class Trigger{
    private boolean isForceFetch = false;

    public Trigger( boolean isForceFetch ){
        this.isForceFetch = isForceFetch;
    }

    public boolean isForceFetch(){
        return isForceFetch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trigger trigger = (Trigger) o;

        return isForceFetch == trigger.isForceFetch;
    }

    @Override
    public int hashCode() {
        return (isForceFetch ? 1 : 0);
    }

}
