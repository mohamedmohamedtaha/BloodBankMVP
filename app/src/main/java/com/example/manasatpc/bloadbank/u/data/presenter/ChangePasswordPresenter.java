package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;
import android.view.View;

import com.example.manasatpc.bloadbank.u.data.interactor.ChangePasswordInteractor;
import com.example.manasatpc.bloadbank.u.data.view.ChangePasswordView;

public class ChangePasswordPresenter implements ChangePasswordInteractor.OnChangedListener {
    private ChangePasswordView changePasswordView;
    private ChangePasswordInteractor interactor;

    public ChangePasswordPresenter(ChangePasswordView changePasswordView, ChangePasswordInteractor interactor) {
        this.changePasswordView = changePasswordView;
        this.interactor = interactor;
    }
    public void changePassword(String newPassowrd, String retryPassword, String code, String savePhone, Context context, View view){
        if (changePasswordView != null){
            changePasswordView.showProgress();
            interactor.changePassword(newPassowrd,retryPassword,code,savePhone,this,context,view);
        }
    }
    public void onDestory(){
        changePasswordView = null;
    }

    @Override
    public void hideProgress() {
        if (changePasswordView != null){
            changePasswordView.hideProgress();
        }
    }
    @Override
    public void showProgress() {
        if (changePasswordView != null){
            changePasswordView.showProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (changePasswordView != null){
            changePasswordView.navigateToLogin();
        }
    }


    @Override
    public void empty() {
        if (changePasswordView != null){
            changePasswordView.setEmpty();
            changePasswordView.hideProgress();
        }
    }

    @Override
    public void stopCounter() {
        if (changePasswordView != null){
            changePasswordView.stopCounter();
        }
    }

    @Override
    public void isNetworkAvailable() {
        if (changePasswordView != null){
            changePasswordView.networkAvailable();
            changePasswordView.hideProgress();
        }
    }

    @Override
    public void showError(String message) {
        if (changePasswordView != null){
            changePasswordView.showError(message);
            changePasswordView.hideProgress();
        }
    }
}
