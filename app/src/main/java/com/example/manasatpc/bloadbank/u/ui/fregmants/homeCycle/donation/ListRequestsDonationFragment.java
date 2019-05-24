package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation;


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
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.Governorates;
import com.example.manasatpc.bloadbank.u.data.model.general.governorates.GovernoratesData;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

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
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRequestsDonationFragment extends Fragment {
    @BindView(R.id.ListRequestsDonationFragment_IM_Search)
    ImageView ListRequestsDonationFragmentIMSearch;
    @BindView(R.id.ListRequestsDonationFragment_Select_Geverment)
    Spinner ListRequestsDonationFragmentSelectGeverment;
    @BindView(R.id.ListRequestsDonationFragment_Select_Blood_Type)
    Spinner ListRequestsDonationFragmentSelectBloodType;
    @BindView(R.id.ListRequestsDonationFragment_RecyclerView)
    RecyclerView ListRequestsDonationFragmentRecyclerView;
    @BindView(R.id.ListRequestsDonationFragment_RL_Empty_view)
    RelativeLayout ListRequestsDonationFragmentRLEmptyView;
    @BindView(R.id.ListRequestsDonationFragment_BrogressBar)
    ProgressBar ListRequestsDonationFragmentBrogressBar;
    @BindView(R.id.ListRequestsDonationFragment_FAB_Request_Donation)
    FloatingActionButton ListRequestsDonationFragmentFABRequestDonation;
    Unbinder unbinder;
    public static final String REQUEST_ID = "request_id";

    private int max = 0;
    ArrayList<Data2DonationRequests> dataDonations = new ArrayList<>();
    private AdapterDonation adapterDonation;
    private APIServices apiServices;
    Bundle bundle;
    Integer positionGeverment;
    Integer positionBloodType;
    RememberMy rememberMy;
    private ArrayList<Integer> IdsGeverment = new ArrayList<>();
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
        rememberMy = new RememberMy(getActivity());
        bundle = new Bundle();
        dataDonations.clear();
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        apiServices = getRetrofit().create(APIServices.class);
        getBloodTypes();
        getGovernorates();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ListRequestsDonationFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                }
            }
        };
        ListRequestsDonationFragmentRecyclerView.addOnScrollListener(onEndless);
        adapterDonation = new AdapterDonation(getActivity(), dataDonations, new AdapterDonation.showDetials() {
            @Override
            public void itemShowDetail(Data2DonationRequests position) {
                int donationRequest = position.getId();
                bundle.putInt(REQUEST_ID, donationRequest);
                DetailRequestDonationFragment detailRequestDonationFragment = new DetailRequestDonationFragment();
                HelperMethod.replece(detailRequestDonationFragment, getActivity().getSupportFragmentManager(),
                        R.id.Cycle_Home_contener, toolbar, position.getPatientName(), bundle);
            }
        }, new AdapterDonation.makeCall() {
            @Override
            public void itemMakeCall(Data2DonationRequests position) {
                String positioncurrent = position.getPhone();
                makePhoneCall(getActivity(), positioncurrent);
            }
        });
        ListRequestsDonationFragmentRecyclerView.setAdapter(adapterDonation);
        getDonation();
        return view;
    }

    private void getDonation() {
        apiServices.getDonationRequests(rememberMy.getAPIKey())
                .enqueue(new Callback<DonationRequests>() {
                    @Override
                    public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                        try {
                            dataDonations.clear();
                            ListRequestsDonationFragmentBrogressBar.setVisibility(View.VISIBLE);
                            DonationRequests donation = response.body();
                            if (donation.getStatus() == 1) {
                                dataDonations.addAll(donation.getData().getData());
                                if (!dataDonations.isEmpty()) {
                                    ListRequestsDonationFragmentRecyclerView.setVisibility(View.VISIBLE);
                                    ListRequestsDonationFragmentBrogressBar.setVisibility(View.GONE);
                                    ListRequestsDonationFragmentRLEmptyView.setVisibility(View.GONE);
                                    adapterDonation.notifyDataSetChanged();
                                } else {
                                    getProperties();
                                }
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
        //     ListRequestsDonationFragmentRecyclerView.setVisibility(View.GONE);
        ListRequestsDonationFragmentBrogressBar.setVisibility(View.GONE);
        ListRequestsDonationFragmentRLEmptyView.setVisibility(View.VISIBLE);
    }

    private void getBloodTypes() {
        final Call<BloodTypes> bloodTypesCall = apiServices.getBloodTypes();
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

    private void getGovernorates() {
        final Call<Governorates> governorates = apiServices.getGovernorates();
        governorates.enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        String getResult;
                        Governorates governorates1 = response.body();
                        ArrayList<String> stringsGeverment = new ArrayList<>();
                        stringsGeverment.add(getString(R.string.select_gaverment));
                        IdsGeverment.add(0);
                        List<GovernoratesData> governoratesData = governorates1.getData();
                        for (int i = 0; i < governoratesData.size(); i++) {
                            getResult = governoratesData.get(i).getName();
                            stringsGeverment.add(getResult);
                            positionGeverment = governoratesData.get(i).getId();
                            IdsGeverment.add(positionGeverment);
                        }
                        HelperMethod.showGovernorates(stringsGeverment, getActivity(), ListRequestsDonationFragmentSelectGeverment);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ListRequestsDonationFragment_IM_Search, R.id.ListRequestsDonationFragment_FAB_Request_Donation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ListRequestsDonationFragment_IM_Search:
                if (IdsBloodType.isEmpty() || IdsGeverment.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.selct_blood_and_Geverment), Toast.LENGTH_SHORT).show();
                    return;
                }
                int blood_id = IdsBloodType.get(ListRequestsDonationFragmentSelectBloodType.getSelectedItemPosition());
                int geverment_id = IdsGeverment.get(ListRequestsDonationFragmentSelectGeverment.getSelectedItemPosition());
                dataDonations.clear();
                ListRequestsDonationFragmentBrogressBar.setVisibility(View.VISIBLE);
                apiServices.getDonationRequestFilter(rememberMy.getAPIKey(), blood_id, geverment_id, 1)
                        .enqueue(new Callback<DonationRequests>() {
                            @Override
                            public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                                DonationRequests donationRequestsFilter = response.body();
                                try {
                                    if (donationRequestsFilter.getStatus() == 1) {
                                        dataDonations.addAll(donationRequestsFilter.getData().getData());
                                        if (!dataDonations.isEmpty()) {
                                            ListRequestsDonationFragmentRecyclerView.setVisibility(View.VISIBLE);
                                            ListRequestsDonationFragmentBrogressBar.setVisibility(View.GONE);
                                            ListRequestsDonationFragmentRLEmptyView.setVisibility(View.GONE);
                                            adapterDonation.notifyDataSetChanged();
                                        } else {
                                            getProperties();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), donationRequestsFilter.getMsg(), Toast.LENGTH_SHORT).show();
                                        getProperties();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), donationRequestsFilter.getMsg(), Toast.LENGTH_SHORT).show();
                                    getProperties();
                                }
                            }

                            @Override
                            public void onFailure(Call<DonationRequests> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                getProperties();
                            }
                        });
                break;
            case R.id.ListRequestsDonationFragment_FAB_Request_Donation:
                RequestDonationFragment requestDonationFragment = new RequestDonationFragment();
                HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation));
                break;
        }
    }
}