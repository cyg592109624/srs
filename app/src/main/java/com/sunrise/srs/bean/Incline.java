package com.sunrise.srs.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ChuHui on 2017/11/23.
 */

public class Incline implements Parcelable {
    private float inch = 0.00f;

    public Incline() {
    }

    protected Incline(Parcel in) {
        inch = in.readFloat();
    }

    public static final Creator<Incline> CREATOR = new Creator<Incline>() {
        @Override
        public Incline createFromParcel(Parcel in) {
            return new Incline(in);
        }

        @Override
        public Incline[] newArray(int size) {
            return new Incline[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(inch);
    }
}
