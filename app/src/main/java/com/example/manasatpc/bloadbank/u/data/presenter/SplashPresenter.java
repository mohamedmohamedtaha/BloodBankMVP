package com.example.manasatpc.bloadbank.u.data.presenter;

import com.example.manasatpc.bloadbank.u.data.interactor.SplashInteractor;
import com.example.manasatpc.bloadbank.u.data.view.SplashView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

public class SplashPresenter implements SplashInteractor.OnFinishedListener {
    private SplashView splashView;
    private SplashInteractor interactor;

    public SplashPresenter(SplashView splashView, SplashInteractor interactor) {
        this.splashView = splashView;
        this.interactor = interactor;
    }
    public void checkRemember(RememberMy rememberMy){
        if (splashView != null){
            interactor.checkRemember(rememberMy,this);
        }
    }
    public void onDestory(){
        splashView = null;
    }

    @Override
    public void notRemember() {
        if (splashView != null){
            splashView.notRemember();
        }

    }

    @Override
    public void isRemember() {
        if (splashView != null){
            splashView.isRemember();
        }

    }
}
