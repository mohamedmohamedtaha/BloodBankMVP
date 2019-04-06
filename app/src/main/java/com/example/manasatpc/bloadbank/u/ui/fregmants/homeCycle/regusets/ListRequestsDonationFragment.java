package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterDonation;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.DonationRequests;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.BloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.bloodtypes.DataBloodTypes;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.Cities;
import com.example.manasatpc.bloadbank.u.data.model.general.cities.DataCities;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

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
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRequestsDonationFragment extends Fragment {


    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.empty_view_category)
    RelativeLayout emptyViewCategory;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.ListRequestsDonationFragment_IM_Search)
    ImageView ListRequestsDonationFragmentIMSearch;
    @BindView(R.id.ListRequestsDonationFragment_Select_City)
    Spinner ListRequestsDonationFragmentSelectCity;
    @BindView(R.id.ListRequestsDonationFragment_Select_Blood_Type)
    Spinner ListRequestsDonationFragmentSelectBloodType;
    Unbinder unbinder;
    @BindView(R.id.Fab_Request_Donation)
    FloatingActionButton FabRequestDonation;
    private int max = 0;
    ArrayList<Data2DonationRequests> dataDonations = new ArrayList<>();
    private AdapterDonation adapterDonation;
    private APIServices apiServices;
    Bundle bundle;
    private SaveData saveData;
    int IDPosition;
    Integer positionCity;
    Integer positionBloodType;
    int blood_type;
    ArrayList<String> strings = new ArrayList<>();
    final ArrayList<Integer> Ids = new ArrayList<>();
    private ArrayList<Integer> IdsCity = new ArrayList<>();
    final ArrayList<Integer> IdsBloodType = new ArrayList<>();
    public ListRequestsDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_requests_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = getArguments();
        saveData = getArguments().getParcelable(GET_DATA);
        dataDonations.clear();
        getBloodTypes();
        apiServices = getRetrofit().create(APIServices.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {

                }

            }
        };
        list.addOnScrollListener(onEndless);
        adapterDonation = new AdapterDonation(getActivity(), dataDonations, new AdapterDonation.showDetials() {
            @Override
            public void itemShowDetail(Data2DonationRequests position) {
                int donationRequest = position.getId();
                  Toast.makeText(getActivity(), position.getLatitude() + "\n" + position.getLongitude(), Toast.LENGTH_SHORT).show();
                DetailRequestDonationFragment detailRequestDonationFragment = new DetailRequestDonationFragment();
                HelperMethod.replece(detailRequestDonationFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, null, null, saveData);

            }
        }, new AdapterDonation.makeCall() {
            @Override
            public void itemMakeCall(Data2DonationRequests position) {
                String positioncurrent = position.getPhone();
                makePhoneCall(getActivity(), positioncurrent);
            }
        });
        list.setAdapter(adapterDonation);
        getDonation();
        return view;
    }

    private void getDonation() {
//"8KTqGqCh3ofQvl0DySaPNBw0TZODwgxlfZ0nHmWxigWlrKK3cpVLJQEb0bju"

        apiServices.getDonationRequests(saveData.getApi_token())
                .enqueue(new Callback<DonationRequests>() {
                    @Override
                    public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                        try {
                            loadingIndicator.setVisibility(View.VISIBLE);
                            DonationRequests donation = response.body();
                            if (donation.getStatus() == 1) {
                                list.setVisibility(View.VISIBLE);
                                loadingIndicator.setVisibility(View.GONE);
                                emptyViewCategory.setVisibility(View.GONE);
                                dataDonations.addAll(donation.getData().getData());
                                adapterDonation.notifyDataSetChanged();
                            } else {

                                Toast.makeText(getActivity(), donation.getMsg(), Toast.LENGTH_SHORT).show();
                                getProperties();

                            }


                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            getProperties();
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationRequests> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        getProperties();

                    }
                });


    }

    public void getProperties() {
        list.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
        emptyViewCategory.setVisibility(View.VISIBLE);
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

                        HelperMethod.showGovernorates(strings, getActivity(), ListRequestsDonationFragmentSelectBloodType);

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

    private void getCities(int getIdGovernorates) {
        APIServices apiServicesgetCities = getRetrofit().create(APIServices.class);
        final Call<Cities> citiesCall = apiServicesgetCities.getCities(getIdGovernorates);
        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                String getResult;
                ArrayList<String> strings = new ArrayList<>();
                IdsCity = new ArrayList<>();
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
                        HelperMethod.showGovernorates(strings, getActivity(), ListRequestsDonationFragmentSelectCity);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ListRequestsDonationFragment_IM_Search, R.id.Fab_Request_Donation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ListRequestsDonationFragment_IM_Search:
                String textBloodType = ListRequestsDonationFragmentSelectBloodType.getSelectedItem().toString();
                String textBloodT = ListRequestsDonationFragmentSelectBloodType.getSelectedItem().toString();
                // emptyViewCategory.setVisibility(View.VISIBLE);
                adapterDonation.getFilter().filter(textBloodType);
                break;
            case R.id.Fab_Request_Donation:
                RequestDonationFragment requestDonationFragment = new RequestDonationFragment();
                HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation), saveData);

                break;
        }
    }
}


