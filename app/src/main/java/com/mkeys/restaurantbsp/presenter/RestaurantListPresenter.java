package com.mkeys.restaurantbsp.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mkeys.restaurantbsp.RestaurantApplication;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;
import com.mkeys.restaurantbsp.views.RestaurantListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by hautran on 21/08/17.
 */

public class RestaurantListPresenter {
    RestaurantListView view;
    AbstractActivity activity;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public RestaurantListPresenter (AbstractActivity ac, RestaurantListView v){
        this.activity = ac;
        this.view = v;
    }

    public void onAddRestaurant(){
        view.onLoading();
        Food food = view.getFoodData();
        database = RestaurantApplication.getFirebaseDatabaseInstance();
        myRef = database.getReference("grocery-items/" + food.getName());

        myRef.setValue(food, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError == null) {
                    view.onGetRestaurantSuccess();
                } else {
                    view.onGetRestaurantFailed(databaseError.getMessage());
                }
            }
        });
    }

    public void getDataToDisplay(){
        view.onLoading();
        view.clearFoodData();
        ArrayList<Food> restaurants = new ArrayList<>();
        database = RestaurantApplication.getFirebaseDatabaseInstance();
        myRef = database.getReference("grocery-items");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "" + postSnapshot.getValue().toString());
                    Food post = postSnapshot.getValue(Food.class);
                    restaurants.add(post);
                }
                view.onDismissLoading();
                view.showData(restaurants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                view.onDismissLoading();
            }
        });
    }
}
