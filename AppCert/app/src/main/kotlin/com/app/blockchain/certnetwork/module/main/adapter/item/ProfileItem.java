package com.app.blockchain.certnetwork.module.main.adapter.item;

import android.os.Parcel;

import com.app.blockchain.certnetwork.module.main.adapter.operator.CertItemCreator;
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem;


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class ProfileItem extends BaseItem {

    private String name;
    private String mobileNumber;
    private String address;

    public ProfileItem(int id){
        super(id, CertItemCreator.TYPE_PROFILE_ITEM);
    }


    public ProfileItem setName(String name) {
        this.name = name;
        return this;
    }

    public ProfileItem setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public ProfileItem setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.address);
    }

    protected ProfileItem(Parcel in) {
        super(in);
        this.name = in.readString();
        this.mobileNumber = in.readString();
        this.address = in.readString();
    }

    public static final Creator<ProfileItem> CREATOR = new Creator<ProfileItem>() {
        @Override
        public ProfileItem createFromParcel(Parcel source) {
            return new ProfileItem(source);
        }

        @Override
        public ProfileItem[] newArray(int size) {
            return new ProfileItem[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileItem that = (ProfileItem) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getMobileNumber() != null ? !getMobileNumber().equals(that.getMobileNumber()) : that.getMobileNumber() != null)
            return false;
        return getAddress() != null ? getAddress().equals(that.getAddress()) : that.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getMobileNumber() != null ? getMobileNumber().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
