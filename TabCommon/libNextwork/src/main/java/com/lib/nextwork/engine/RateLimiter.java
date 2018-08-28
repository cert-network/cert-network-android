package com.lib.nextwork.engine;

import android.os.SystemClock;
import android.support.v4.util.ArrayMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

public class RateLimiter<KEY> {
    protected Map<KEY, Long> timestamps = new ArrayMap<>();
    private final long timeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean shouldFetch(KEY key) {
        Long lastFetched = timestamps.get(key);
        long now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return onNullLastFetched(key, timestamps);
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    public void clear(KEY key) {
        timestamps.put(key, 0L);
    }

    public void clearAll() {
        timestamps.clear();
    }

    public boolean onNullLastFetched(KEY key, Map<KEY, Long> timestamps) {
        return true;
    }

    protected long now() {
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key) {
        timestamps.remove(key);
    }

}
