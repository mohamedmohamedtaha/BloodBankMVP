package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterDonation;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.DonationRequests;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.SaveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;

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
    Unbinder unbinder;
    private int max = 0;
    ArrayList<Data2DonationRequests> dataDonations = new ArrayList<>();
    private AdapterDonation adapterDonation;
    private APIServices apiServices;
    Bundle bundle;
    private SaveData saveData;

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
                //  Toast.makeText(getActivity(), "AA : " + donationRequest, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
