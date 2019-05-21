package com.example.manasatpc.bloadbank.u.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {
    private List<String> selectedBloodTypesIds = new ArrayList<>();
    private Context context;
    private ArrayList<DataBloodTypes> getNotificationsSettingsList = new ArrayList<>();
    private ArrayList<GovernoratesData> governoratesDataArrayList = new ArrayList<>();


    public List<Integer> selectBloodTypesIds = new ArrayList<>();

    public AdapterNotification(Context context, ArrayList<DataBloodTypes> getNotificationsSettingsList, List<String> selectedBloodTypesIds) {
        this.context = context;
        this.getNotificationsSettingsList = getNotificationsSettingsList;
        this.selectedBloodTypesIds = selectedBloodTypesIds;
    }
   /* public AdapterNotification(Context context, ArrayList<GovernoratesData> governoratesDataArrayList, List<String> selectedBloodTypesIds) {
        this.context = context;
        this.governoratesDataArrayList = governoratesDataArrayList;
        this.selectedBloodTypesIds = selectedBloodTypesIds;
    }*/

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification, parent, false);
        final NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, final int position) {
        holder.CBRecyclerView.setText(getNotificationsSettingsList.get(position).getName());
        for (int i = 0; i < selectedBloodTypesIds.size(); i++) {
            if (getNotificationsSettingsList.get(position).getId().equals(selectedBloodTypesIds.get(i))) {
                holder.CBRecyclerView.setChecked(true);
                selectBloodTypesIds.add(getNotificationsSettingsList.get(position).getId());
            }
        }
        holder.CBRecyclerView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectBloodTypesIds.add(getNotificationsSettingsList.get(position).getId());
                } else {
                    for (int i = 0; i < selectBloodTypesIds.size(); i++) {
                        if (getNotificationsSettingsList.get(position).getId().equals(selectBloodTypesIds.get(i))) {
                            selectBloodTypesIds.remove(i);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (getNotificationsSettingsList != null) {
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
            ButterKnife.bind(this, view);
        }
    }

}