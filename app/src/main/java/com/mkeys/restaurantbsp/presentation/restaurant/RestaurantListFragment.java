package com.mkeys.restaurantbsp.presentation.restaurant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.RestaurantApplication;
import com.mkeys.restaurantbsp.adapter.DividerItemDecoration;
import com.mkeys.restaurantbsp.adapter.RestaurantAdapter;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractFragment;
import com.mkeys.restaurantbsp.presenter.RestaurantListPresenter;
import com.mkeys.restaurantbsp.views.RestaurantListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hautran on 21/08/17.
 */

public class RestaurantListFragment extends AbstractFragment implements RestaurantListView {

    @BindView(R.id.reRestaurant)
    RecyclerView reRestaurant;

    private RestaurantAdapter adapter;
    private ArrayList<Food> restaurants = new ArrayList<>();
    RestaurantListPresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String newRes = "";

    @Override
    public void moveToNextScreen() {

    }

    @Override
    protected void initTittleBar() {
        mActivity.setTitle("10");
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_restaurant;
    }

    @Override
    protected void initViewFragment() {
        fakeData();
        adapter = new RestaurantAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        reRestaurant.setLayoutManager(layoutManager);
        reRestaurant.setAdapter(adapter);
        reRestaurant.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        adapter.setData(restaurants);
        reRestaurant.setAdapter(adapter);
        restaurants.clear();
        adapter.clearData();
        presenter.getDataToDisplay();
    }

    private void fakeData() {
        for (int i = 0; i < 10; i++) {
            Food food = new Food();
            food.setName("food" + i);
            food.setAddedByUser("email" + i);
            food.setCompleted(true);
            restaurants.add(food);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = RestaurantApplication.getFirebaseAuthInstance();
        presenter = new RestaurantListPresenter(mActivity, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mActivity.getMenuInflater().inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add:
                createDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Grocery Item");
        dialogBuilder.setMessage("Add new item");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                newRes = edt.getText().toString();
                addRestaurant();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addRestaurant() {
        presenter.onAddRestaurant();
    }

    @Override
    public void onGetRestaurantSuccess() {
        newRes = "";
        dialogHelper.alert(null, "Added new item");
    }

    @Override
    public void onGetRestaurantFailed(String mess) {
        dialogHelper.alert(null, mess);
    }

    @Override
    public void onLoading() {
        dialogHelper.showProgress();
    }

    @Override
    public void onDismissLoading() {
        dialogHelper.dismissProgress();
    }

    @Override
    public Food getFoodData() {
        Food food = new Food();
        food.setCompleted(true);
        food.setName(newRes);
        if (currentUser != null) {
            food.setAddedByUser(currentUser.getEmail());
        }
        return food;
    }

    @Override
    public void showData(ArrayList<Food> foods) {
        adapter.clearData();
        adapter.setData(foods);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearFoodData() {
        restaurants.clear();
        adapter.notifyDataSetChanged();
    }

    @Override

    public void onResume() {
        super.onResume();
        currentUser = mAuth.getCurrentUser();
    }
}
