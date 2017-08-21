package com.mkeys.restaurantbsp.views;

import com.mkeys.restaurantbsp.models.Food;

/**
 * Created by hautran on 21/08/17.
 */

public interface RestaurantListView extends BaseView {
    public void onGetRestaurantSuccess();
    public void onGetRestaurantFailed(String mess);
    public void onLoading();
    public void onDismissLoading();
    public Food getFoodData();
}
