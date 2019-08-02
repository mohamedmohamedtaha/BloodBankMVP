package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.DetailsDonationModel;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;

public interface DetailsDonationView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void loadSuccess(DataDonationRequest dataDonationRequest);

}
