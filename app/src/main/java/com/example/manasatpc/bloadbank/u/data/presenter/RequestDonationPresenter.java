package com.example.manasatpc.bloadbank.u.data.presenter;

public interface RequestDonationPresenter {
    void onDestroy();

    void saveDonationRequest(String name_patient, String age_patient, int blood_type, String number_package_string,
                             String hospital_name, String hospital_address, int city_id, String phone,
                             String notes, double latitude, double longtite);
}
