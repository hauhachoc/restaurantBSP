package com.mkeys.restaurantbsp.views;

import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.models.Food;

import java.util.ArrayList;

/**
 * Created by hautran on 21/08/17.
 */

public interface RestaurantListView extends BaseView {
    public void onGetRestaurantSuccess();
    public void onGetRestaurantFailed(String mess);
    public void onLoading();
    public void onDismissLoading();
    public Food getFoodData();
    public ArrayList<Food> getFoodsData();
    public void showData(ArrayList<Food> foods);
    public void showUsersData(ArrayList<BaseUser> users);
    public void showDataUpdated(ArrayList<Food> foods);
    public void clearFoodData();
    public void onDeleteChannelSuccess(int pos);
}
