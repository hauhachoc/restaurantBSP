package com.mkeys.restaurantbsp.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkeys.restaurantbsp.RestaurantApplication;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;
import com.mkeys.restaurantbsp.views.LoginView;

/**
 * Created by hautran on 21/08/17.
 */

public class LoginPresenter {
    LoginView view;
    private FirebaseAuth mAuth;
    private AbstractActivity activity;

    public LoginPresenter (AbstractActivity ac, LoginView v){
        activity = ac;
        this.view = v;
    }

    public void onLoginEvent(){
        view.showLoading();
        BaseUser user = view.getData();
        mAuth = RestaurantApplication.getFirebaseAuthInstance();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.dismissLoading();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user!=null){
                                view.moveToNextScreen();
                            }else {
                                view.onLoginFailed("Login failed");
                            }
                        } else {
                           view.onLoginFailed(task.getException().toString());
                        }
                    }
                });
    }
}
