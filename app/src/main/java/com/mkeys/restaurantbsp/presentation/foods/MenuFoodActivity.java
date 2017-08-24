package com.mkeys.restaurantbsp.presentation.foods;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;

public class MenuFoodActivity extends AbstractActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

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
        mAuth = FoodsApplication.getFirebaseAuthInstance();
        database = FoodsApplication.getFirebaseDatabaseInstance();
        myRef = FoodsApplication.getDatabaseReferenceInstance();
        replaceFragment(new FoodsFragment(), false, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentUser!=null){
            updateUserStt(currentUser);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateUserStt(FirebaseUser user){
        myRef = database.getReference("users/" + user.getUid());
        BaseUser baseUser = new BaseUser();
        baseUser.setEmail(user.getEmail());
        baseUser.setPassword(user.getUid());
        baseUser.setOnline(false);
        myRef.setValue(baseUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError == null) {
//                    view.moveToNextScreen();
//                } else {
//                    view.onLoginFailed(databaseError.getMessage().toString());
//                }
            }
        });
    }
}
