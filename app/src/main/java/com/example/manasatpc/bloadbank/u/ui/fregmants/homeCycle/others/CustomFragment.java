package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterForConverter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class

CustomFragment extends Fragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    Unbinder unbinder;
    Bundle bundle;
    SaveData saveData;

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.custom_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        bundle = getArguments();
        saveData = getArguments().getParcelable(GET_DATA);
        AdapterForConverter adapterForConverter = new AdapterForConverter(getActivity(),getChildFragmentManager(),saveData);
        viewPager.setAdapter(adapterForConverter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle(R.string.home);

    }
}
