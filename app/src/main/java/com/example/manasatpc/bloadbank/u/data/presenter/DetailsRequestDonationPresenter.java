package com.example.manasatpc.bloadbank.u.data.presenter;

public interface DetailsRequestDonationPresenter {
    void onDestroy();
    void getDonationRequest(String apiKey, int request_id);
}
