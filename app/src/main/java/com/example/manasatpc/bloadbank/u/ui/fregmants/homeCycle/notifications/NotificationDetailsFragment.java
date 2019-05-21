package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.notifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationDetailsFragment extends Fragment {
    @BindView(R.id.NotificationDetailsFragment_TV_Show_Id)
    TextView NotificationDetailsFragmentTVShowId;
    @BindView(R.id.NotificationDetailsFragment_TV_Show_Date)
    TextView NotificationDetailsFragmentTVShowDate;
    @BindView(R.id.NotificationDetailsFragment_TV_Show_Title)
    TextView NotificationDetailsFragmentTVShowTitle;
    @BindView(R.id.NotificationDetailsFragment_TV_Show_Content)
    TextView NotificationDetailsFragmentTVShowContent;
    Unbinder unbinder;

    public NotificationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        APIServices apiServices = getRetrofit().create(APIServices.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
