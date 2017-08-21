package com.mkeys.restaurantbsp.presenter;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mkeys.restaurantbsp.RestaurantApplication;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;
import com.mkeys.restaurantbsp.views.RestaurantListView;

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
}
