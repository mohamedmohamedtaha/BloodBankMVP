package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.manasatpc.bloadbank.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDonationFragment extends Fragment {


    @BindView(R.id.ET_Name_Patient)
    TextInputEditText ETNamePatient;
    @BindView(R.id.ET_Age_Patient)
    TextInputEditText ETAgePatient;
    @BindView(R.id.ET_Type_Blood_Patient)
    TextInputEditText ETTypeBloodPatient;
    @BindView(R.id.Spinner_Number_Package)
    MaterialBetterSpinner SpinnerNumberPackage;
    @BindView(R.id.ET_Name_Hospital_Patient)
    TextInputEditText ETNameHospitalPatient;
    @BindView(R.id.Fragment_Date_Regester)
    TextView FragmentDateRegester;
    @BindView(R.id.Spinner_Gaverment_Request_Donation)
    MaterialBetterSpinner SpinnerGavermentRequestDonation;
    @BindView(R.id.Spinner_City_Request_Donation)
    MaterialBetterSpinner SpinnerCityRequestDonation;
    @BindView(R.id.ET_Phone_Patient)
    TextInputEditText ETPhonePatient;
    @BindView(R.id.ET_Notes_Patient)
    TextInputEditText ETNotesPatient;
    @BindView(R.id.BT_Send_Request)
    Button BTSendRequest;
    Unbinder unbinder;

    public RequestDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.BT_Send_Request)
    public void onViewClicked() {
    }
}
