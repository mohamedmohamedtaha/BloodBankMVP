package com.example.manasatpc.bloadbank.u.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.u.data.rest.general.contact.Contact;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

public class RememberMy {

    public static final String FOLDERNAME = "saveUser";
    public static final String KEY_REMEMBERMY = "rememberMy";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // This is constructor
    public RememberMy(Context context) {
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void removeDateUser(Context context){
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    // This method for save data User
    public void saveDateUser(String phone, String password,String getAPI_key) {
        editor.putBoolean(KEY_REMEMBERMY, true);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(API_KEY, getAPI_key);

        editor.commit();

    }
    public String getDataUser(Context context){
        sharedPreferences = context.getSharedPreferences(FOLDERNAME, Context.MODE_PRIVATE);
        String vaue = sharedPreferences.getString(API_KEY,null);
     return vaue;
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
