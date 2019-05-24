package com.example.manasatpc.bloadbank.u.ui.fregmants.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {
    private static final long SPLASH_DISPAY_LENGTH = 2000;
    RememberMy rememberMy;
    Bundle bundle;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        bundle = getArguments();
        rememberMy = new RememberMy(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rememberMy.isRemember()) {
                    HelperMethod.startActivity(getActivity(), HomeActivity.class);
                } else {
                    SliderFragment sliderFragment = new SliderFragment();
                    HelperMethod.replece(sliderFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Splash_contener, null, null);
                }
            }
        }, SPLASH_DISPAY_LENGTH);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
