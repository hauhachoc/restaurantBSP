package com.mkeys.restaurantbsp.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.presentation.login.LoginFragment;

/**
 * Created by hautran on 21/08/17.
 */

public class TopActivity extends AbstractActivity{
    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_top;
    }

    @Override
    public int getViewContainerId() {
        return R.id.fragmentContainer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        replaceFragment(new LoginFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commitAllowingStateLoss();
    }
}
