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
import com.example.manasatpc.bloadbank.u.data.interactor.DonationInteractor;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequests.Data2DonationRequests;
import com.example.manasatpc.bloadbank.u.data.presenter.DonationPresenter;
import com.example.manasatpc.bloadbank.u.data.view.DonationView;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.OnEndless;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;
import static com.example.manasatpc.bloadbank.u.ui.activities.HomeActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRequestsDonationFragment extends Fragment implements DonationView {
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
    private DonationPresenter presenter;
    private int max = 0;
    private AdapterDonation adapterDonation;
    Bundle bundle;
    RememberMy rememberMy;
    private ArrayList<Integer> IdsGeverment = new ArrayList<>();
    ArrayList<Integer> IdsBloodType = new ArrayList<>();
    OnEndless onEndless;


    public ListRequestsDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_requests_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new DonationInteractor(this, getActivity());
        rememberMy = new RememberMy(getActivity());
        bundle = new Bundle();
        //    dataDonations.clear();
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        IdsBloodType = HelperMethod.getBloodTypes(getActivity(), ListRequestsDonationFragmentSelectBloodType);
        IdsGeverment = HelperMethod.getGovernorates(getActivity(), ListRequestsDonationFragmentSelectGeverment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ListRequestsDonationFragmentRecyclerView.setLayoutManager(linearLayoutManager);
        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    //getDonation(current_page);
                    presenter.loadData(current_page);

                }
            }
        };
        ListRequestsDonationFragmentRecyclerView.addOnScrollListener(onEndless);
        presenter.loadData(1);

        return view;
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
                int blood_id = IdsBloodType.get(ListRequestsDonationFragmentSelectBloodType.getSelectedItemPosition());
                int geverment_id = IdsGeverment.get(ListRequestsDonationFragmentSelectGeverment.getSelectedItemPosition());
                if (blood_id <= 0 || geverment_id <= 0) {
                    Toast.makeText(getActivity(), getString(R.string.selct_blood_and_Geverment), Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.search(blood_id, geverment_id, 1);
                break;
            case R.id.ListRequestsDonationFragment_FAB_Request_Donation:
                RequestDonationFragment requestDonationFragment = new RequestDonationFragment();
                HelperMethod.replece(requestDonationFragment, getActivity().getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.request_donation));
                break;
        }
    }

    @Override
    public void showProgress() {
        ListRequestsDonationFragmentBrogressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ListRequestsDonationFragmentBrogressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        ListRequestsDonationFragmentRecyclerView.setVisibility(View.VISIBLE);
        ListRequestsDonationFragmentRLEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showRelativeView() {
        ListRequestsDonationFragmentRecyclerView.setVisibility(View.GONE);
        ListRequestsDonationFragmentRLEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSuccess(ArrayList<Data2DonationRequests> dataDonations) {
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
        adapterDonation.notifyDataSetChanged();
        ListRequestsDonationFragmentRecyclerView.setAdapter(adapterDonation);
    }
}