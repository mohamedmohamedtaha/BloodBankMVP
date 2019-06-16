package com.example.manasatpc.bloadbank.u.ui.fregmants.splashCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterSlider;
import com.example.manasatpc.bloadbank.u.data.interactor.SliderInteractor;
import com.example.manasatpc.bloadbank.u.data.presenter.SliderPresenter;
import com.example.manasatpc.bloadbank.u.data.view.SliderView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.ui.activities.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment implements SliderView {
    @BindView(R.id.SliderFragment_ViewPager)
    ViewPager SliderFragmentViewPager;
    @BindView(R.id.SliderFragment_DotLayout)
    LinearLayout SliderFragmentDotLayout;
    @BindView(R.id.SliderFragment_Skip)
    Button SliderFragmentSkip;
    Unbinder unbinder;
    private int[] layouts;
    private AdapterSlider adapterSlider;
    private SliderPresenter presenter;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        layouts = new int[]{R.layout.info_1, R.layout.info_2};
        adapterSlider = new AdapterSlider(getActivity(), layouts);
        presenter = new SliderPresenter(this, new SliderInteractor());
        SliderFragmentViewPager.setAdapter(adapterSlider);
        SliderFragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                presenter.swipePage(position, SliderFragmentDotLayout, getActivity(), layouts);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        presenter.swipePage(0, SliderFragmentDotLayout, getActivity(), layouts);
        return view;
    }

    @OnClick(R.id.SliderFragment_Skip)
    public void onViewClicked() {
        presenter.skip();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestory();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void skip() {
        HelperMethod.startActivity(getActivity(), LoginActivity.class);
    }
}