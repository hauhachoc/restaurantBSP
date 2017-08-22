package com.mkeys.restaurantbsp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractFragment;
import com.mkeys.restaurantbsp.presentation.foods.MenuFoodActivity;
import com.mkeys.restaurantbsp.presenter.LoginPresenter;
import com.mkeys.restaurantbsp.views.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hautran on 21/08/17.
 */

public class LoginFragment extends AbstractFragment implements LoginView {

    @BindView(R.id.edtEmail)
    AppCompatEditText edtEmail;

    @BindView(R.id.edtPassword)
    AppCompatEditText edtPassword;

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @OnClick(R.id.btnLogin)
    public void onLogin() {
        presenter.onLoginEvent();

    }

    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;

    @OnClick(R.id.btnSignUp)
    public void onSignUp() {
        toSignUp();
    }

    private void toSignUp() {
        addToBackStack(new SignUpFragment());
    }

    private LoginPresenter presenter;

    @Override
    protected void initTittleBar() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(mActivity, this);
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViewFragment() {

    }

    @Override
    public void onLoginFailed(String mess) {
        dialogHelper.alert(null, mess);
    }

    @Override
    public BaseUser getData() {
        BaseUser user = new BaseUser();
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        return user;
    }

    @Override
    public void showLoading() {
        dialogHelper.showProgress();
    }

    @Override
    public void dismissLoading() {
        dialogHelper.dismissProgress();
    }

    @Override
    public void moveToNextScreen() {
        startActivity(new Intent(mActivity, MenuFoodActivity.class));
        mActivity.finish();
    }
}
