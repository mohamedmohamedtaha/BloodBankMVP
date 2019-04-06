package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterNotificationBloodType;
import com.example.manasatpc.bloadbank.u.adapter.AdapterNotificationGovernorates;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.notification.getnotificationssettings.GetNotificationSsettings;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;


public class SettingsNotificationFragment extends Fragment {

    @BindView(R.id.SettingsNotificationFragment_RecyclerView_Types_Blood)
    RecyclerView SettingsNotificationFragmentRecyclerViewTypesBlood;
    @BindView(R.id.SettingsNotificationFragment_RecyclerView_Types_Cities)
    RecyclerView SettingsNotificationFragmentRecyclerViewTypesCities;
    Unbinder unbinder;
    @BindView(R.id.RSettingsNotificationFragment_BT_Save)
    Button RSettingsNotificationFragmentBTSave;
    @BindView(R.id.SettingsNotificationFragment_Progress_Bar)
    ProgressBar SettingsNotificationFragmentProgressBar;
    // TODO: Rename parameter arguments, choose names that match

    private AdapterNotificationBloodType adapterNotification;
    private AdapterNotificationGovernorates adapterNotificationBloodType;
    private ArrayList<DataBloodTypes> getDataBloodTypes = new ArrayList<>();
    private ArrayList<String> getNotificationSsettingsArrayList = new ArrayList<>();
    private ArrayList<GovernoratesData> getGovrnorates = new ArrayList<>();

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
        SettingsNotificationFragmentProgressBar.setVisibility(View.VISIBLE);
        SettingsNotificationFragmentRecyclerViewTypesBlood.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapterNotification = new AdapterNotificationBloodType(getActivity(), getDataBloodTypes);
        SettingsNotificationFragmentRecyclerViewTypesBlood.setAdapter(adapterNotification);
        SettingsNotificationFragmentRecyclerViewTypesCities.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapterNotificationBloodType = new AdapterNotificationGovernorates(getActivity(), getGovrnorates);
        SettingsNotificationFragmentRecyclerViewTypesCities.setAdapter(adapterNotificationBloodType);
        getPosts();
        return view;
    }

    private void getPosts() {
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                try {
                    BloodTypes getNotificationsSettings = response.body();
                    if (getNotificationsSettings.getStatus() == 1) {
                        getDataBloodTypes.addAll(getNotificationsSettings.getData());
                        adapterNotification.notifyDataSetChanged();
                        SettingsNotificationFragmentProgressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    SettingsNotificationFragmentProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                SettingsNotificationFragmentProgressBar.setVisibility(View.GONE);
            }
        });
        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {
                    Governorates governorates = response.body();
                    if (governorates.getStatus() == 1) {
                        getGovrnorates.addAll(governorates.getData());
                        adapterNotificationBloodType.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), governorates.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        apiServices.getGetNotificationsSettings("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<GetNotificationSsettings>() {
            @Override
            public void onResponse(Call<GetNotificationSsettings> call, Response<GetNotificationSsettings> response) {
                GetNotificationSsettings getNotificationSsettings = response.body();
                try {
                    if (getNotificationSsettings.getStatus() == 1) {
                        getNotificationSsettingsArrayList.addAll(getNotificationSsettings.getData().getBloodTypes());
                        adapterNotification.notifyDataSetChanged();

                        Toast.makeText(getActivity(), getNotificationSsettings.getData().getGovernorates().toString(), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getActivity(), getNotificationSsettings.getMsg(), Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.RSettingsNotificationFragment_BT_Save)
    public void onViewClicked() {
    }
}
