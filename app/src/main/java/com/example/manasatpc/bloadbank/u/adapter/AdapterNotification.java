package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.manasatpc.bloadbank.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {
    private Context context;

    private ArrayList<String> getNotificationsSettingsList = new ArrayList<>();

    public AdapterNotification(Context context, ArrayList<String> getNotificationsSettingsList) {
        this.context = context;
        this.getNotificationsSettingsList = getNotificationsSettingsList;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification, parent, false);
        final NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.CBRecyclerView.setText(getNotificationsSettingsList.get(position));



    }

    @Override
    public int getItemCount() {
        if (getNotificationsSettingsList != null){
            return getNotificationsSettingsList.size();
        }
        return 0;
    }

    @OnClick(R.id.CB_Recycler_View)
    public void onViewClicked() {
    }


    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.CB_Recycler_View)
        CheckBox CBRecyclerView;


        public NotificationViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);
        }
    }

}