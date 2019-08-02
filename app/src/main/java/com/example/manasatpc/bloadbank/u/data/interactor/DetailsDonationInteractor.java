package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.presenter.DetailsDonationPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.DetailsDonationView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class DetailsDonationInteractor implements DetailsDonationPresenter {
    private DetailsDonationView detailsDonationView;
    private APIServices apiServices = getRetrofit().create(APIServices.class);
    RememberMy rememberMy;
    Context context;

    public DetailsDonationInteractor(DetailsDonationView detailsDonationView, Context context) {
        this.detailsDonationView = detailsDonationView;
        this.context = context;
        rememberMy = new RememberMy(context);
    }

    @Override
    public void onDestroy() {
        detailsDonationView = null;
    }

    @Override
    public void loadData(int requestId) {
        if (detailsDonationView != null) {
            detailsDonationView.showProgress();
            apiServices.getDonationRequest(rememberMy.getAPIKey(), requestId)
                    .enqueue(new Callback<DonationRequest>() {
                        @Override
                        public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                            DonationRequest donationRequest = response.body();
                            try {
                                DataDonationRequest dataDonationRequest = donationRequest.getData();
                                if (donationRequest.getStatus() == 1) {
                                    detailsDonationView.hideProgress();
                                    detailsDonationView.loadSuccess(dataDonationRequest);
                                } else {
                                    detailsDonationView.showError(donationRequest.getMsg());
                                    detailsDonationView.hideProgress();
                                }
                            } catch (Exception e) {
                                detailsDonationView.showError(e.getMessage());
                                detailsDonationView.hideProgress();
                            }

                        }

                        @Override
                        public void onFailure(Call<DonationRequest> call, Throwable t) {
                            detailsDonationView.showError(t.getMessage());
                            detailsDonationView.hideProgress();
                        }
                    });
        }
    }
}