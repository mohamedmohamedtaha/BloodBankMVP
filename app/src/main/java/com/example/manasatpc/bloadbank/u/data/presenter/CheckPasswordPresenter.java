package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;

import com.example.manasatpc.bloadbank.u.data.interactor.CheckPasswordInteractor;
import com.example.manasatpc.bloadbank.u.data.view.CheckPasswordView;

public class CheckPasswordPresenter implements CheckPasswordInteractor.OnChangedListener {
    private CheckPasswordView checkPasswordView;
    private CheckPasswordInteractor interactor;

    public CheckPasswordPresenter(CheckPasswordView checkPasswordView, CheckPasswordInteractor interactor) {
        this.checkPasswordView = checkPasswordView;
        this.interactor = interactor;
    }

    public void checkEmail(String phone, Context context) {
        if (checkPasswordView != null) {
            checkPasswordView.showProgress();
        }
        interactor.checkPhone(phone, this, context);
    }

    @Override
    public void showProgress() {
        if (checkPasswordView != null) {
            checkPasswordView.showProgress();
        }

    }
    public void onDestory(){
        checkPasswordView = null;
    }

    @Override
    public void hideProgress() {
        if (checkPasswordView != null) {
            checkPasswordView.hideProgress();
        }
    }

    @Override
    public void send() {
        if (checkPasswordView != null) {
            checkPasswordView.send();
        }
    }

    @Override
    public void setEmpty() {
        if (checkPasswordView != null) {
            checkPasswordView.isEmpty();
            checkPasswordView.hideProgress();
        }

    }
}
