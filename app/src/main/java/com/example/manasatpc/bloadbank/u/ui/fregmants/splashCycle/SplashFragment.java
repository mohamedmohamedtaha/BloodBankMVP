package com.example.manasatpc.bloadbank.u.ui.fregmants.splashCycle;


import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {

    private static final long SPLASH_DISPAY_LENGTH = 2000;
    @BindView(R.id.TV_Copy_Right)
    TextView TVCopyRight;
    Unbinder unbinder;
    RememberMy rememberMy;
    String getAPI_key;

    Bundle bundle;
    private SaveData saveData;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        saveData = getArguments().getParcelable(GET_DATA);

        rememberMy = new RememberMy(getActivity());



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rememberMy.isRemember()){
                    getAPI_key=rememberMy.getDataUser(getActivity());
                    HelperMethod.startActivity(getActivity(), HomeActivity.class,getAPI_key);

                }else {
                    SliderFragment sliderFragment = new SliderFragment();
                    HelperMethod.replece(sliderFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Splash_contener, null, null, saveData);

                }


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
