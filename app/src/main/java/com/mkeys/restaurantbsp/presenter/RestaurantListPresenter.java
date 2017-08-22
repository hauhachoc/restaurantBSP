package com.mkeys.restaurantbsp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.models.BaseUser;
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

    public RestaurantListPresenter(AbstractActivity ac, RestaurantListView v) {
        this.activity = ac;
        this.view = v;
        database = FoodsApplication.getFirebaseDatabaseInstance();
    }

    public void onAddFood() {
        view.onLoading();
        Food food = view.getFoodData();
        ArrayList<Food> restaurants = new ArrayList<>();
        myRef = database.getReference("grocery-items/" + food.getName());
        myRef.setValue(food, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError == null) {

                    Query myRef = databaseReference.getDatabase().getReference("grocery-items").orderByChild("completed");
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
                } else {
                    view.onGetRestaurantFailed(databaseError.getMessage());
                }
            }
        });
    }

    public void getDataToDisplay() {
        view.onLoading();
        view.clearFoodData();
        ArrayList<Food> restaurants = new ArrayList<>();
        Query myRef = database.getReference("grocery-items").orderByChild("completed");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getUserToDisplay() {
        view.onLoading();
        ArrayList<BaseUser> pps = new ArrayList<>();
        myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "" + postSnapshot.getValue().toString());
                    BaseUser post = postSnapshot.getValue(BaseUser.class);
                    if (post.isOnline()){
                        pps.add(post);
                    }
                }
                view.onDismissLoading();
                view.showUsersData(pps);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                view.onDismissLoading();
            }
        });
    }

    public void onDeleteFood(int pos) {
        myRef = database.getReference("grocery-items");
        ArrayList<Food> food = view.getFoodsData();
        myRef.child(food.get(pos).getName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                view.onDeleteChannelSuccess(pos);
            }
        });
    }

    public void onUpdateFood(Food food) {
        view.onLoading();
        myRef = database.getReference("grocery-items/" + food.getName());
        ArrayList<Food> restaurants = new ArrayList<>();

        myRef.setValue(food, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError == null) {
                    Query myRef = databaseReference.getDatabase().getReference("grocery-items").orderByChild("completed");
                    myRef.keepSynced(true);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "" + dataSnapshot.getChildrenCount());

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Log.d(TAG, "" + postSnapshot.getValue().toString());
                                Food post = postSnapshot.getValue(Food.class);
                                restaurants.add(post);
                            }
                            view.showDataUpdated(restaurants);
                            view.onDismissLoading();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                            view.onDismissLoading();
                        }
                    });
                } else {
                    view.onGetRestaurantFailed(databaseError.getMessage());
                }
            }
        });
    }

}
