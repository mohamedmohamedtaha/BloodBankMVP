package com.example.manasatpc.bloadbank.u.data.view;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void navigateToHome();
    void navigateToRegister();
    void errorLogin();
    void emptyFiled();
    void hideProgressOnly();
    void getToken();
    void navigateToResetPassword();

}
