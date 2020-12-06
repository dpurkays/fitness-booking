package com.example.iat359project;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
/*
MapsActivity displays map from Google Maps API. Uses users' current location and updates the map
accordingly.
 */

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private static final String TAG = "TAG";
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LatLng defaultLocation = new LatLng(49.2502, -122.8958);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    private LatLng lastKnownLocation;
    private LocationCallback locationCallback;

    private long UPDATE_INTERVAL = 10 * 1000;  // 10 secs
    private long FASTEST_INTERVAL = 2000; // 2 sec
    private int PROXIMITY_RADIUS = 1000; // 1km

    private Button button;
    private LocationRequest locationRequest;
    private boolean buttonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //TODO: change map view to another location and hardcode the gyms. put markers on gym
                buttonClicked = true;

                //change to Lougheed Mall's location
                LatLng lougheedMall = new LatLng(49.2531, -122.8949);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lougheedMall ,DEFAULT_ZOOM));
                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                //show gym markers by Lougheed Mall
                showLougheedGyms();

                // Due to the cost associate to Google Places API, the following function
                // has been commented out.
//              showNearbyGyms(lastKnownLocation);
            }
        });

    }

    void showLougheedGyms() {
        //The marker pin icon is from https://www.flaticon.com/free-icon/fitness-gym_2038640

        //Fitness World
        MarkerOptions fitnessWorldMarker = new MarkerOptions();
        String fitnessWorldString = "Fitness World";
        LatLng fitnessWorldLatLng = new LatLng(49.2502, -122.8958);
        fitnessWorldMarker.position(fitnessWorldLatLng)
                .title(fitnessWorldString)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(fitnessWorldMarker);

        //GoodLife
        MarkerOptions GoodLifeMarker = new MarkerOptions();
        String GoodLifeString = "GoodLife";
        LatLng GoodLifeLatLng = new LatLng(49.2528, -122.8934);
        GoodLifeMarker.position(GoodLifeLatLng)
                .title(GoodLifeString)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(GoodLifeMarker);

        //Steve Nash Sports Club
        MarkerOptions SteveNashMarker = new MarkerOptions();
        String SteveNashString = "Steve Nash Sports Club";
        LatLng SteveNashLatLng = new LatLng(49.2514, -122.8963);
        SteveNashMarker.position(SteveNashLatLng)
                .title(SteveNashString)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(SteveNashMarker);

        //f45 Training Lougheed
        MarkerOptions f45TrainingMarker = new MarkerOptions();
        String f45TrainingString = "F45 Training Lougheed";
        LatLng f45TrainingLatLng = new LatLng(49.2552, -122.8925);
        f45TrainingMarker.position(f45TrainingLatLng)
                .title(f45TrainingString)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(f45TrainingMarker);

        //Fitness 2000 Athletic Club
        MarkerOptions fitness2000Marker = new MarkerOptions();
        String fitness2000String = "Fitness 2000 Athletic Club";
        LatLng fitness2000LatLng = new LatLng(49.2518, -122.9014);
        fitness2000Marker.position(fitness2000LatLng)
                .title(fitness2000String)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(fitness2000Marker);

        //Cameron Recreation Complex
        MarkerOptions cameronRecMarker = new MarkerOptions();
        String cameronRecString = "Cameron Recreation Complex";
        LatLng cameronRecLatLng = new LatLng(49.2539, -122.8992);
        cameronRecMarker.position(cameronRecLatLng)
                .title(cameronRecString)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
        mMap.addMarker(cameronRecMarker);

    }

    // Due to the cost associate to Google Places API, the following method is not used.
    // This method works!
    void showNearbyGyms(LatLng latLng) {
        mMap.clear();
        String url = getUrl(latLng);
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        Log.d(TAG, "Showing nearby gyms");
    }

    // This method works but uses Google Places API.
    private String getUrl(LatLng latLng) {
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latLng.latitude+","+latLng.longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+"gym");
        googlePlaceUrl.append("&key="+ getString(R.string.google_maps_key));
        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationPermission();

        if (locationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
        }


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    getLastLocation();
                }
            }
        };


    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getLastLocation() {
        try {
            if (locationPermissionGranted) {

                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    lastKnownLocation = new LatLng(
                                            location.getLatitude(),
                                            location.getLongitude());
                                    startLocationUpdates();
                                } else {
                                    Log.d(TAG, "Current location is null. Using defaults.");
                                    mMap.moveCamera(CameraUpdateFactory
                                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                }
                            }
                        });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                    getLastLocation();
                }
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    private void showUserLocation(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latLng.latitude,
                latLng.longitude),
                DEFAULT_ZOOM));
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);



        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                        if(mMap != null) {
                            lastKnownLocation = new LatLng(locationResult.getLastLocation().getLatitude(),
                                    locationResult.getLastLocation().getLongitude());

                            //if button was not clicked, change map view to user's current location
                            if (!buttonClicked) {
                                showUserLocation(lastKnownLocation);
                            }
                        }
                    }
                },
                Looper.getMainLooper());

    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public void onLocationChanged(Location location) {
        Log.d(TAG, "Updated Location: " +
                ""+ (location.getLatitude()) + "," +
                "" + (location.getLongitude()));
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lastKnownLocation = latLng;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationPermissionGranted) {
            getLastLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


}