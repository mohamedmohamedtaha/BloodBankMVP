package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.data.rest.APIServices;
import com.example.manasatpc.bloadbank.u.data.model.donation.donationrequest.DonationRequest;
import com.example.manasatpc.bloadbank.u.helper.DrawerLocker;
import com.example.manasatpc.bloadbank.u.helper.SaveData;
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
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.GET_DATA;
import static com.example.manasatpc.bloadbank.u.helper.HelperMethod.makePhoneCall;

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
    Unbinder unbinder;
    @BindView(R.id.mapView)
    MapView mapView;
    private APIServices apiServices;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    Double longitude, latiude;
    SaveData saveData;
    private String savePhone;
    private static final int REQUEST_CALL = 1;

    public DetailRequestDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_request_donation, container, false);
        unbinder = ButterKnife.bind(this, view);
        saveData = getArguments().getParcelable(GET_DATA);
        ((DrawerLocker) getActivity()).setDraweEnabled(false);

        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        apiServices = getRetrofit().create(APIServices.class);
        apiServices.getDonationRequest(saveData.getApi_token(), 1)
                .enqueue(new Callback<DonationRequest>() {
                    @Override
                    public void onResponse(Call<DonationRequest> call, Response<DonationRequest> response) {
                        DonationRequest donationRequest = response.body();
                        if (donationRequest.getStatus() == 1) {
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
                        } else {
                            Toast.makeText(getActivity(), donationRequest.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationRequest> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        mapView.onDestroy();
        super.onDestroyView();
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
        LatLng latLng = new LatLng(longitude, latiude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        gMap.addMarker(markerOptions);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}
