package com.mkeys.restaurantbsp.presentation.login;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractFragment;
import com.mkeys.restaurantbsp.presenter.SignUpPresenter;
import com.mkeys.restaurantbsp.views.SignUpView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends AbstractFragment implements SignUpView {

    @BindView(R.id.edtEmail)
    AppCompatEditText edtEmail;

    @BindView(R.id.edtPassword)
    AppCompatEditText edtPassword;

    @BindView(R.id.btnSignUp)
    AppCompatButton btnSignUp;

    @OnClick(R.id.btnSignUp)
    public void onSignUp() {
        presenter.onRegister();
    }

    SignUpPresenter presenter;


    @Override
    protected void initTittleBar() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(mActivity, this);
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void initViewFragment() {

    }

    @Override
    public void moveToNextScreen() {
        this.clearBackStack();
    }

    @Override
    public void onRegisterSuccess() {
    }

    @Override
    public void onRegisterFailed(String mess) {
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


}
