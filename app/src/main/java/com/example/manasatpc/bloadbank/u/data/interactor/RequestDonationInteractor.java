package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestcreate.DonationRequestCreate;
import com.example.manasatpc.bloadbank.u.data.presenter.RequestDonationPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.RequestDonationView;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class RequestDonationInteractor implements RequestDonationPresenter {
    private RequestDonationView detailsRequestDonationView;
    private APIServices apiServices;
    private RememberMy rememberMy;

    public RequestDonationInteractor(RequestDonationView detailsRequestDonationView, Context context) {
        this.detailsRequestDonationView = detailsRequestDonationView;
        rememberMy = new RememberMy(context);
        apiServices = getRetrofit().create(APIServices.class);
    }

    @Override
    public void onDestroy() {
        detailsRequestDonationView = null;

    }

    @Override
    public void saveDonationRequest(String name_patient, String age_patient, int blood_type,String number_package_string,
                                    String hospital_name,String hospital_address,int city_id,String phone,
                                   String notes,double latitude, double longtite) {
        detailsRequestDonationView.showProgress();
        apiServices.getDonationRequestCreate(rememberMy.getAPIKey(), name_patient, age_patient
                , blood_type, number_package_string, hospital_name, hospital_address, city_id, phone, notes, latitude, longtite)
                .enqueue(new Callback<DonationRequestCreate>() {
            @Override
            public void onResponse(Call<DonationRequestCreate> call, Response<DonationRequestCreate> response) {
                DonationRequestCreate donationRequestCreate = response.body();
                try {
                    if (donationRequestCreate.getStatus() == 1) {
                        detailsRequestDonationView.hideProgress();
                        detailsRequestDonationView.showError(donationRequestCreate.getMsg());
                    } else {
                        detailsRequestDonationView.hideProgress();
                        detailsRequestDonationView.showError(donationRequestCreate.getMsg());
                    }
                } catch (Exception e) {
                    detailsRequestDonationView.hideProgress();
                    detailsRequestDonationView.showError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DonationRequestCreate> call, Throwable t) {
                detailsRequestDonationView.hideProgress();
                detailsRequestDonationView.showError(t.getMessage());
            }
        });
    }
}
