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
import com.example.manasatpc.bloadbank.u.adapter.AdapterNotification;
import com.example.manasatpc.bloadbank.u.data.model.general.GeneralResponseData;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.notification.getnotificationssettings.GetNotificationSsettings;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;


public class SettingsNotificationFragment extends Fragment {
    @BindView(R.id.SettingsNotificationFragment_RecyclerView_Types_Blood)
    RecyclerView SettingsNotificationFragmentRecyclerViewTypesBlood;
    @BindView(R.id.SettingsNotificationFragment_RecyclerView_Types_Cities)
    RecyclerView SettingsNotificationFragmentRecyclerViewTypesCities;
    @BindView(R.id.RSettingsNotificationFragment_BT_Save)
    Button RSettingsNotificationFragmentBTSave;
    @BindView(R.id.SettingsNotificationFragment_Progress_Bar)
    ProgressBar SettingsNotificationFragmentProgressBar;
    Unbinder unbinder;

    private AdapterNotification bloodsAdapter, governAdapter;
    private List<GeneralResponseData> getGovrnorates = new ArrayList<>();
    private List<GeneralResponseData> getBloodType = new ArrayList<>();

    private List<String> oldBloodTypes = new ArrayList<>();
    private List<String> oldGovernorates = new ArrayList<>();

    private APIServices apiServices;
    RememberMy rememberMy;

    public SettingsNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        apiServices = getRetrofit().create(APIServices.class);

        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        // SettingsNotificationFragmentProgressBar.setVisibility(View.VISIBLE);
        getPosts();
        return view;
    }

    private void getPosts() {
        getGovrnorates.clear();
        getBloodType.clear();
        apiServices.getGetNotificationsSettings(rememberMy.getAPIKey()).enqueue(new Callback<GetNotificationSsettings>() {
            @Override
            public void onResponse(Call<GetNotificationSsettings> call, Response<GetNotificationSsettings> response) {
                final GetNotificationSsettings getNotificationSsettings = response.body();
                try {
                    if (getNotificationSsettings.getStatus() == 1) {
                        oldBloodTypes = getNotificationSsettings.getData().getBloodTypes();
                        oldGovernorates = getNotificationSsettings.getData().getGovernorates();
                        getGav();
                        getBloodType();
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

    private void getGav() {
        SettingsNotificationFragmentRecyclerViewTypesCities.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        apiServices.getGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                try {
                    Governorates governorates = response.body();
                    if (governorates.getStatus() == 1) {
                        getGovrnorates.addAll(response.body().getData());
                        governAdapter = new AdapterNotification(getActivity(), getActivity(), getGovrnorates, oldGovernorates);
                        SettingsNotificationFragmentRecyclerViewTypesCities.setAdapter(governAdapter);
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
    }

    private void getBloodType() {
        SettingsNotificationFragmentRecyclerViewTypesBlood.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        bloodsAdapter = new AdapterNotification(getActivity(), getActivity(), getBloodType, oldBloodTypes);
        apiServices.getBloodTypes().enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                try {
                    BloodTypes bloodType = response.body();
                    if (bloodType.getStatus() == 1) {
                        getBloodType.addAll(bloodType.getData());
                        SettingsNotificationFragmentRecyclerViewTypesBlood.setAdapter(bloodsAdapter);
                    } else {
                        Toast.makeText(getActivity(), bloodType.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {
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
        apiServices.notificationsSettings(rememberMy.getAPIKey(), governAdapter.Ids, bloodsAdapter.Ids).enqueue(new Callback<GetNotificationSsettings>() {
            @Override
            public void onResponse(Call<GetNotificationSsettings> call, Response<GetNotificationSsettings> response) {
                try {
                    GetNotificationSsettings getNotificationSsettings = response.body();
                    if (getNotificationSsettings.getStatus() == 1) {
                        Toast.makeText(getActivity(), getNotificationSsettings.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getNotificationSsettings.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<GetNotificationSsettings> call, Throwable t) {

            }
        });
    }
}
