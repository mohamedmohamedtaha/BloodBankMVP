package com.example.manasatpc.bloadbank.u.ui.fregmants.userCycle;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.rest.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.rest.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.rest.general.governorates.Governorates;

import com.example.manasatpc.bloadbank.u.data.rest.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.rest.user.register.Register;
import com.example.manasatpc.bloadbank.u.helper.DateModel;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.SelectDateFragment;
import com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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


    @BindView(R.id.Fragment_Name_Regester)
    TextInputEditText FragmentNameRegester;
    @BindView(R.id.Fragment_Email_Regester)
    TextInputEditText FragmentEmailRegester;
    @BindView(R.id.Fragment_Date_Regester)
    TextView FragmentDateRegester;
    @BindView(R.id.Fragment_Blood_Regester)
    TextInputEditText FragmentBloodRegester;
    @BindView(R.id.Fragment_Last_Date_Regester)
    TextView FragmentLastDateRegester;
    @BindView(R.id.Fragment_Phone_Regester)
    TextInputEditText FragmentPhoneRegester;
    @BindView(R.id.Fragment_Password_Regester)
    TextInputEditText FragmentPasswordRegester;
    @BindView(R.id.Fragment_Retry_Password_Regester)
    TextInputEditText FragmentRetryPasswordRegester;
    @BindView(R.id.regester_bt)
    Button regesterBt;
    Unbinder unbinder;
    Bundle bundle;

    String getDate = null;
    @BindView(R.id.Spinner_Gaverment)
    MaterialBetterSpinner SpinnerGaverment;
    @BindView(R.id.Spinner_City)
    MaterialBetterSpinner SpinnerCity;
    public static int getIdGovernorates = 0;
    public static int getIdCities = 0;
    private DateModel dateModel1;
    private DateModel dateModel2;

    final Calendar getDatenow = Calendar.getInstance();

    private int startYear;
    private int startMonth;
    private int startDay;


    public RegesterFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regester, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();


        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);

        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);

        // for get DataPost to governorate Spinner
        APIServices apiServicesgetGovernorate = getRetrofit().create(APIServices.class);
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

                        strings.add("");
                        Ids.add(0);

                        List<GovernoratesData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            strings.add(getResult);
                            Ids.add(governoratesData.get(i).getId());
                        }
                        HelperMethod.showGovernorates(strings, getActivity(), SpinnerGaverment);

//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        //                              android.R.layout.simple_spinner_item, strings);

                        //                       SpinnerGaverment.setAdapter(adapter);

                        SpinnerGaverment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        SpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FragmentDateRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.date), FragmentDateRegester, dateModel1);
            }
        });

        FragmentLastDateRegester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperMethod.showCalender(getActivity(), getString(R.string.last_date), FragmentLastDateRegester, dateModel2);
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
                    Cities cities = response.body();

                    List<DataCities> dataCities = cities.getData();
                    for (int i = 0; i < dataCities.size(); i++) {
                        getResult = dataCities.get(i).getName();
                        strings.add(getResult);

                    }

                    HelperMethod.showGovernorates(strings, getActivity(), SpinnerCity);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.regester_bt)
    public void onViewClicked() {
        // for check network
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
            return;
        }
        String new_user = FragmentNameRegester.getText().toString().trim();
        String email = FragmentEmailRegester.getText().toString().trim();
        String date_birth = FragmentDateRegester.getText().toString().trim();
        String blood_type = FragmentBloodRegester.getText().toString().trim();
        String last_date = FragmentLastDateRegester.getText().toString().trim();
        String phone = FragmentPhoneRegester.getText().toString().trim();
        String password = FragmentPasswordRegester.getText().toString().trim();
        String retry_password = FragmentRetryPasswordRegester.getText().toString().trim();


        APIServices apiServices = getRetrofit().create(APIServices.class);
        Call<Register> registerCall = apiServices.getRegister(new_user, email,
                date_birth, 1, phone,
                last_date, password, retry_password, blood_type);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    Register register = response.body();
                    if (register.getStatus() == 0) {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), register.getMsg(), Toast.LENGTH_LONG).show();
                        LoginFragment loginFragment = new LoginFragment();
                        HelperMethod.replece(loginFragment, getActivity().getSupportFragmentManager(),
                                R.id.Cycle_User_contener, null, null, null);


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

}






















