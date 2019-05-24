package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;


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
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.user.register.Register;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.DateModel;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import java.util.ArrayList;
import java.util.Calendar;
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
public class RegesterFragment extends Fragment {
    @BindView(R.id.RegesterFragment_Name)
    TextInputEditText RegesterFragmentName;
    @BindView(R.id.RegesterFragment_Email)
    TextInputEditText RegesterFragmentEmail;
    @BindView(R.id.RegesterFragment__Date_Of_Birth)
    TextView RegesterFragmentDateOfBirth;
    @BindView(R.id.RegesterFragment_Blood_Type)
    Spinner RegesterFragmentBloodType;
    @BindView(R.id.RegesterFragment_SP_Gaverment)
    Spinner RegesterFragmentSPGaverment;
    @BindView(R.id.RegesterFragment_SP_City)
    Spinner RegesterFragmentSPCity;
    @BindView(R.id.RegesterFragment_Last_Date_Donation)
    TextView RegesterFragmentLastDateDonation;
    @BindView(R.id.RegesterFragment_Phone)
    TextInputEditText RegesterFragmentPhone;
    @BindView(R.id.RegesterFragment_Password)
    TextInputEditText RegesterFragmentPassword;
    @BindView(R.id.RegesterFragment_Retry_Password)
    TextInputEditText RegesterFragment_Retry_Password;
    @BindView(R.id.RegesterFragment_BT_Regester)
    Button RegesterFragmentBTRegester;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.RegesterFragment_Progress_Bar)
    ProgressBar RegesterFragmentProgressBar;
    Unbinder unbinder;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;
    //for gaverment and cities
    String getResult;
    int IDPosition;
    Integer positionCity;
    Integer positionBloodType;
    int blood_type;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> Ids = new ArrayList<>();
    private ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsBloodType = new ArrayList<>();
    APIServices apiServices;

    public RegesterFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regester, container, false);
        unbinder = ButterKnife.bind(this, view);
        getBloodTypes();
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        getGaverment();
        RegesterFragmentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date), RegesterFragmentDateOfBirth, dateModel1);
            }
        });
        RegesterFragmentLastDateDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date), RegesterFragmentLastDateDonation, dateModel2);
            }
        });
        return view;
    }

    private void getGaverment() {
        // for get DataPost to governorate Spinner
        apiServices = getRetrofit().create(APIServices.class);
        final Call<Governorates> governorates = apiServices.getGovernorates();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        Governorates governorates1 = response.body();
                        strings.add(getString(R.string.select_gaverment));
                        Ids.add(0);
                        List<GovernoratesData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            strings.add(getResult);
                            IDPosition = governoratesData.get(i).getId();
                            Ids.add(IDPosition);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RegesterFragmentSPGaverment);
                        RegesterFragmentSPGaverment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    getCities(Ids.get(position));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

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
    }

    private void getCities(int getIdGovernorates) {
        APIServices apiServicesgetCities = getRetrofit().create(APIServices.class);
        final Call<Cities> citiesCall = apiServicesgetCities.getCities(getIdGovernorates);
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Cities cities = response.body();
                if (cities.getStatus() == 1) {
                    try {
                        strings.add(getString(R.string.select_city));
                        IdsCity.add(0);
                        List<DataCities> dataCities = cities.getData();
                        for (int i = 0; i < dataCities.size(); i++) {
                            getResult = dataCities.get(i).getName();
                            strings.add(getResult);
                            positionCity = dataCities.get(i).getId();
                            IdsCity.add(positionCity);
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), RegesterFragmentSPCity);
                        RegesterFragmentSPCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity(), "Position :" + IdsCity.get(RegesterFragmentSPCity.getSelectedItemPosition())
                                        , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
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
                        HelperMethod.showGovernorates(strings, getActivity(), RegesterFragmentBloodType);
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

    @OnClick(R.id.RegesterFragment_BT_Regester)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String new_user = RegesterFragmentName.getText().toString().trim();
        String email = RegesterFragmentEmail.getText().toString().trim();
        String date_birth = RegesterFragmentDateOfBirth.getText().toString().trim();
        String last_date = RegesterFragmentLastDateDonation.getText().toString().trim();
        String phone = RegesterFragmentPhone.getText().toString().trim();
        String password = RegesterFragmentPassword.getText().toString().trim();
        String retry_password = RegesterFragment_Retry_Password.getText().toString().trim();
        if (new_user.isEmpty() || email.isEmpty() || date_birth.isEmpty() || last_date.isEmpty() ||
                phone.isEmpty() || password.isEmpty() || retry_password.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.all_filed_request), Toast.LENGTH_SHORT).show();
            return;
        }
        if (IdsCity.isEmpty() || IdsBloodType.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.selct_blood_and_city), Toast.LENGTH_SHORT).show();
            return;
        }
        blood_type = IdsBloodType.get(RegesterFragmentBloodType.getSelectedItemPosition());
        int cityId = IdsCity.get(RegesterFragmentSPCity.getSelectedItemPosition());
        RegesterFragmentProgressBar.setVisibility(View.VISIBLE);
        APIServices apiServices = getRetrofit().create(APIServices.class);
        Call<Register> registerCall = apiServices.getRegister(new_user, email,
                date_birth, cityId, phone,
                last_date, password, retry_password, blood_type);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    Register register = response.body();
                    if (register.getStatus() == 0) {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                        RegesterFragmentProgressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                        RegesterFragmentProgressBar.setVisibility(View.GONE);
                        LoginFragment loginFragment = new LoginFragment();
                        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_User_contener, null, null);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    RegesterFragmentProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                RegesterFragmentProgressBar.setVisibility(View.GONE);
            }
        });
    }
}