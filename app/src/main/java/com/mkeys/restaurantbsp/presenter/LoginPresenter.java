package com.mkeys.restaurantbsp.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;
import com.mkeys.restaurantbsp.views.LoginView;

/**
 * Created by hautran on 21/08/17.
 */

public class LoginPresenter {
    LoginView view;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private AbstractActivity activity;

    public LoginPresenter(AbstractActivity ac, LoginView v) {
        activity = ac;
        this.view = v;
        mAuth = FoodsApplication.getFirebaseAuthInstance();
        database = FoodsApplication.getFirebaseDatabaseInstance();
    }

    public void onLoginEvent() {
        view.showLoading();
        BaseUser user = view.getData();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.dismissLoading();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                onAddUserData(user);
                            } else {
                                view.onLoginFailed("Login failed");
                            }
                        } else {
                            view.onLoginFailed(task.getException().toString());
                        }
                    }
                });
    }

    public void onAddUserData(FirebaseUser user) {
        myRef = database.getReference("users/" + user.getUid());
        BaseUser baseUser = new BaseUser();
        baseUser.setEmail(user.getEmail());
        baseUser.setPassword(user.getUid());
        baseUser.setOnline(true);
        myRef.setValue(baseUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    view.moveToNextScreen();
                } else {
                    view.onLoginFailed(databaseError.getMessage().toString());
                }
            }
        });
    }
}
