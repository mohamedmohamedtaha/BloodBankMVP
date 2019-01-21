package com.example.manasatpc.bloadbank.ui.fregmants.splashCycle;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.ui.activities.LoginActivity;
import com.example.manasatpc.bloadbank.ui.adapter.AdapterSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {

    @BindView(R.id.ViewPager)
    android.support.v4.view.ViewPager ViewPager;
    @BindView(R.id.DotLayout)
    LinearLayout DotLayout;
    @BindView(R.id.Skip)
    Button Skip;

    Unbinder unbinder;
    private int[] layouts;

    private AdapterSlider adapterSlider;
    private TextView[] dottv;

    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);


        layouts = new int[]{R.layout.info_1,R.layout.info_2};
        adapterSlider = new AdapterSlider(getActivity(),layouts);
        ViewPager.setAdapter(adapterSlider);
        ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == layouts.length-1){

                }
                setDotLayout(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotLayout(0);


        return view;

    }

    private void setDotLayout(int page){
        DotLayout.removeAllViews();
        dottv = new TextView[layouts.length];
        for (int i = 0 ; i <dottv.length ;i++){
            dottv[i]= new TextView(getActivity());
            dottv[i].setText(Html.fromHtml("&#8226"));
            dottv[i].setTextSize(30);
            //  dottv[i].setP

            dottv[i].setTextColor(Color.parseColor("#000000"));
            DotLayout.addView(dottv[i]);
        }

        //set current dot active
        if (dottv.length > 0 ){
            dottv[page].setTextColor(Color.parseColor("#9da0a3"));
        }

    }


    @OnClick(R.id.Skip)
    public void onViewClicked() {
        startLoginActivity();
    }
    private void startLoginActivity(){
        Intent startLoginActivity = new Intent(getActivity(),LoginActivity.class);
        getActivity().startActivity(startLoginActivity);
        //LoginFragment loginFragment = new LoginFragment();
        //HelperMethod.replece(loginFragment,getActivity().getSupportFragmentManager(),R.id.content_test,null,null);


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
