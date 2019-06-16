package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.os.Handler;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;
import com.example.manasatpc.bloadbank.u.ui.fregmants.splashCycle.SliderFragment;

public class SplashInteractor {
    private static final long SPLASH_DISPAY_LENGTH = 2000;
    public interface OnFinishedListener{
        void notRemember();
        void isRemember();
    }
    public void checkRemember(final RememberMy rememberMy,final OnFinishedListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rememberMy.isRemember()) {
                    listener.isRemember();
                } else {
                   listener.notRemember();
                }
            }
        }, SPLASH_DISPAY_LENGTH);
    }
}
