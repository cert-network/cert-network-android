package com.app.blockchain.certnetwork.module.main.adapter.item;

import android.os.Parcel;

import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem;
import com.app.blockchain.certnetwork.module.main.adapter.operator.CertItemCreator;


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class CertCardItem extends BaseItem {

    private String image;
    private String name;
    private String certificateName;
    private String category;
    private String approveBy;
    private boolean isApprove;

    public CertCardItem(int id){
        super(id, CertItemCreator.TYPE_CERT_ITEM);
    }

    public CertCardItem setImage(String image) {
        this.image = image;
        return this;
    }

    public CertCardItem setName(String name) {
        this.name = name;
        return this;
    }

    public CertCardItem setCertificateName(String certificateName) {
        this.certificateName = certificateName;
        return this;
    }

    public CertCardItem setCategory(String category) {
        this.category = category;
        return this;
    }

    public CertCardItem setApproveBy(String approveBy) {
        this.approveBy = approveBy;
        return this;
    }

    public CertCardItem setApprove(boolean isApprove) {
        this.isApprove = isApprove;
        return this;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public String getCategory() {
        return category;
    }

    public String getApproveBy() {
        return approveBy;
    }

    public Boolean isApprove() {
        return isApprove;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.image);
        dest.writeString(this.name);
        dest.writeString(this.certificateName);
        dest.writeString(this.category);
        dest.writeString(this.approveBy);
        dest.writeByte(this.isApprove ? (byte) 1 : (byte) 0);
    }

    protected CertCardItem(Parcel in) {
        super(in);
        this.image = in.readString();
        this.name = in.readString();
        this.certificateName = in.readString();
        this.category = in.readString();
        this.approveBy = in.readString();
        this.isApprove = in.readByte() != 0;
    }

    public static final Creator<CertCardItem> CREATOR = new Creator<CertCardItem>() {
        @Override
        public CertCardItem createFromParcel(Parcel source) {
            return new CertCardItem(source);
        }

        @Override
        public CertCardItem[] newArray(int size) {
            return new CertCardItem[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CertCardItem that = (CertCardItem) o;

        if (isApprove != that.isApprove) return false;
        if (getImage() != null ? !getImage().equals(that.getImage()) : that.getImage() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getCertificateName() != null ? !getCertificateName().equals(that.getCertificateName()) : that.getCertificateName() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null)
            return false;
        return getApproveBy() != null ? getApproveBy().equals(that.getApproveBy()) : that.getApproveBy() == null;
    }

    @Override
    public int hashCode() {
        int result = getImage() != null ? getImage().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCertificateName() != null ? getCertificateName().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getApproveBy() != null ? getApproveBy().hashCode() : 0);
        result = 31 * result + (isApprove ? 1 : 0);
        return result;
    }
}
