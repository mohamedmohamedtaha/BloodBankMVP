package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.DonationRequests;
import com.example.manasatpc.bloadbank.u.data.presenter.DonationPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.DonationView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class DonationInteractor implements DonationPresenter {
    private DonationView donationView;
    private APIServices apiServices = getRetrofit().create(APIServices.class);
    private RememberMy rememberMy;
    private Context context;
    ArrayList<Data2DonationRequests> dataDonations = new ArrayList<>();


    public DonationInteractor(DonationView donationView, Context context) {
        this.donationView = donationView;
        this.context = context;
        rememberMy = new RememberMy(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadData(int page) {
        if (donationView != null) {
            donationView.showProgress();
            apiServices.getDonationRequests(rememberMy.getAPIKey(), page)
                    .enqueue(new Callback<DonationRequests>() {
                        @Override
                        public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                            try {
                                DonationRequests donation = response.body();
                                if (donation.getStatus() == 1) {
                                    dataDonations.addAll(donation.getData().getData());
                                    if (!dataDonations.isEmpty()) {
                                        donationView.hideProgress();
                                        donationView.showRecyclerView();
                                        donationView.loadSuccess(dataDonations);
                                    } else {
                                        donationView.hideProgress();
                                        donationView.showRelativeView();
                                    }
                                } else {
                                    donationView.showError(donation.getMsg());
                                    donationView.hideProgress();
                                    donationView.showRelativeView();
                                }
                            } catch (Exception e) {
                                donationView.showError(e.getMessage());
                                donationView.hideProgress();
                                donationView.showRelativeView();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonationRequests> call, Throwable t) {
                            donationView.hideProgress();
                            donationView.showRelativeView();
                        }
                    });
        }

    }

    @Override
    public void search(int blood_id, int geverment_id, int page) {
        if (donationView != null) {
            donationView.showProgress();
            dataDonations.clear();
            apiServices.getDonationRequestFilter(rememberMy.getAPIKey(), blood_id, geverment_id, page)
                    .enqueue(new Callback<DonationRequests>() {
                        @Override
                        public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                            DonationRequests donationRequestsFilter = response.body();
                            try {
                                if (donationRequestsFilter.getStatus() == 1) {
                                    dataDonations.addAll(donationRequestsFilter.getData().getData());
                                    if (!dataDonations.isEmpty()) {
                                        donationView.hideProgress();
                                        donationView.showRecyclerView();
                                        donationView.loadSuccess(dataDonations);
                                    } else {
                                        donationView.hideProgress();
                                        donationView.showRelativeView();
                                    }
                                } else {
                                    donationView.showError(donationRequestsFilter.getMsg());
                                    donationView.hideProgress();
                                    donationView.showRelativeView();
                                }
                            } catch (Exception e) {
                                donationView.showError(e.getMessage());
                                donationView.hideProgress();
                                donationView.showRelativeView();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonationRequests> call, Throwable t) {
                            donationView.showError(t.getMessage());
                            donationView.hideProgress();
                            donationView.showRelativeView();
                        }
                    });
        }

    }
}
