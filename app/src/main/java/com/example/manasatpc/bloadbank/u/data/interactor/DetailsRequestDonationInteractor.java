package com.example.manasatpc.bloadbank.u.data.interactor;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.presenter.DetailsRequestDonationPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.DetailsRequestDonationView;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.DetailRequestDonationFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class DetailsRequestDonationInteractor implements DetailsRequestDonationPresenter {
    private DetailsRequestDonationView detailsRequestDonationView;
    private APIServices apiServices;

    public DetailsRequestDonationInteractor(DetailsRequestDonationView detailsRequestDonationView) {
        this.detailsRequestDonationView = detailsRequestDonationView;
    }

    @Override
    public void onDestroy() {
        detailsRequestDonationView = null;

    }

    @Override
    public void getDonationRequest(String apiKey, int request_id) {
        apiServices = getRetrofit().create(APIServices.class);
        detailsRequestDonationView.showProgress();
        apiServices.getDonationRequest(apiKey, request_id)
                .enqueue(new Callback<DonationRequest>() {
                    @Override
                    public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                        DonationRequest donationRequest = response.body();
                        try {
                            DataDonationRequest dataDonationRequest = donationRequest.getData();
                            if (donationRequest.getStatus() == 1) {
                                if (dataDonationRequest != null){
                                    detailsRequestDonationView.hideProgress();
                                    detailsRequestDonationView.loadSuccess(dataDonationRequest);
                                }else {
                                    detailsRequestDonationView.hideProgress();
                                    detailsRequestDonationView.showError(donationRequest.getMsg());
                                }

                            } else {
                                detailsRequestDonationView.hideProgress();
                                detailsRequestDonationView.showError(donationRequest.getMsg());
                            }
                        } catch (Exception e) {
                            detailsRequestDonationView.hideProgress();
                            detailsRequestDonationView.showError(e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<DonationRequest> call, Throwable t) {
                        detailsRequestDonationView.hideProgress();
                        detailsRequestDonationView.showError(t.getMessage());
                    }
                });
    }
}
