package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterNotification;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.model.notification.getnotificationssettings.GetNotificationSsettings;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;


public class SettingsNotificationFragment extends Fragment {

    @BindView(R.id.RecyclerView_Types_Blood)
    RecyclerView RecyclerViewTypesBlood;
    @BindView(R.id.RecyclerView_Types_Cities)
    RecyclerView RecyclerViewTypesCities;
    Unbinder unbinder;

    // TODO: Rename parameter arguments, choose names that match

    private AdapterNotification adapterNotification;
    private ArrayList<String> getNotificationsSettingsList;
    private APIServices apiServices;
    private SaveData saveData;

    public SettingsNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        apiServices = getRetrofit().create(APIServices.class);
        RecyclerViewTypesBlood.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        adapterNotification = new AdapterNotification(getActivity(), getNotificationsSettingsList);
        RecyclerViewTypesBlood.setAdapter(adapterNotification);
        getPosts();

        return view;
    }

    private void getPosts() {


        apiServices.getGetNotificationsSettings(saveData.getApi_token()).enqueue(new Callback<GetNotificationSsettings>() {
            @Override
            public void onResponse(Call<GetNotificationSsettings> call, Response<GetNotificationSsettings> response) {
                try {
                    GetNotificationSsettings getNotificationsSettings = response.body();
                    if (getNotificationsSettings.getStatus() == 1) {
                        if (!getNotificationsSettings.getData().getGovernorates().isEmpty()) {
                            getNotificationsSettingsList.addAll(getNotificationsSettings.getData().getGovernorates());
                            adapterNotification.notifyDataSetChanged();
                        }


                    } else {
                        Toast.makeText(getActivity(), getNotificationsSettings.getMsg(), Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetNotificationSsettings> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.setting_notification);

    }
}
