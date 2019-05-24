package com.example.manasatpc.bloadbank.u.helper;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.API_KEY;

public class RememberMy {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String FOLDERNAME = "saveUser";
    public static final String KEY_REMEMBERMY = "rememberMy";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";

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
    // This method for save Date User Two
    public void saveDateUserTwo(String name, String phone,String email,String getAPI_key) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(API_KEY, getAPI_key);
        editor.putString(KEY_EMAIL, email);
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
    // This method for getNameUser
    public String getNameUser() {
        String saveName = sharedPreferences.getString(KEY_NAME, null);
        return saveName;
    }
    // This method for getEmail
    public String getEmailUser() {
        String saveEmail = sharedPreferences.getString(KEY_EMAIL, null);
        return saveEmail;
    }
    // This method for getPhoneUser
    public String getPhoneUser() {
        String savePhone = sharedPreferences.getString(KEY_PHONE, null);
        return savePhone;
    }
    public String getAPIKey() {
        String vaue = sharedPreferences.getString(API_KEY, null);
        return vaue;
    }

}
