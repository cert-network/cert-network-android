package com.app.blockchain.certnetwork.common.base.view.holder.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public abstract class BaseItem implements Parcelable {
    private long id;
    private int type;

    public BaseItem(long id, int type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public BaseItem setId(long id) {
        this.id = id;
        return this;
    }

    public BaseItem copy() {
        return this;
    }

    public int getType() {
        return type;
    }

    public BaseItem() {
    }

    public boolean isItemTheSame(Object baseItem) {
        return equals(baseItem);
    }

    public boolean isContentTheSame(Object baseItem) {
        return equals(baseItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.type);
    }

    protected BaseItem(Parcel in) {
        this.id = in.readLong();
        this.type = in.readInt();
    }

}
