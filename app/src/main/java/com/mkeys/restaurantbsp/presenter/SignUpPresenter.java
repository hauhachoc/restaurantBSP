package com.mkeys.restaurantbsp.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;
import com.mkeys.restaurantbsp.views.SignUpView;

/**
 * Created by hautran on 21/08/17.
 */

public class SignUpPresenter {
    private SignUpView view;
    private FirebaseAuth mAuth;
    private AbstractActivity activity;

    public SignUpPresenter(AbstractActivity ac,SignUpView v){
        this.activity = ac;
        this.view = v;
    }

    public void onRegister(){
        view.showLoading();
        BaseUser baseUser = view.getData();
        mAuth = FoodsApplication.getFirebaseAuthInstance();
        mAuth.createUserWithEmailAndPassword(baseUser.getEmail(), baseUser.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.dismissLoading();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user!=null){
                                view.moveToNextScreen();
                            }else {
                                view.onRegisterFailed("Please try again");
                            }
                        } else {
                            view.onRegisterFailed(task.getException().toString());
                        }
                    }
                });
    }
}
