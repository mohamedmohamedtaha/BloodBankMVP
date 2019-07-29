package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;

import com.example.manasatpc.bloadbank.u.data.model.general.settings.Settings;

public interface ConnectWithUsPresenter {
    void onDestroy();
    void getContact(String title_message, String text_message);
    void openWebSite(Context context, String platform);
    void getSettings( String apiKey);


}
