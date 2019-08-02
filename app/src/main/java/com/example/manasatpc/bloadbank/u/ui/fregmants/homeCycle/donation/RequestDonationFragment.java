package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.RequestDonationInteractor;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestcreate.DonationRequestCreate;
import com.example.manasatpc.bloadbank.u.data.model.general.GeneralResponseData;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.presenter.RequestDonationPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.RequestDonationView;
import com.example.manasatpc.bloadbank.u.helper.GPSTracker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.MapFragment;

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
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.MapFragment.LATITUDE_MAP;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.MapFragment.LONGITUDE_MAP;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets.MapFragment.longitude;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDonationFragment extends Fragment implements RequestDonationView {
    @BindView(R.id.RequestDonationFragment_ET_Name_Patient)
    TextInputEditText RequestDonationFragmentETNamePatient;
    @BindView(R.id.RequestDonationFragment_ET_Age_Patient)
    TextInputEditText RequestDonationFragmentETAgePatient;
    @BindView(R.id.RequestDonationFragment_SP_Blood_Types)
    Spinner RequestDonationFragmentSPBloodTypes;
    @BindView(R.id.RequestDonationFragment_SP_Number_Package)
    Spinner RequestDonationFragmentSPNumberPackage;
    @BindView(R.id.RequestDonationFragment_ET_Name_Hospital_Patient)
    TextInputEditText RequestDonationFragmentETNameHospitalPatient;
    @BindView(R.id.RequestDonationFragment_TV_Adrees_Hospital)
    TextView RequestDonationFragmentTVAdreesHospital;
    @BindView(R.id.RequestDonationFragment_SP_Gaverment_Request_Donation)
    Spinner RequestDonationFragmentSPGavermentRequestDonation;
    @BindView(R.id.RequestDonationFragment_SP_City_Request_Donation)
    Spinner RequestDonationFragmentSPCityRequestDonation;
    @BindView(R.id.RequestDonationFragment_ET_Phone_Patient)
    TextInputEditText RequestDonationFragmentETPhonePatient;
    @BindView(R.id.RequestDonationFragment_ET_Notes_Patient)
    TextInputEditText RequestDonationFragmentETNotesPatient;
    @BindView(R.id.RequestDonationFragment_BT_Send_Request)
    Button RequestDonationFragmentBTSendRequest;
    @BindView(R.id.RequestDonationFragment_Progress_Bar)
    ProgressBar RequestDonationFragmentProgressBar;
    @BindView(R.id.RequestDonationFragment_ET_Adrees_Hospital)
    TextInputEditText RequestDonationFragmentETAdreesHospital;
    Unbinder unbinder;

    RememberMy rememberMy;
    private APIServices apiServicesgetGovernorate;
    Integer positionCity;
    Integer positionBloodType;
    Bundle bundle;
    private RequestDonationPresenter presenter;

     ArrayList<Integer> IdsCity = new ArrayList<>();
     ArrayList<Integer> IdsBloodType = new ArrayList<>();
    int IdsNumberBackage;
    ArrayList<String> stringsNumberBackage = new ArrayList<>();


    public RequestDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new RequestDonationInteractor(this,getActivity());
        rememberMy = new RememberMy(getActivity());
        bundle = getArguments();

        stringsNumberBackage.add(getString(R.string.select_number_package));
        stringsNumberBackage.add("1");
        stringsNumberBackage.add("2");
        stringsNumberBackage.add("3");
        stringsNumberBackage.add("4");
        stringsNumberBackage.add("5");
        stringsNumberBackage.add("6");
        stringsNumberBackage.add("7");
        stringsNumberBackage.add("8");
        stringsNumberBackage.add("9");
        stringsNumberBackage.add("10");

        //For fill SpinnerNumberPackage
        HelperMethod.showGovernorates(stringsNumberBackage, getActivity(), RequestDonationFragmentSPNumberPackage);
        RequestDonationFragmentSPNumberPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IdsNumberBackage = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        IdsBloodType = HelperMethod.getBloodTypes(getActivity(),RequestDonationFragmentSPBloodTypes);


        // for get DataPost to governorate Spinner
        apiServicesgetGovernorate = getRetrofit().create(APIServices.class);
        final Call<Governorates> governorates = apiServicesgetGovernorate.getGovernorates();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    String getResult;
                    ArrayList<String> strings = new ArrayList<>();
                    final ArrayList<Integer> Ids = new ArrayList<>();

                    try {
                        Governorates governorates1 = response.body();
                        strings.add(getString(R.string.select_gaverment));
                        Ids.add(0);
                        List<GeneralResponseData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            strings.add(getResult);
                            Ids.add(governoratesData.get(i).getId());
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RequestDonationFragmentSPGavermentRequestDonation);
                        RequestDonationFragmentSPGavermentRequestDonation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    IdsCity = HelperMethod.getCities(getActivity(),RequestDonationFragmentSPCityRequestDonation,Ids.get(i));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.RequestDonationFragment_TV_Adrees_Hospital, R.id.RequestDonationFragment_BT_Send_Request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.RequestDonationFragment_TV_Adrees_Hospital:
                if (GPSTracker.isServicesOk(getActivity())) {
                    MapFragment mapFragment = new MapFragment();
                    HelperMethod.replece(mapFragment, getActivity().getSupportFragmentManager(),
                            R.id.Cycle_Home_contener, toolbar, getString(R.string.address_hospital));
                }
                break;
            case R.id.RequestDonationFragment_BT_Send_Request:
                // for check network
                boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
                if (check_network == false) {
                    return;
                }

                String name_patient = RequestDonationFragmentETNamePatient.getText().toString().trim();
                String age_patient = RequestDonationFragmentETAgePatient.getText().toString().trim();
                String hospital_name = RequestDonationFragmentETNameHospitalPatient.getText().toString().trim();
                String phone = RequestDonationFragmentETPhonePatient.getText().toString().trim();
                String notes = RequestDonationFragmentETNotesPatient.getText().toString().trim();
                String hospital_address = RequestDonationFragmentETAdreesHospital.getText().toString().trim();
                if (name_patient.isEmpty() || age_patient.isEmpty() || hospital_name.isEmpty() ||
                        phone.isEmpty() || notes.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (IdsNumberBackage <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_number_package), Toast.LENGTH_SHORT).show();
                    return;
                }
                String number_package_string = String.valueOf(IdsNumberBackage);

                if (IdsCity.isEmpty() || IdsBloodType.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.selct_blood_and_city), Toast.LENGTH_SHORT).show();
                    return;
                }
                double latitude =MapFragment. latitude ;
                double longtite = MapFragment.longitude ;
                if (longtite == 0 || latitude == 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_map), Toast.LENGTH_SHORT).show();
                    return;
                }
                int city_id = IdsCity.get(RequestDonationFragmentSPCityRequestDonation.getSelectedItemPosition());
                int blood_type = IdsBloodType.get(RequestDonationFragmentSPBloodTypes.getSelectedItemPosition());

                presenter.saveDonationRequest(name_patient,age_patient,blood_type,number_package_string,hospital_name,
                        hospital_address,city_id,phone,notes,latitude,longtite);
                break;
        }
    }

    @Override
    public void showProgress() {
        RequestDonationFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        RequestDonationFragmentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}