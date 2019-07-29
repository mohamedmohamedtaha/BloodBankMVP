package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manasatpc.bloadbank.R;
import com.example.manasatpc.bloadbank.u.adapter.AdapterInfoWindow;
import com.example.manasatpc.bloadbank.u.helper.HelperMethod;
import com.example.manasatpc.bloadbank.u.helper.PlaceAutocompleteAdapter;
import com.example.manasatpc.bloadbank.u.helper.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,  GoogleApiClient.OnConnectionFailedListener
{
    @BindView(R.id.input_search)
    AutoCompleteTextView inputSearch;
    @BindView(R.id.ic_gps)
    ImageView icGps;
    @BindView(R.id.MapFragment_Select_Location)
    Button MapFragmentSelectLocation;
    @BindView(R.id.ic_info_location)
    ImageView icInfoLocation;
    Unbinder unbinder;


    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 12;
    //The entry point to the Fused Loaction Provider
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mGoogleMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168)
            , new LatLng(71, 136));
    private static final int PLACE_PICKER_REQUEST = 1;
    public static final String LATITUDE_MAP = "latitude";
    public static final String LONGITUDE_MAP = "longitude";
    Bundle bundle;
    private PlaceInfo mPlace;
    private Marker mMarker;
    public static double latitude;
    public static double longitude;
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Retricve location and camira position from savedinstance state.
      /*  if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mcameraPosition = savedInstanceState.getParcelable(KEY_CAMOIRA_POSITION);
        }*/
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        bundle = new Bundle();
        boolean check_network = HelperMethod.isNetworkConnected(getActivity(), getView());
        if (check_network == false) {
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //For check Location Enable or not
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getDeviceLocation();
        } else {
            showGPSDosable();

        }
        if (client == null) {
            client = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), this).build();
        }

        inputSearch.setOnItemClickListener(mOnItemClickListener);
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), client, LAT_LNG_BOUNDS, null);
        inputSearch.setAdapter(mPlaceAutocompleteAdapter);

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        client.stopAutoManage(getActivity());
        client.disconnect();
    }

    private void geoLocate() {
        String search = inputSearch.getText().toString().trim();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(search, 1);
        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            // Toast.makeText(getActivity(), address.toString(), Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            moveCamera(latLng,
                    DEFAULT_ZOOM, address.getAddressLine(0));

        }

    }


/*
    // Saves the state of the map when the activity is paued
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mGoogleMap != null) {
            outState.putParcelable(KEY_CAMOIRA_POSITION, mGoogleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
           getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

                return;
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //create a marker
                    MarkerOptions markerOptions = new MarkerOptions();
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;

                    //setting the position for the marker
                    markerOptions.position(latLng);
                    markerOptions.title(latitude + ":  " + longitude);
                    // clear the previously touch position
                    mGoogleMap.clear();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    //placing a marker on the touched position
                    mGoogleMap.addMarker(markerOptions);
                }
            });


    }

    private void getDeviceLocation() {
        /* Get the best and most recent location of the device, which may be null in rare
        cases when a location isnotavailable
         */
        //Construct a FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Toast.makeText(getActivity(), "find location!", Toast.LENGTH_SHORT).show();
                            //Set the map's camera position to the current location of the device.
                            //  mLastKnownLocation = task.getResult();
                            Location location = (Location) task.getResult();

                            moveCamera(new LatLng(location.getLatitude(), location.getLongitude()),
                                    DEFAULT_ZOOM, "My Location");
                        } else {
                        //    getLocationPermission();
                            //  Toast.makeText(getActivity(), "Current Location is null. Using defaults.", Toast.LENGTH_SHORT).show();
                            //  mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });

        } catch (SecurityException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Prompts the user for permission to use the device location.
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ActivityCompat.checkSelfPermission(getContext(),
//                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(),
//                COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            showGPSDosable();
//         }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     //   mLocationPermissionGranted = false;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "No Permitions Granted", Toast.LENGTH_SHORT).show();
        } else {
        }

       /* switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                //If request is ancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Toast.makeText(getActivity(), "permission failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void hideSoftKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard();
            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(client, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Toast.makeText(getActivity(), "Place query did not complete successfully:"
                        + places.getStatus().toString(), Toast.LENGTH_SHORT).show();
                places.release();
                return;
            }
            final Place place = places.get(0);
            try {
                mPlace = new PlaceInfo();
                mPlace.setId(place.getId());
                mPlace.setName(place.getName().toString());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setAttrbuties(place.getAttributions());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setLatLng(place.getLatLng());
                mPlace.setRate(place.getRating());
                mPlace.setUrl(place.getWebsiteUri());
                Toast.makeText(getActivity(), "Place: " + mPlace.toString(), Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            LatLng latLng = new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude);
            moveCamera(latLng, DEFAULT_ZOOM, mPlace);
            places.release();
        }
    };

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mGoogleMap.clear();
        mGoogleMap.setInfoWindowAdapter(new AdapterInfoWindow(getActivity()));
        if (placeInfo != null) {
            try {
                String snippet = "LAt:" + placeInfo.getLatLng() + "\n" +
                        "Address:" + placeInfo.getAddress() + "\n" +
                        "Phone number:" + placeInfo.getPhoneNumber() + "\n" +
                        "webSite:" + placeInfo.getUrl() + "\n";
                MarkerOptions options = new MarkerOptions()
                        .position(latLng).title(placeInfo.getName()).snippet(snippet);
                mMarker = mGoogleMap.addMarker(options);


            } catch (NullPointerException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                PendingResult<PlaceBuffer> pendingResult = Places.GeoDataApi
                        .getPlaceById(client, place.getId());
                pendingResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mGoogleMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    @OnClick({R.id.ic_gps, R.id.MapFragment_Select_Location, R.id.ic_info_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_gps:
                getDeviceLocation();
                break;
            case R.id.MapFragment_Select_Location:
                if (longitude == 0 || latitude == 0) {
                    Toast.makeText(getActivity(), getString(R.string.no_place), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.ic_info_location:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "GooglePlayServicesNotAvailableException \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (GooglePlayServicesRepairableException e) {
                    Toast.makeText(getActivity(), "GooglePlayServicesRepairableException \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void showGPSDosable() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("GPS is disable in your device. would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page to Enable GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }


  }











