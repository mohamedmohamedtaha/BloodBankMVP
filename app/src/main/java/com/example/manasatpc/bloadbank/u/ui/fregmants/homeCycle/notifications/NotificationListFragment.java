package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterNotificationList;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationslist.Data2NotificationsList;
import com.example.manasatpc.bloadbank.u.data.model.notification.notificationslist.NotificationsList;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationListFragment extends Fragment {


    @BindView(R.id.NotificationListFragment_Recycle_View)
    RecyclerView NotificationListFragmentRecycleView;
    @BindView(R.id.NotificationListFragment_RL_Empty_View)
    RelativeLayout NotificationListFragmentRLEmptyView;
    @BindView(R.id.NotificationListFragment_Loading_Indicator)
    ProgressBar NotificationListFragmentLoadingIndicator;
    Unbinder unbinder;
    SaveData saveData;
    AdapterNotificationList adapterNotificationList;
    ArrayList<Data2NotificationsList> data2NotificationsListArrayList = new ArrayList<>();
    private int max = 0;

    public NotificationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        data2NotificationsListArrayList.clear();
        NotificationListFragmentRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        NotificationListFragmentRecycleView.setLayoutManager(linearLayoutManager);
        adapterNotificationList = new AdapterNotificationList(getActivity(), data2NotificationsListArrayList, new AdapterNotificationList.showDetails() {
            @Override
            public void showItemDetails(Data2NotificationsList data2NotificationsList) {
                Toast.makeText(getActivity(), "d:" + data2NotificationsList.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        NotificationListFragmentRecycleView.setAdapter(adapterNotificationList);
        APIServices apiServices = getRetrofit().create(APIServices.class);
        NotificationListFragmentLoadingIndicator.setVisibility(View.VISIBLE);
        apiServices.getNotificationsList("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl").enqueue(new Callback<NotificationsList>() {
            @Override
            public void onResponse(Call<NotificationsList> call, Response<NotificationsList> response) {
                try {
                    NotificationsList notificationsList = response.body();
                    if (notificationsList.getStatus() == 1) {
                        if (!notificationsList.getData().getData().isEmpty()){
                            NotificationListFragmentLoadingIndicator.setVisibility(View.GONE);
                            NotificationListFragmentRecycleView.setVisibility(View.VISIBLE);
                            NotificationListFragmentRLEmptyView.setVisibility(View.GONE);
                            data2NotificationsListArrayList.addAll(notificationsList.getData().getData());
                            adapterNotificationList.notifyDataSetChanged();
                        }else {
                            NotificationListFragmentLoadingIndicator.setVisibility(View.GONE);
                            NotificationListFragmentRecycleView.setVisibility(View.GONE);
                            NotificationListFragmentRLEmptyView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getActivity(), notificationsList.getMsg(), Toast.LENGTH_SHORT).show();
                        NotificationListFragmentLoadingIndicator.setVisibility(View.GONE);
                        NotificationListFragmentRLEmptyView.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    NotificationListFragmentLoadingIndicator.setVisibility(View.GONE);
                    NotificationListFragmentRLEmptyView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<NotificationsList> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                NotificationListFragmentLoadingIndicator.setVisibility(View.GONE);
                NotificationListFragmentRLEmptyView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
