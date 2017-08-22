package com.mkeys.restaurantbsp.adapter;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.models.BaseUser;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hautran on 22/08/17.
 */

public class PeopleAdapter extends AbstractAdapter {

    public ArrayList<BaseUser> channels;
    public AbstractActivity activity;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public PeopleAdapter(AbstractActivity mActivity) {
        super(mActivity);
        channels = new ArrayList<>();
    }

    public void setData(ArrayList<BaseUser> data) {
        this.channels = data;
    }

    public void clearData() {
        this.channels.clear();
        notifyDataSetChanged();
    }

    public ArrayList<BaseUser> getData() {
        return channels;
    }

    public interface setChannelItemClick {
        public void setChannelItemClickListener(int pos);

        public void setChannelDeleteClickListener(int position);
        public void setIsChecked(Food food);
    }

    public void setChannelItemClickListener(RestaurantAdapter.setChannelItemClick lis) {
        this.listener = lis;
    }

    public RestaurantAdapter.setChannelItemClick listener;

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.people_item, parent, false);
        return new PeoplesHolder(view);
    }

    @Override
    protected void OnBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        PeoplesHolder view = (PeoplesHolder) holder;
        BaseUser restaurant = channels.get(position);
        view.tvEmail.setText(restaurant.getEmail());

    }

    @Override
    protected int setItemCount() {
        if (channels == null || channels.size() == 0) {
            return 0;
        }
        return channels.size();
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    @Override
    protected int setItemViewType(int position) {
        return position;
    }

    static class PeoplesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvEmail)
        AppCompatTextView tvEmail;

        public PeoplesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
