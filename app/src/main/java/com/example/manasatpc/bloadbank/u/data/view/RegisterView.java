package com.example.manasatpc.bloadbank.u.data.view;

public interface RegisterView {
    void showProgress();
    void hideProgress();
    void emptyFiled();
    void onSuccess();
    void cityEmpty();
    void selectMap();
    void selectBackage();
    void showError(String message);
}
