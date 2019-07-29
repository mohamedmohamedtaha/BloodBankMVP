package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;

public interface DetailsRequestDonationView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void loadSuccess(DataDonationRequest dataDonationRequest);

}
