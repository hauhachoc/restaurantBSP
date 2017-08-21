package com.mkeys.restaurantbsp.views;

import com.mkeys.restaurantbsp.models.BaseUser;

/**
 * Created by hautran on 21/08/17.
 */

public interface LoginView extends BaseView{
    public void onLoginFailed(String mess);
    public BaseUser getData();
    public void showLoading();
    public void dismissLoading();
}
