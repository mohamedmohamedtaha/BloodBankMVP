package com.example.manasatpc.bloadbank.u.data.presenter;

public interface DonationPresenter {
    void onDestroy();
    void loadData(int page);
    void search(int blood_id,int geverment_id,int page);
}
