package com.app.blockchain.certnetwork.common.base.mvvm.layer1View.holder;

import android.os.Parcel;

import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem;


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class SpaceItem extends BaseItem {

    public static int TYPE_SPACE = 10001;

    private int heightPx;
    private boolean isShow = true;


    public SpaceItem(int id){
        super(id, TYPE_SPACE);
    }

    public SpaceItem setHeightPx(int px) {
        this.heightPx = px;
        return this;
    }

    public SpaceItem setShow(boolean show) {
        isShow = show;
        return this;
    }

    public int getHeightPx() {
        return heightPx;
    }

    public boolean isShow() {
        return isShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpaceItem spaceItem = (SpaceItem) o;

        if (heightPx != spaceItem.heightPx) return false;
        return isShow == spaceItem.isShow;
    }

    @Override
    public int hashCode() {
        int result = heightPx;
        result = 31 * result + (isShow ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.heightPx);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
    }

    protected SpaceItem(Parcel in) {
        super(in);
        this.heightPx = in.readInt();
        this.isShow = in.readByte() != 0;
    }

    public static final Creator<SpaceItem> CREATOR = new Creator<SpaceItem>() {
        @Override
        public SpaceItem createFromParcel(Parcel source) {
            return new SpaceItem(source);
        }

        @Override
        public SpaceItem[] newArray(int size) {
            return new SpaceItem[size];
        }
    };
}
