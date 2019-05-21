package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequestcreate.DonationRequestCreate;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
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
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDonationFragment extends Fragment {
    Button BTSendRequest;
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
    Unbinder unbinder;
    private SaveData saveData;
    private APIServices apiServicesgetGovernorate;
    Integer positionCity;
    Integer positionBloodType;

    final ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsBloodType = new ArrayList<>();
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
        saveData = getArguments().getParcelable(GET_DATA);
        getBloodTypes();

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
                //    Toast.makeText(getActivity(), "Position :" + IdsNumberBackage , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                        List<GovernoratesData> governoratesData = governorates1.getData();
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
                                    getCities(Ids.get(i));
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

    private void getCities(int getIdGovernorates) {
        APIServices apiServicesgetCities = getRetrofit().create(APIServices.class);
        final Call<Cities> citiesCall = apiServicesgetCities.getCities(getIdGovernorates);
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();

                try {
                    strings.add(getString(R.string.select_city));
                    IdsCity.add(0);

                    Cities cities = response.body();

                    List<DataCities> dataCities = cities.getData();
                    for (int i = 0; i < dataCities.size(); i++) {
                        getResult = dataCities.get(i).getName();
                        strings.add(getResult);
                        positionCity = dataCities.get(i).getId();
                        IdsCity.add(positionCity);

                    }

                    HelperMethod.showGovernorates(strings, getActivity(), RequestDonationFragmentSPCityRequestDonation);

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getBloodTypes() {
        APIServices apiServicesgetBloodTypes = getRetrofit().create(APIServices.class);
        final Call<BloodTypes> bloodTypesCall = apiServicesgetBloodTypes.getBloodTypes();
        bloodTypesCall.enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                BloodTypes bloodTypes = response.body();
                if (bloodTypes.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.blood_type));
                        IdsBloodType.add(0);
                        List<DataBloodTypes> bloodTypesList = bloodTypes.getData();
                        for (int i = 0; i < bloodTypesList.size(); i++) {
                            getResult = bloodTypesList.get(i).getName();
                            strings.add(getResult);
                            positionBloodType = bloodTypesList.get(i).getId();
                            IdsBloodType.add(positionBloodType);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RequestDonationFragmentSPBloodTypes);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                MapFragment mapFragment = new MapFragment();
                HelperMethod.replece(mapFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, getString(R.string.address_hospital), saveData);
                break;
            case R.id.RequestDonationFragment_BT_Send_Request:
                String name_patient = RequestDonationFragmentETNamePatient.getText().toString().trim();
                String age_patient = RequestDonationFragmentETAgePatient.getText().toString().trim();
                String hospital_name = RequestDonationFragmentETNameHospitalPatient.getText().toString().trim();
                String phone = RequestDonationFragmentETPhonePatient.getText().toString().trim();
                String notes = RequestDonationFragmentETNotesPatient.getText().toString().trim();
                String hospital_address = RequestDonationFragmentETNamePatient.getText().toString().trim();
                String longtude = null;
                String latitude = null;
                if (IdsNumberBackage <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.select_number_package), Toast.LENGTH_SHORT).show();
                    return;
                }
                String number_package_string = String.valueOf(IdsNumberBackage);

                if (IdsCity.isEmpty() || IdsBloodType.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.selct_blood_and_city), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (longtude.isEmpty() || latitude.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.select_map), Toast.LENGTH_SHORT).show();
                    return;
                }
                int city_id = IdsCity.get(RequestDonationFragmentSPCityRequestDonation.getSelectedItemPosition());
                int blood_type = IdsBloodType.get(RequestDonationFragmentSPBloodTypes.getSelectedItemPosition());
                apiServicesgetGovernorate.getDonationRequestCreate(saveData.getApi_token(), name_patient, age_patient
                        , blood_type, number_package_string, hospital_name, hospital_address, city_id, phone, notes, latitude, longtude).enqueue(new Callback<DonationRequestCreate>() {
                    @Override
                    public void onResponse(Call<DonationRequestCreate> call, Response<DonationRequestCreate> response) {
                        DonationRequestCreate donationRequestCreate = response.body();
                        try {
                            if (donationRequestCreate.getStatus() == 1) {
                                Toast.makeText(getActivity(), donationRequestCreate.getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), donationRequestCreate.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationRequestCreate> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}