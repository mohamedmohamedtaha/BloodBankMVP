package com.example.manasatpc.bloadbank.u.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class RememberMy {

    public static final String FOLDERNAME = "saveUser";
    public static final String KEY_REMEMBERMY = "rememberMy";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ID = "id";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // This is constructor
    public RememberMy(Context context) {
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // This method for save data User
    public void saveDateUSer(String phone, String password) {
        editor.putBoolean(KEY_REMEMBERMY, true);
        editor.putString(KEY_ID, phone);
        editor.putString(KEY_PHONE, password);
        editor.commit();
    }

    // This method for check Do the user save data or not ?
    public boolean isRemember() {
        if (sharedPreferences.getBoolean(KEY_REMEMBERMY, false)) {
            return true;
        } else {
            return false;
        }
    }


}
