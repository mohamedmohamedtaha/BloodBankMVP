package com.example.manasatpc.bloadbank.u.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.GeneralResponseData;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {
    private List<String> oldBloodTypes = new ArrayList<>();
    private Context context;
    private Activity activity;
    private List<GeneralResponseData> bloods = new ArrayList<>();
    public List<Integer> Ids = new ArrayList<>();

    public AdapterNotification(Context context, Activity activity, List<GeneralResponseData> bloods, List<String> oldBloodTypes) {
        this.context = context;
        this.activity = activity;
        this.bloods = bloods;
        this.oldBloodTypes = oldBloodTypes;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification, parent, false);
        final NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        setData(holder,position);
        setAction(holder,position);
       /* holder.CBRecyclerView.setText(getNotificationsSettingsList.get(position).getName());
        for (int i = 0; i < selectedIds.size(); i++) {
            if (String.valueOf(getNotificationsSettingsList.get(position).getId()).equals(selectedIds.get(i))) {
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
        });*/
//        holder.CBRecyclerView.setText(getBloodTypesArrayList.get(position).getName());

    }
    private void setData(NotificationViewHolder holder, int position){
        try{
            holder.CBRecyclerView.setChecked(false);
        for (int i = 0; i < oldBloodTypes.size(); i++) {
            if (oldBloodTypes.get(i).equals((String.valueOf(bloods.get(position).getId())))) {
                holder.CBRecyclerView.setChecked(true);
                Ids.add(bloods.get(position).getId());
                break;
            }}
            holder.CBRecyclerView.setText(bloods.get(position).getName());
        }catch (Exception e){

        }
    }
    private void setAction(NotificationViewHolder holder, final int position){

        try {
            holder.CBRecyclerView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Ids.add(bloods.get(position).getId());
                    } else {
                        for (int i = 0; i < Ids.size(); i++) {
                            if (Ids.get(i).equals(bloods.get(position).getId())) {
                                Ids.remove(i);
                            }
                        }
                    }
                }
            });
        }catch (Exception e){

        }
    }


    @Override
    public int getItemCount() {
        return bloods.size();
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