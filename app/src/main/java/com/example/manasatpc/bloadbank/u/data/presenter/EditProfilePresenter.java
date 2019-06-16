package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;
import android.widget.Spinner;

import com.example.manasatpc.bloadbank.u.data.model.EditProfileModel;

import java.util.ArrayList;

public interface EditProfilePresenter {
    void onDestroy();
    void editProfile(String new_user, String email, String date_birth, String last_date, String phone, String password,
                     String retry_password, final Context context, final Spinner EditInformationFragmentSPBloodType, final Spinner EditInformationFragmentSPCity);

    void getProfile(String api, final Context context);

    ArrayList<Integer> getGaverment(final Context context
            , final Spinner EditInformationFragmentSPGaverment, final Spinner EditInformationFragmentSPCity);

    ArrayList<Integer> getCities(int getIdGovernorates,final Context context, final Spinner EditInformationFragmentSPCity);

    ArrayList<Integer> getBloodTypes(final Context context, final Spinner EditInformationFragmentBloodType);

}
