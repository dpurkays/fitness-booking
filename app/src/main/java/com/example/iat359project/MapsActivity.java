package com.example.iat359project;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
/*
MapsActivity displays map from Google Maps API. Uses users' current location and updates the map
accordingly.
 */

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    // constants
    private static final String TAG = "TAG";
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng defaultLocation = new LatLng(49.2502, -122.8958);

    private long UPDATE_INTERVAL = 10 * 1000;  // 10 secs
    private long FASTEST_INTERVAL = 2000; // 2 sec
    private int PROXIMITY_RADIUS = 1000; // 1km

    private LatLng lastKnownLocation;

    //booleans
    private boolean locationPermissionGranted;
    private boolean buttonClicked = false;

    private LocationCallback locationCallback;

    private Button showGymButton;
    private List<Gym> gymList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();

        showGymButton = (Button) findViewById(R.id.button);

        showGymButton.setOnClickListener(new View.OnClickListener() {
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

    void addLougheedGyms() {
//        List<Gym> gymList = new ArrayList<>();
        Gym fitnessWorld = new Gym("Fitness World", new LatLng(49.2502, -122.8958));
        Gym goodLife = new Gym("Good Life", new LatLng(49.2528, -122.8934));
        Gym steveNash = new Gym("Steve Nash Sports Club", new LatLng(49.2514, -122.8963));
        Gym f45Training = new Gym("F45 Training Lougheed", new LatLng(49.2552, -122.8925));
        Gym fitness2000 = new Gym("Fitness 2000 Athletic Club", new LatLng(49.2518, -122.9014));
        Gym cameronRec = new Gym("Cameron Recreation Complex", new LatLng(49.2539, -122.8992));

        gymList.add(fitnessWorld);
        gymList.add(goodLife);
        gymList.add(steveNash);
        gymList.add(f45Training);
        gymList.add(fitness2000);
        gymList.add(cameronRec);

    }
    void showLougheedGyms() {
        //The marker pin icon is from https://www.flaticon.com/free-icon/fitness-gym_2038640

        String snippet = "Book Now!";
        addLougheedGyms();
        for(int i = 0; i < gymList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(gymList.get(i).getLatLng())
                    .title(gymList.get(i).getName())
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
            mMap.addMarker(markerOptions);
        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
        mMap.setOnInfoWindowClickListener(MapsActivity.this);

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

                            //if showGymbutton was not clicked, change map view to user's current location
                            //otherwise do not show the user location on map view
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

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent i = new Intent(this, SignUpActivity.class);
        i.putExtra("TITLE", marker.getTitle());
        i.putExtra("LAT", marker.getPosition().latitude);
        i.putExtra("LNG", marker.getPosition().longitude);
        Toast.makeText(this, marker.getTitle() + " has been selected for booking.",
                Toast.LENGTH_SHORT).show();

        startActivity(i);
        Log.d(TAG, "clicked");
    }
}