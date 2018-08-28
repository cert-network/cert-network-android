package com.lib.nextwork.engine.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A generic class that holds a value with its loadingFromNetwork status.
 *
 * @param <T>
 */
public abstract class NextworkResource<T> {

    @NonNull
    private final @Status
    int status;

    @Nullable
    private final Throwable exception;

    @Nullable
    private final T data;

    @Nullable
    private final Object payload;

    private final boolean isFetched;


    private final boolean isCached;

    public NextworkResource(@NonNull @Status int status,
                            @Nullable T data,
                            @Nullable Throwable exception,
                            @Nullable Object payload,
                            boolean isFetched,
                            boolean isCached) {
        this.status = status;
        this.data = data;
        this.exception = exception;
        this.payload = payload;
        this.isFetched = isFetched;
        this.isCached = isCached;
    }

    @NonNull
    public int getStatus() {
        return status;
    }

    @Nullable
    public Throwable getException() {
        return exception;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Object getPayload() {
        return payload;
    }

    public boolean isFetched() {
        return isFetched;
    }

    public boolean isCached() {
        return isCached;
    }

    @Override
    public String toString() {
        return "NextworkResource{" +
                "status=" + status +
                ", message='" + exception +
                ", data=" + data +
                ", payload=" + payload +
                ", isFetched=" + isFetched +
                ", isCached=" + isCached +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NextworkResource<?> that = (NextworkResource<?>) o;

        if (status != that.status) return false;
        if (isFetched != that.isFetched) return false;
        if (isCached != that.isCached) return false;
        if (exception != null ? !exception.equals(that.exception) : that.exception != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return payload != null ? payload.equals(that.payload) : that.payload == null;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        result = 31 * result + (isFetched ? 1 : 0);
        result = 31 * result + (isCached ? 1 : 0);
        return result;
    }


}
