package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.user.register.Register;
import com.example.manasatpc.bloadbank.u.data.presenter.RegisterPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.RegisterView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class RegisterInteractor implements RegisterPresenter {
    private ArrayList<Integer> IdsCity = new ArrayList<>();
    ArrayList<Integer> IdsBloodType = new ArrayList<>();
    private RegisterView registerView;
    APIServices apiServices = getRetrofit().create(APIServices.class);
    String getResult;
    int IDPosition;
    Integer positionCity;
    Integer positionBloodType;

    public RegisterInteractor(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void onDestroy() {
        registerView = null;
    }

    @Override
    public void register(String new_user, String email, String date_birth, String last_date, String phone, String password,
                         String retry_password,
                         final Context context,
                         final Spinner RegesterFragmentSPCity, final Spinner RegesterFragmentBloodType) {

        if (new_user.isEmpty() || email.isEmpty() || date_birth.isEmpty() || last_date.isEmpty() ||
                phone.isEmpty() || password.isEmpty() || retry_password.isEmpty()) {
            registerView.emptyFiled();
            return;
        }
        if (IdsCity.isEmpty() || IdsBloodType.isEmpty()) {
            registerView.cityEmpty();
            return;
        }

        int blood_type = IdsBloodType.get(RegesterFragmentBloodType.getSelectedItemPosition());
        int cityId = IdsCity.get(RegesterFragmentSPCity.getSelectedItemPosition());
        registerView.showProgress();
        Call<Register> registerCall = apiServices.getRegister(new_user, email,
                date_birth, cityId, phone,
                last_date, password, retry_password, blood_type);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                try {
                    Register register = response.body();
                    if (register.getStatus() == 0) {
                        Toast.makeText(context, register.getMsg(), Toast.LENGTH_LONG).show();
                        registerView.hideProgress();
                    } else {
                        registerView.onSuccess();
                        Toast.makeText(context, register.getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    registerView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                registerView.hideProgress();
            }
        });
    }


    @Override
    public ArrayList<Integer> getGaverment(final Context context, final Spinner RegesterFragmentSPGaverment,
                                           final Spinner RegesterFragmentSPCity) {
        // for get DataPost to governorate Spinner
        final Call<Governorates> governorates = apiServices.getGovernorates();
        final ArrayList<String> strings = new ArrayList<>();
        final ArrayList<Integer> Ids = new ArrayList<>();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        Governorates governorates1 = response.body();
                        strings.add(context.getString(R.string.select_gaverment));
                        Ids.add(0);
                        List<GovernoratesData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            strings.add(getResult);
                            IDPosition = governoratesData.get(i).getId();
                            Ids.add(IDPosition);
                        }
                        HelperMethod.showGovernorates(strings, context, RegesterFragmentSPGaverment);
                        RegesterFragmentSPGaverment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    getCities(Ids.get(position), context, RegesterFragmentSPCity);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return Ids;
    }

    @Override
    public ArrayList<Integer> getCities(int getIdGovernorates, final Context context, final Spinner RegesterFragmentSPCity) {
        final Call<Cities> citiesCall = apiServices.getCities(getIdGovernorates);
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                Cities cities = response.body();
                if (cities.getStatus() == 1) {
                    try {
                        strings.add(context.getString(R.string.select_city));
                        IdsCity.add(0);
                        List<DataCities> dataCities = cities.getData();
                        for (int i = 0; i < dataCities.size(); i++) {
                            getResult = dataCities.get(i).getName();
                            strings.add(getResult);
                            positionCity = dataCities.get(i).getId();
                            IdsCity.add(positionCity);
                        }
                        HelperMethod.showGovernorates(strings, context, RegesterFragmentSPCity);
                        RegesterFragmentSPCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //     Toast.makeText(context, "Position :" + IdsCity.get(RegesterFragmentSPCity.getSelectedItemPosition())
                                //           , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return IdsCity;


    }

    @Override
    public ArrayList<Integer> getBloodTypes(final Context context, final Spinner RegesterFragmentBloodType) {
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
                        strings.add(context.getString(R.string.blood_type));
                        IdsBloodType.add(0);
                        List<DataBloodTypes> bloodTypesList = bloodTypes.getData();
                        for (int i = 0; i < bloodTypesList.size(); i++) {
                            getResult = bloodTypesList.get(i).getName();
                            strings.add(getResult);
                            positionBloodType = bloodTypesList.get(i).getId();
                            IdsBloodType.add(positionBloodType);
                        }
                        HelperMethod.showGovernorates(strings, context, RegesterFragmentBloodType);
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<BloodTypes> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        return IdsBloodType;
    }
}
