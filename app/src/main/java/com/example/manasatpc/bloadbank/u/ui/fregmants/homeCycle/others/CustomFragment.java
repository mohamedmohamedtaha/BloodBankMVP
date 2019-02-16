package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.others;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterForConverter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

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
        AdapterForConverter adapterForConverter = new AdapterForConverter(getActivity(), getActivity().getSupportFragmentManager(),bundle);
        viewPager.setAdapter(adapterForConverter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
