package com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder;

import android.os.Parcel;

import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem;

/**
 * Created by「 The Khaeng 」on 13 Jun 2018 :)
 */
public class LoadmoreItem extends BaseItem {

    public static final int TYPE = 100000;


    public LoadmoreItem(int id) {
        super(id, TYPE);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected LoadmoreItem(Parcel in) {
        super(in);
    }

    public static final Creator<LoadmoreItem> CREATOR = new Creator<LoadmoreItem>() {
        @Override
        public LoadmoreItem createFromParcel(Parcel source) {
            return new LoadmoreItem(source);
        }

        @Override
        public LoadmoreItem[] newArray(int size) {
            return new LoadmoreItem[size];
        }
    };
}
