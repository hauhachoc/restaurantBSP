package com.mkeys.restaurantbsp.views;

import com.mkeys.restaurantbsp.models.BaseUser;

/**
 * Created by hautran on 21/08/17.
 */

public interface SignUpView extends BaseView{
    public void onRegisterSuccess();
    public void onRegisterFailed(String mess);
    public BaseUser getData();
    public void showLoading();
    public void dismissLoading();
}
