package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.interactor.DetailsDonationInteractor;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DataDonationRequest;
import com.example.manasatpc.bloadbank.u.data.presenter.DetailsDonationPresenter;
import com.example.manasatpc.bloadbank.u.data.view.DetailsDonationView;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.RememberMy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.ListRequestsDonationFragment.REQUEST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestDonationFragment extends Fragment implements OnMapReadyCallback, DetailsDonationView {
    @BindView(R.id.TV_Show_Name_Details_Donation)
    TextView TVShowNameDetailsDonation;
    @BindView(R.id.TV_Show_Age_Details_Donation)
    TextView TVShowAgeDetailsDonation;
    @BindView(R.id.TV_Show_Type_Blood_Details_Donation)
    TextView TVShowTypeBloodDetailsDonation;
    @BindView(R.id.TV_Show_Number_Package_Request_Details_Donation)
    TextView TVShowNumberPackageRequestDetailsDonation;
    @BindView(R.id.TV_Show_Hospital_Details_Donation)
    TextView TVShowHospitalDetailsDonation;
    @BindView(R.id.TV_Show_Address_Hospital_Details_Donation)
    TextView TVShowAddressHospitalDetailsDonation;
    @BindView(R.id.TV_Show_Phone_Details_Donation)
    TextView TVShowPhoneDetailsDonation;
    @BindView(R.id.TV_Show_Details_Details_Donation)
    TextView TVShowDetailsDetailsDonation;
    @BindView(R.id.BT_Call)
    Button BTCall;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.DetailRequestDonationFragment_Progress_Bar)
    ProgressBar DetailRequestDonationFragmentProgressBar;
    Unbinder unbinder;
    RememberMy rememberMy;
    Bundle bundle;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    Double longitude = null;
    Double latiude = null;
    private String savePhone;
    private static final int REQUEST_CALL = 1;
    private DetailsDonationPresenter presenter;
    Bundle mapViewBundle = null;

    public DetailRequestDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_request_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        rememberMy = new RememberMy(getActivity());
        presenter = new DetailsDonationInteractor(this, getActivity());
        bundle = getArguments();
        int requestId = bundle.getInt(REQUEST_ID);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        } else {
            ((DrawerLocker) getActivity()).setDraweEnabled(false);

            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
            }
            presenter.loadData(requestId);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapView != null) {
            mapView.onDestroy();
        }
        unbinder.unbind();
    }

    @OnClick(R.id.BT_Call)
    public void onViewClicked() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            makePhoneCall(getActivity(), savePhone);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(getActivity(), savePhone);
            } else {
                Toast.makeText(getActivity(), getString(R.string.notPermission), Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        if (longitude != null || latiude != null) {
            LatLng latLng = new LatLng(latiude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress() {
        DetailRequestDonationFragmentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSuccess(DataDonationRequest donationRequest) {
        if (donationRequest != null) {
            DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);
            TVShowNameDetailsDonation.append(donationRequest.getPatientName());
            TVShowAgeDetailsDonation.append(donationRequest.getPatientAge());
            TVShowTypeBloodDetailsDonation.append(donationRequest.getBloodType().getName());
            TVShowNumberPackageRequestDetailsDonation.append(donationRequest.getBagsNum());
            TVShowHospitalDetailsDonation.append(donationRequest.getHospitalName());
            TVShowAddressHospitalDetailsDonation.append(donationRequest.getHospitalAddress());
            savePhone = donationRequest.getPhone();
            TVShowPhoneDetailsDonation.append(savePhone);
            TVShowDetailsDetailsDonation.append(donationRequest.getNotes());
            latiude = Double.parseDouble(donationRequest.getLatitude());
            longitude = Double.parseDouble(donationRequest.getLongitude());
            mapView.onCreate(mapViewBundle);
            mapView.getMapAsync(DetailRequestDonationFragment.this);
            mapView.onStart();

        }
    }
}