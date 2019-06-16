package com.example.manasatpc.bloadbank.u.data.interactor;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.model.EditProfileModel;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.model.user.editprofile.EditProfile;

import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.ClientGetProfile;
import com.example.manasatpc.bloadbank.u.data.model.user.getprofile.GetProfile;
import com.example.manasatpc.bloadbank.u.data.presenter.EditProfilePresenter;
import com.example.manasatpc.bloadbank.u.data.presenter.RegisterPresenter;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.view.EditProfileView;
import com.example.manasatpc.bloadbank.u.data.view.RegisterView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;

public class EditProfileInteractor implements EditProfilePresenter{
    private EditProfileView editProfileView;
    APIServices apiServices = getRetrofit().create(APIServices.class);
    private RememberMy rememberMy;
    private ClientGetProfile clientGetProfile;

    private ArrayList<Integer> IdsCity = new ArrayList<>();
    ArrayList<Integer> IdsBloodType = new ArrayList<>();
    String getResult;
    Integer positionGaverment;
    Integer positionCity;
    Integer positionBloodType;


    public EditProfileInteractor(EditProfileView editProfileView,Context context) {
        this.editProfileView = editProfileView;
        rememberMy = new RememberMy(context);
    }

    @Override
    public void onDestroy() {
        editProfileView = null;

    }

    @Override
    public void getProfile(String APIKey, final Context context ) {
        editProfileView.showProgress();
        final EditProfileModel editProfileModel = new EditProfileModel() ;
        apiServices.getProfile(APIKey).enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response) {
                GetProfile getProfile = response.body();
                clientGetProfile = getProfile.getData().getClient();
                try {
                    if (getProfile.getStatus() == 1) {
                        editProfileModel.setName(clientGetProfile.getName());
                        editProfileModel.setPhone(clientGetProfile.getPhone());
                        editProfileModel.setEmail(clientGetProfile.getEmail());
                        editProfileModel.setBirthDay(clientGetProfile.getBirthDate());
                        editProfileModel.setDonationLastDate(clientGetProfile.getDonationLastDate());
                        editProfileView.retrieveSuccess();
                        editProfileView.hideProgress();
                    } else {
                        Toast.makeText(context, getProfile.getMsg(), Toast.LENGTH_SHORT).show();
                        editProfileView.hideProgress();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    editProfileView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                editProfileView.hideProgress();
            }
        });
    }

    @Override
    public void editProfile(String new_user, String email, String date_birth, String last_date, String phone, String password,
                            String retry_password, final Context context, final Spinner EditInformationFragmentSPBloodType, final Spinner EditInformationFragmentSPCity) {


        if (new_user.isEmpty() || email.isEmpty() || date_birth.isEmpty() || last_date.isEmpty() || phone.isEmpty() || password.isEmpty() || retry_password.isEmpty()) {
            editProfileView.emptyField();
            return;
        }
        int blood_type = IdsBloodType.get(EditInformationFragmentSPBloodType.getSelectedItemPosition());
        int cityId = IdsCity.get(EditInformationFragmentSPCity.getSelectedItemPosition());
        if (blood_type <= 0 || cityId <= 0) {
            editProfileView.emptyCity();
            return;
        }
        Call<EditProfile> profileCall = apiServices.editProfile(new_user, email,
                date_birth, cityId, phone,
                last_date, password, retry_password, blood_type, rememberMy.getAPIKey());
        profileCall.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                try {
                    EditProfile profile = response.body();
                    if (profile.getStatus() == 0) {
                        Toast.makeText(context, profile.getMsg(), Toast.LENGTH_LONG).show();
                        editProfileView.hideProgress();
                    } else {
                        Toast.makeText(context, profile.getMsg(), Toast.LENGTH_LONG).show();
                        editProfileView.editSuccess();
                        editProfileView.hideProgress();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    editProfileView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                editProfileView.hideProgress();
            }
        });

    }


    @Override
    public ArrayList<Integer> getGaverment(final Context context,final Spinner EditInformationFragmentSPGaverment, final Spinner EditInformationFragmentSPCity) {
        final Call<Governorates> governorates = apiServices.getGovernorates();
        final ArrayList<String> stringsGaverment = new ArrayList<>();
        final ArrayList<Integer> IdsGeverment = new ArrayList<>();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        int pio = 0;
                        Governorates governorates1 = response.body();
                        stringsGaverment.add(context.getString(R.string.select_gaverment));
                        IdsGeverment.add(0);
                        List<GovernoratesData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            stringsGaverment.add(getResult);
                            positionGaverment = governoratesData.get(i).getId();
                            IdsGeverment.add(positionGaverment);
                            if (clientGetProfile.getCity().getGovernorate().getId().equals(governoratesData.get(i).getId())) {
                                pio = i + 1;
                            }
                        }
                        HelperMethod.showGovernorates(stringsGaverment, context, EditInformationFragmentSPGaverment);
                        EditInformationFragmentSPGaverment.setSelection(pio);
                        EditInformationFragmentSPGaverment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    getCities(IdsGeverment.get(position),context,EditInformationFragmentSPCity);
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
        return IdsGeverment;
    }

    @Override
    public ArrayList<Integer> getCities(int getIdGovernorates,final Context context, final Spinner EditInformationFragmentSPCity) {

        final Call<Cities> citiesCall = apiServices.getCities(getIdGovernorates);
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                Cities cities = response.body();
                if (cities.getStatus() == 1) {
                    try {
                        String getResult;
                        ArrayList<String> stringsCity = new ArrayList<>();
                        int pio = 0;
                        IdsCity = new ArrayList<>();
                        stringsCity.add(context.getString(R.string.select_city));
                        IdsCity.add(0);
                        List<DataCities> dataCities = cities.getData();
                        for (int i = 0; i < dataCities.size(); i++) {
                            getResult = dataCities.get(i).getName();
                            stringsCity.add(getResult);
                            positionCity = dataCities.get(i).getId();
                            IdsCity.add(positionCity);
                            if (clientGetProfile.getCity().getId().equals(dataCities.get(i).getId())) {
                                pio = i + 1;
                            }
                        }
                        HelperMethod.showGovernorates(stringsCity,context, EditInformationFragmentSPCity);
                        EditInformationFragmentSPCity.setSelection(pio);
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
    public ArrayList<Integer> getBloodTypes(final Context context, final Spinner EditInformationFragmentBloodType) {
        final Call<BloodTypes> bloodTypesCall = apiServices.getBloodTypes();
        bloodTypesCall.enqueue(new Callback<BloodTypes>() {
            @Override
            public void onResponse(Call<BloodTypes> call, Response<BloodTypes> response) {
                String getResult;
                BloodTypes bloodTypes = response.body();
                ArrayList<String> stringsBloodType = new ArrayList<>();

                if (bloodTypes.getStatus() == 1) {
                    try {
                        stringsBloodType.add(context.getString(R.string.blood_type));
                        IdsBloodType.add(0);
                        List<DataBloodTypes> bloodTypesList = bloodTypes.getData();
                        int pio = 0;
                        for (int i = 0; i < bloodTypesList.size(); i++) {
                            getResult = bloodTypesList.get(i).getName();
                            stringsBloodType.add(getResult);
                            positionBloodType = bloodTypesList.get(i).getId();
                            IdsBloodType.add(positionBloodType);
                            if (clientGetProfile.getBloodType().getId().equals(bloodTypesList.get(i).getId())) {
                                pio = i + 1;
                            }
                        }
                        HelperMethod.showGovernorates(stringsBloodType, context, EditInformationFragmentBloodType);

                        EditInformationFragmentBloodType.setSelection(pio);

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
