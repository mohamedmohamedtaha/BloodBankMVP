package com.example.manasatpc.bloadbank.u.ui.fregmants.homeCycle.regusets;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.example.manasatpc.bloadbank.u.helper.GPSTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    @BindView(R.id.mapView)
    MapView mapView;
    Unbinder unbinder;
    @BindView(R.id.BT_Get_Location)
    Button BTGetLocation;
    @BindView(R.id.TV_Show_Direction)
    TextView TVShowDirection;

    private GoogleMap googleMap;
    private CameraPosition mcameraPosition;

    //The entry points to the Places API
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    //The entry point to the Fused Loaction Provider
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney , Australia ) and default zoom to use when location permission is not granted
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    //The geographical location where the device is currently located. That i, the last-known location retrieved
    //by the Fused Location Provider
    private Location mLastKnownLocation;

    //Keys for storing activity state.
    private static final String KEY_CAMOIRA_POSITION = "camira_position";
    private static final String KEY_LOCATION = "location";

    //Used for selecting th ecurrent place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAdresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);

        //Retricve location and camira position from savedinstance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mcameraPosition = savedInstanceState.getParcelable(KEY_CAMOIRA_POSITION);
        }

        //  client = LocationServices.getFusedLocationProviderClient(getActivity());

//Construct a GeoDataClient
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        //Construct a PlaceDetectionClient
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);

        //Construct a FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        mapView.getMapAsync(this);

        //  showCurrentPlace();

        return view;
    }

    // Saves the state of the map when the activity is paued
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (googleMap != null) {
            outState.putParcelable(KEY_CAMOIRA_POSITION, googleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;

        GPSTracker gpsTracker = new GPSTracker(getActivity(), getActivity());
        gpsTracker.getLocation();
/*
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
              //  View infoWindow = getLayoutInflater().inflate(R.layout.custom_inf)
                return null;
            }
        });
*/
        //Prompt the user for permission
        getLocationPermission();

        //Turn on the my location layer and the related control on the map
        updateLocationUI();

        //Get the current location of the device and set the position of the map
        getDeviceLocation();
        /*
        googleMap.setIndoorEnabled(true);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        //For dropping a marker at a point on the Map
        LatLng ss = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(ss).title("Name").snippet("Desc"));


        //For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ss).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
*/

    }

    private void getDeviceLocation() {
        /* Get the best and most recent location of the device, which may be null in rare
        cases when a location isnotavailable
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            //Set the map's camera position to the current location of the device.
                            //   if (mLastKnownLocation !=null)
                            mLastKnownLocation = task.getResult();
                            Double Latiude = mLastKnownLocation.getLatitude();
                            Double longtude = mLastKnownLocation.getLongitude();

//                           googleMap.setMinZoomPreference(12);
                        //    LatLng latLng = new LatLng(longtude, Latiude);
                          //  MarkerOptions markerOptions = new MarkerOptions();
                            //markerOptions.position(latLng);
                           // googleMap.addMarker(markerOptions);
                           // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            // Toast.makeText(getActivity(), "Current Location: " + Latiude +
                            //       longtude, Toast.LENGTH_SHORT).show();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(Latiude, longtude),
                                    DEFAULT_ZOOM));
                        } else {
                            Toast.makeText(getActivity(), "Current Location is null. Usinf defaults.", Toast.LENGTH_SHORT).show();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });

            }
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Prompts the user for permission to use the device location.
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission
                .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                //If request is ancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }

            }
        }
        updateLocationUI();
    }

    /*
    Prompts the user to select the current place from a list of likely places, and shows the
    current place on the map - provided the user has granted locationpermission
     */
    private void showCurrentPlace() {
        if (googleMap == null) {
            return;
        }
        if (mLocationPermissionGranted) {

            @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult =
                    mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                        int count;
                        if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
                            count = likelyPlaces.getCount();
                        } else {
                            count = M_MAX_ENTRIES;
                        }
                        int i = 0;
                        mLikelyPlaceNames = new String[count];
                        mLikelyPlaceAdresses = new String[count];
                        mLikelyPlaceAttributions = new String[count];
                        mLikelyPlaceLatLngs = new LatLng[count];

                        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                            mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                            mLikelyPlaceAdresses[i] = (String) placeLikelihood.getPlace().getAddress();
                            mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
                            mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
                            i++;
                            if (i > (count - 1)) {
                                break;
                            }

                        }
                        likelyPlaces.release();
                        openPlacesDialog();
                    } else {
                        Toast.makeText(getActivity(), "Not :" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Theuser didnot grant location permission.", Toast.LENGTH_SHORT).show();


            // Add a default marker, because the user hasn't selected a place.
            googleMap.addMarker(new MarkerOptions()
                    .title("Title")
                    .position(mDefaultLocation)
                    .snippet("Snap"));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The "which" argument contains the position of the selected item.
                LatLng markerLatLng = mLikelyPlaceLatLngs[which];
                String markerSnippet = mLikelyPlaceAdresses[which];
                if (mLikelyPlaceAttributions[which] != null) {
                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
                }

                // Add a marker for the selected place, with an info window
                // showing information about that place.
                googleMap.addMarker(new MarkerOptions()
                        .title(mLikelyPlaceNames[which])
                        .position(markerLatLng)
                        .snippet(markerSnippet));

                // Position the map's camera at the location of the marker.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                        DEFAULT_ZOOM));
            }
        };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Place")
                .setItems(mLikelyPlaceNames, listener)
                .show();
    }

    /*
Updates the map's UI settings based on whether the user has granted location permission.
 */
    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Handles the result of the request for locationpermissions
     */
    @OnClick(R.id.BT_Get_Location)
    public void onViewClicked() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "لابد من اخذ الاذن أولا ", Toast.LENGTH_SHORT).show();
            // TODO: Consider calling

            return;
        }

    }
}
