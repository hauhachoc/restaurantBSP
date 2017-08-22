package com.mkeys.restaurantbsp.presentation.foods;

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
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.adapter.DividerItemDecoration;
import com.mkeys.restaurantbsp.adapter.RestaurantAdapter;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractFragment;
import com.mkeys.restaurantbsp.presentation.foods.detail.DetaiFoodFragment;
import com.mkeys.restaurantbsp.presenter.RestaurantListPresenter;
import com.mkeys.restaurantbsp.views.RestaurantListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hautran on 21/08/17.
 */

public class FoodsFragment extends AbstractFragment implements RestaurantListView , RestaurantAdapter.setChannelItemClick{

    @BindView(R.id.reRestaurant)
    RecyclerView reRestaurant;

    private RestaurantAdapter adapter;
    private ArrayList<Food> restaurants = new ArrayList<>();
    RestaurantListPresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String newRes = "";
    private ArrayList<BaseUser> users;

    @Override
    public void moveToNextScreen() {

    }

    @Override
    protected void initTittleBar() {
        mActivity.setTitle("0");
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
//        fakeData();
        adapter = new RestaurantAdapter(mActivity);
        adapter.setChannelItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        reRestaurant.setLayoutManager(layoutManager);
        reRestaurant.setAdapter(adapter);
        reRestaurant.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        adapter.setData(restaurants);
        reRestaurant.setAdapter(adapter);
        restaurants.clear();
        adapter.clearData();
        presenter.getDataToDisplay();
        presenter.getUserToDisplay();
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
        mAuth = FoodsApplication.getFirebaseAuthInstance();
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
        presenter.onAddFood();
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
        food.setCompleted(false);
        food.setName(newRes);
        if (currentUser != null) {
            food.setAddedByUser(currentUser.getEmail());
        }
        return food;
    }

    @Override
    public ArrayList<Food> getFoodsData() {
        return adapter.getData();
    }

    @Override
    public void showData(ArrayList<Food> foods) {
        adapter.clearData();
        adapter.setData(foods);
        adapter.notifyDataSetChanged();
        mActivity.setTitle(""+checkCheckedData(foods));
    }

    @Override
    public void showUsersData(ArrayList<BaseUser> userss) {
        users = userss;
    }

    @Override
    public void showDataUpdated(ArrayList<Food> foods) {
        adapter.clearData();
        adapter.setData(foods);
        adapter.notifyDataSetChanged();
        mActivity.setTitle(""+checkCheckedData(foods));
    }

    private int checkCheckedData(ArrayList<Food> foods){
        if (foods.size()>0){
            int y = 0;
            for (int i = 0 ; i < foods.size(); i++){
                Food food = foods.get(i);
                if (food.isCompleted()){
                    y++;
                }
            }
            return y;
        }else {
            return 0;
        }
    }

    @Override
    public void clearFoodData() {
        restaurants.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteChannelSuccess(int pos) {
        adapter.notifyItemRemoved(pos);
        adapter.notifyDataSetChanged();
        dialogHelper.alert("", "Deleted");
    }

    @Override

    public void onResume() {
        super.onResume();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void setChannelItemClickListener(int pos) {
        Bundle b = new Bundle();
        b.putParcelableArrayList("bundle", users);
        addToBackStack(new DetaiFoodFragment(), b);
    }

    @Override
    public void setChannelDeleteClickListener(int position) {
        presenter.onDeleteFood(position);
    }

    @Override
    public void setIsChecked(Food food) {
        Log.d(TAG, "updated:"+food.getName());
        presenter.onUpdateFood(food);
    }
}
