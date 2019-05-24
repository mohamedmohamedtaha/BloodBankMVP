package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manasatpc.bloadbank.u.data.rest.RetrofitClient.getRetrofit;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;
import static com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.donation.ListRequestsDonationFragment.REQUEST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRequestDonationFragment extends Fragment implements OnMapReadyCallback {
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
    private APIServices apiServices;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    Double longitude = null;
    Double latiude = null;
    private String savePhone;
    private static final int REQUEST_CALL = 1;

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
        bundle = getArguments();
        int requestId = bundle.getInt(REQUEST_ID);
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        ((DrawerLocker) getActivity()).setDraweEnabled(false);
        apiServices = getRetrofit().create(APIServices.class);
        DetailRequestDonationFragmentProgressBar.setVisibility(View.VISIBLE);
        apiServices.getDonationRequest(rememberMy.getAPIKey(), requestId)
                .enqueue(new Callback<DonationRequest>() {
                    @Override
                    public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                        DonationRequest donationRequest = response.body();
                        try {
                            if (donationRequest.getStatus() == 1) {
                                DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);
                                TVShowNameDetailsDonation.append(donationRequest.getData().getPatientName());
                                TVShowAgeDetailsDonation.append(donationRequest.getData().getPatientAge());
                                TVShowTypeBloodDetailsDonation.append(donationRequest.getData().getBloodType().getName());
                                TVShowNumberPackageRequestDetailsDonation.append(donationRequest.getData().getBagsNum());
                                TVShowHospitalDetailsDonation.append(donationRequest.getData().getHospitalName());
                                TVShowAddressHospitalDetailsDonation.append(donationRequest.getData().getHospitalAddress());
                                savePhone = donationRequest.getData().getPhone();
                                TVShowPhoneDetailsDonation.append(savePhone);
                                TVShowDetailsDetailsDonation.append(donationRequest.getData().getNotes());
                                latiude = Double.parseDouble(donationRequest.getData().getLatitude());
                                longitude = Double.parseDouble(donationRequest.getData().getLongitude());
                                Bundle mapViewBundle = null;
                                if (savedInstanceState != null) {
                                    mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
                                }
                                mapView.onCreate(mapViewBundle);
                                mapView.getMapAsync(DetailRequestDonationFragment.this);
                                mapView.onStart();
                            } else {
                                Toast.makeText(getActivity(), donationRequest.getMsg(), Toast.LENGTH_SHORT).show();
                                DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<DonationRequest> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        DetailRequestDonationFragmentProgressBar.setVisibility(View.GONE);
                    }
                });
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.BT_Call)
    public void onViewClicked() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
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
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);
        if (longitude != null || latiude != null) {
            LatLng latLng = new LatLng(longitude, latiude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

    }
}
