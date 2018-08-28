package com.app.blockchain.certnetwork.module.main.adapter.item;

import android.os.Parcel;

import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem;
import com.app.blockchain.certnetwork.module.main.adapter.operator.CertItemCreator;


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class EmptyCertItem extends BaseItem {


    public EmptyCertItem(int id){
        super(id, CertItemCreator.TYPE_EMPTY_CERT);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected EmptyCertItem(Parcel in) {
        super(in);
    }

    public static final Creator<EmptyCertItem> CREATOR = new Creator<EmptyCertItem>() {
        @Override
        public EmptyCertItem createFromParcel(Parcel source) {
            return new EmptyCertItem(source);
        }

        @Override
        public EmptyCertItem[] newArray(int size) {
            return new EmptyCertItem[size];
        }
    };
}
