package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.donation.DataDonation;
import com.example.manasatpc.bloadbank.u.data.rest.donation.Donation;
import com.example.manasatpc.bloadbank.u.data.rest.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.rest.posts.post.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestDonationFragment extends Fragment {


    @BindView(R.id.TV_Show_Name_Details_Donation)
    TextView TVShowNameDetailsDonation;
    @BindView(R.id.TV_Show_Age_Details_Donation)
    TextView TVShowAgeDetailsDonation;
    @BindView(R.id.TV_Show_Type_Blood_Details_Donation)
    TextView TVShowTypeBloodDetailsDonation;
    @BindView(R.id.TV_Show_Number_Package_Request_Details_Donation)
    TextView TVShowNumberPackageRequestDetailsDonation;
    @BindView(R.id.TV_Show_Hospital_Details_Donation)
    TextView TVShowHospitalDetailsDonation;
    @BindView(R.id.TV_Show_Address_Hospital_Details_Donation)
    TextView TVShowAddressHospitalDetailsDonation;
    @BindView(R.id.TV_Show_Phone_Details_Donation)
    TextView TVShowPhoneDetailsDonation;
    @BindView(R.id.TV_Show_Details_Details_Donation)
    TextView TVShowDetailsDetailsDonation;
    @BindView(R.id.BT_Call)
    Button BTCall;
    Unbinder unbinder;
    private APIServices apiServices;

    public DetailRequestDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_request_donation, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiServices =  getRetrofit().create(APIServices.class);
        apiServices.getDonationRequest("8KTqGqCh3ofQvl0DySaPNBw0TZODwgxlfZ0nHmWxigWlrKK3cpVLJQEb0bju",1)
                .enqueue(new Callback<DonationRequest>() {
            @Override
            public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                DonationRequest donationRequest = response.body();
                if (donationRequest.getStatus() == 1){
                        TVShowNameDetailsDonation.append(donationRequest.getData().getPatientName());
                        TVShowAgeDetailsDonation.append(donationRequest.getData().getPatientAge());
                        TVShowTypeBloodDetailsDonation.append(donationRequest.getData().getBloodType());
                        TVShowNumberPackageRequestDetailsDonation.append(donationRequest.getData().getBagsNum());
                        TVShowHospitalDetailsDonation.append(donationRequest.getData().getHospitalName());
                        TVShowAddressHospitalDetailsDonation.append(donationRequest.getData().getHospitalAddress());
                        TVShowPhoneDetailsDonation.append(donationRequest.getData().getPhone());
                        TVShowDetailsDetailsDonation.append(donationRequest.getData().getNotes());



                }else {
                    Toast.makeText(getActivity(), donationRequest.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonationRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.BT_Call)
    public void onViewClicked() {
    }
}
