package com.mkeys.restaurantbsp.adapter;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.mkeys.restaurantbsp.R;
import com.mkeys.restaurantbsp.models.Food;
import com.mkeys.restaurantbsp.presentation.AbstractActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public class RestaurantAdapter extends AbstractAdapter {

    public ArrayList<Food> channels;
    public AbstractActivity activity;
    public boolean isCheckMode ;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public RestaurantAdapter(AbstractActivity mActivity) {
        super(mActivity);
        channels = new ArrayList<>();
        isCheckMode = false;
    }

    public void setData(ArrayList<Food> data) {
        this.channels = data;
    }

    public void clearData() {
        this.channels.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Food> getData() {
        return channels;
    }

    public interface setChannelItemClick {
        public void setChannelItemClickListener(int pos);

        public void setChannelDeleteClickListener(int position);
        public void setIsChecked(Food food);
    }

    public void setChannelItemClickListener(setChannelItemClick lis) {
        this.listener = lis;
    }

    public setChannelItemClick listener;

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
        View view = mInflater.inflate(R.layout.food_item, parent, false);
        return new ChannelsHolder(view);
    }

    @Override
    protected void OnBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ChannelsHolder view = (ChannelsHolder) holder;
        Food restaurant = channels.get(position);
        view.tvTitle.setText(restaurant.getName());
        view.tvEmail.setText(restaurant.getAddedByUser());
        view.cb.setChecked(restaurant.isCompleted());

        view.content_layout.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.setChannelItemClickListener(position);
            }
        });

        view.cb.setOnCheckedChangeListener((compoundButton, b) -> {
            restaurant.setCompleted(b);
        });

        view.cb.setOnClickListener(view1 -> {
            listener.setIsChecked(restaurant);
        });

        view.delete_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setChannelDeleteClickListener(position);
            }
        });

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

    static class ChannelsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;

        @BindView(R.id.tvEmail)
        AppCompatTextView tvEmail;

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipe_layout;

        @BindView(R.id.delete_layout)
        FrameLayout delete_layout;

        @BindView(R.id.content_layout)
        FrameLayout content_layout;

        @BindView(R.id.check_layout)
        FrameLayout check_layout;

        @BindView(R.id.cb)
        CheckBox cb;


        public ChannelsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
