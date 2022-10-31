package com.yt.yunxiaoweimusic.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Qua implements Parcelable {
    public String PP;
    public String QV;
    public String PL;
    public String PR;
    public String VE;
    public String VN;
    public String DE;

    public Qua() {

    }

    protected Qua(Parcel in) {
        PP = in.readString();
        QV = in.readString();
        PL = in.readString();
        PR = in.readString();
        VE = in.readString();
        VN = in.readString();
        DE = in.readString();
    }

    public static final Creator<Qua> CREATOR = new Creator<Qua>() {
        @Override
        public Qua createFromParcel(Parcel in) {
            return new Qua(in);
        }

        @Override
        public Qua[] newArray(int size) {
            return new Qua[size];
        }
    };


    public void setPP(String PP) {
        this.PP = PP;
    }


    public void setQV(String QV) {
        this.QV = QV;
    }


    public void setPL(String PL) {
        this.PL = PL;
    }


    public void setPR(String PR) {
        this.PR = PR;
    }


    public void setVE(String VE) {
        this.VE = VE;
    }


    public void setVN(String VN) {
        this.VN = VN;
    }


    public void setDE(String DE) {
        this.DE = DE;
    }

    @Override
    public String toString() {
        return "Qua{" +
                "PP='" + PP + '\'' +
                ", QV='" + QV + '\'' +
                ", PL='" + PL + '\'' +
                ", PR='" + PR + '\'' +
                ", VE='" + VE + '\'' +
                ", VN='" + VN + '\'' +
                ", DE='" + DE + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PP);
        dest.writeString(QV);
        dest.writeString(PL);
        dest.writeString(PR);
        dest.writeString(VE);
        dest.writeString(VN);
        dest.writeString(DE);
    }
}
