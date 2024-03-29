package com.example.manasatpc.bloadbank.u.data.presenter;

import android.content.Context;
import android.widget.Spinner;

import java.util.ArrayList;

public interface RegisterPresenter {

    void onDestroy();

    void register(String new_user, String email, String date_birth, String last_date, String phone,
                  String password, String retry_password,
                  final Spinner RegesterFragmentSPCity, final Spinner RegesterFragmentBloodType);

    ArrayList<Integer> getGaverment(final Context context
            , final Spinner RegesterFragmentSPGaverment, final Spinner RegesterFragmentSPCity);

    ArrayList<Integer> getCities(int getIdGovernorates, final Context context, final Spinner RegesterFragmentSPCity);

    ArrayList<Integer> getBloodTypes(final Context context, final Spinner RegesterFragmentBloodType);

    void getDonationRequestCreate(String apiKey,String name_patient, String age_patient, int blood_type,
                                  String hospital_name,String hospital_address, int city_id,String phone,
                                   String notes,String latitude,String longtite,int IdsNumberBackage);

}