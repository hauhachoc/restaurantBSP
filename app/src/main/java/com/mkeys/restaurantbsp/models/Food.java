package com.mkeys.restaurantbsp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hautran on 21/08/17.
 */

public class Food extends BaseUser implements Parcelable{

    public String addedByUser;
    public String name;
    public boolean completed;

    public Food() {

    }

    public Food(String namee, String byUser) {
        this.name = namee;
        this.addedByUser = byUser;
    }

    protected Food(Parcel in) {
        addedByUser = in.readString();
        name = in.readString();
        completed = in.readByte() != 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(String addedByUser) {
        this.addedByUser = addedByUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(addedByUser);
        parcel.writeString(name);
        parcel.writeByte((byte) (completed ? 1 : 0));
    }
}
