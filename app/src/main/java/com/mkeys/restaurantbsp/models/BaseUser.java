package com.mkeys.restaurantbsp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hautran on 21/08/17.
 */

public class BaseUser implements Parcelable{

    public String email;
    public String password;
    public boolean isOnline;

    public BaseUser() {
    }

    public BaseUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public BaseUser(String email, String password, boolean isOnline) {
        this.email = email;
        this.password = password;
        this.isOnline = isOnline;
    }

    public BaseUser(String email, boolean isOnline) {
        this.email = email;
        this.isOnline = isOnline;
    }

    protected BaseUser(Parcel in) {
        email = in.readString();
        password = in.readString();
        isOnline = in.readByte() != 0;
    }

    public static final Creator<BaseUser> CREATOR = new Creator<BaseUser>() {
        @Override
        public BaseUser createFromParcel(Parcel in) {
            return new BaseUser(in);
        }

        @Override
        public BaseUser[] newArray(int size) {
            return new BaseUser[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeByte((byte) (isOnline ? 1 : 0));
    }
}
