package com.mkeys.restaurantbsp.presentation.foods.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.FoodsApplication;
import com.mkeys.restaurantbsp.adapter.DividerItemDecoration;
import com.mkeys.restaurantbsp.adapter.PeopleAdapter;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.presentation.AbstractFragment;
import com.mkeys.restaurantbsp.presentation.TopActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetaiFoodFragment extends AbstractFragment {


    @BindView(R.id.rePeople)
    RecyclerView rePeople;

    PeopleAdapter adapter;
    ArrayList<BaseUser> users = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void initTittleBar() {
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mActivity.setTitle("Online");
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_detai_restaurant;
    }

    @Override
    protected void initViewFragment() {
        adapter = new PeopleAdapter(mActivity);
//        adapter.setChannelItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        rePeople.setLayoutManager(layoutManager);
        rePeople.setAdapter(adapter);
        rePeople.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        adapter.setData(users);
        rePeople.setAdapter(adapter);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<BaseUser> userss = bundle.getParcelableArrayList("bundle");
            users = userss;
            adapter.setData(users);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FoodsApplication.getFirebaseAuthInstance();
        database = FoodsApplication.getFirebaseDatabaseInstance();
        myRef = FoodsApplication.getDatabaseReferenceInstance();
    }

    @Override
    public void moveToNextScreen() {
        startActivity(new Intent(mActivity, TopActivity.class));
        mActivity.finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mActivity.getMenuInflater().inflate(R.menu.menu_out, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mActivity.onBackPressed();
                return true;

            case R.id.sign_out:
                logOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut(){
        FirebaseAuth auth = FoodsApplication.getFirebaseAuthInstance();
        auth.signOut();
        moveToNextScreen();
        if (currentUser!=null){
            updateUserStt(currentUser);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        currentUser = mAuth.getCurrentUser();
    }
}
