package com.mkeys.restaurantbsp.models;

/**
 * Created by hautran on 21/08/17.
 */

public class Food extends BaseUser {

    public String addedByUser;
    public String name;
    public boolean completed;

    public Food() {

    }

    public Food(String namee, String byUser) {
        this.name = namee;
        this.addedByUser = byUser;
    }

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
}
