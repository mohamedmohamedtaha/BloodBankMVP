package com.example.manasatpc.bloadbank.ui.fregmants.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.helper.HelperMethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private static final long SPLASH_DISPAY_LENGTH = 2000 ;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SliderFragment splashFragment = new SliderFragment();
                HelperMethod.replece(splashFragment,getActivity().getSupportFragmentManager(),R.id.Cycle_Splash_contener,null,null);

            }
        },SPLASH_DISPAY_LENGTH);

        return view;

    }


}
