package com.example.manasatpc.bloadbank.u.data.presenter;

import android.widget.CheckBox;

import com.example.manasatpc.bloadbank.u.data.interactor.LoginInteractor;
import com.example.manasatpc.bloadbank.u.data.view.LoginView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {
    public LoginView loginView;
    public LoginInteractor loginInteractor;

    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;

    }

    public void validate(String userName, String password, RememberMy rememberMy, CheckBox checkBox) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.checkLogin(userName, password, this, rememberMy, checkBox);
    }

    @Override
    public void onErrorLogin() {
        if (loginView != null) {
            loginView.errorLogin();
            loginView.hideProgress();
        }
    }

    @Override
    public void onEmptyFields() {
        if (loginView != null) {
            loginView.emptyFiled();
            loginView.hideProgress();
        }

    }

    public void onDestory() {
        loginView = null;
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }

    }

    @Override
    public void hideProgress() {
        if (loginView != null) {
            loginView.hideProgressOnly();
        }
    }

    @Override
    public void getToken() {
        if (loginView != null) {
            loginView.getToken();
        }
    }

    @Override
    public void navigateToRegister() {
        if (loginView != null) {
            loginView.navigateToRegister();
        }
    }

    @Override
    public void navigateToResetPassword() {
        if (loginView != null) {
            loginView.navigateToResetPassword();
        }
    }

    @Override
    public void showError(String message) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showError(message);
        }
    }
}
