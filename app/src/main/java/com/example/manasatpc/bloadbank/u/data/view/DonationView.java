package com.example.manasatpc.bloadbank.u.data.view;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;

import java.util.ArrayList;

public interface DonationView {
    void showProgress();
    void hideProgress();
    void showRecyclerView();
    void showRelativeView();
    void showError(String message);
    void loadSuccess( ArrayList<Data2DonationRequests> dataDonations);
}
