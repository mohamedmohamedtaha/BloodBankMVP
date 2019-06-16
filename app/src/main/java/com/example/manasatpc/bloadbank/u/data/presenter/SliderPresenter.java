package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.manasatpc.bloadbank.u.data.interactor.SliderInteractor;
import com.example.manasatpc.bloadbank.u.data.view.SliderView;

public class SliderPresenter implements SliderInteractor.OnChangedListener {
    private SliderView sliderView;
    private SliderInteractor sliderInteractor;

    public SliderPresenter(SliderView sliderView, SliderInteractor sliderInteractor) {
        this.sliderView = sliderView;
        this.sliderInteractor = sliderInteractor;
    }

    public void swipePage(int position, LinearLayout linearLayout, Context context, int[] layout) {
        if (sliderView != null) {
            sliderInteractor.setLayouts(position, linearLayout, context, layout);
        }
    }
    public void onDestory(){
        sliderView = null;
    }
    @Override
    public void skip() {
        if (sliderView != null) {
            sliderView.skip();
        }
    }
}

