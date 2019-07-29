package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.general.settings.Settings;

public interface ConnectWithUsView {
    void showProgress();
    void hideProgress();
    void send();
    void isEmpty();
    void showMessage(String message);
    void retrieveData(Settings settings);

}
