package com.mkeys.restaurantbsp;

import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mkeys.restaurantbsp.utils.CustomSharedPreferences;

import java.util.Locale;

/**
 * Created by hautran on 17/08/17.
 */

public class FoodsApplication extends Application {

    private static FoodsApplication sharedInstance;

    public static FoodsApplication getSharedInstance() {
        return sharedInstance;
    }

    private static FirebaseAuth mAuth;
    public static FirebaseAuth getFirebaseAuthInstance() {
        return mAuth;
    }

    private static FirebaseUser currentUser;
    public static FirebaseUser getFirebaseUserInstance() {
        return currentUser;
    }

    private static FirebaseDatabase database;
    public static FirebaseDatabase getFirebaseDatabaseInstance() {
        return database;
    }

    private static DatabaseReference myRef;
    public static DatabaseReference getDatabaseReferenceInstance() {
        return myRef;
    }

    private Locale locale = null;

    @Override
    public void onCreate() {
        super.onCreate();
        CustomSharedPreferences.init(this);
        sharedInstance = this;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

