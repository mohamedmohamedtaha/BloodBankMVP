package com.example.manasatpc.bloadbank.u.fregmants.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private static final long SPLASH_DISPAY_LENGTH = 2000;
    @BindView(R.id.TV_Copy_Right)
    TextView TVCopyRight;
    Unbinder unbinder;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SliderFragment splashFragment = new SliderFragment();
                HelperMethod.replece(splashFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Splash_contener, null, null, null);

            }
        }, SPLASH_DISPAY_LENGTH);

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
