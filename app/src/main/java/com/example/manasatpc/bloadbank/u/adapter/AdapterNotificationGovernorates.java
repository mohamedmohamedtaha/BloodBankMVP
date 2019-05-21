package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.notification.getnotificationssettings.GetNotificationSsettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdapterNotificationGovernorates extends RecyclerView.Adapter<AdapterNotificationGovernorates.NotificationViewHolder> {

    private Context context;
    private ArrayList<GovernoratesData> governoratesDataArrayList = new ArrayList<>();
    private ArrayList<GetNotificationSsettings> getNotificationSsettingsArrayList = new ArrayList<>();
    SparseBooleanArray itemSateArray = new SparseBooleanArray();


    public AdapterNotificationGovernorates(Context context, ArrayList<GovernoratesData> governoratesDataArrayList, List<Integer> selectedGavermentIds) {
        this.context = context;
        this.governoratesDataArrayList = governoratesDataArrayList;
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
        bind(holder, position);
    }

    void bind(NotificationViewHolder holder, int position) {
        if (!itemSateArray.get(position, false)) {
            holder.CBRecyclerView.setChecked(false);
        } else {
            holder.CBRecyclerView.setChecked(true);
        }
        holder.CBRecyclerView.setText(governoratesDataArrayList.get(position).getName());
    }

    void loadItems(ArrayList<GovernoratesData> governoratesDataArrayList) {
        this.governoratesDataArrayList = governoratesDataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (governoratesDataArrayList != null) {
            return governoratesDataArrayList.size();
        }
        return 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.CB_Recycler_View)
        CheckBox CBRecyclerView;

        public NotificationViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

}

