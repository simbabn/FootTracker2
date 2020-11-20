package fr.android.nazim.foottracker2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class LocationMatch extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSION_FINE_LOCATION = 99;//anyone => for the request code in the onRequestPermissionResult
    TextView longName, varLong, latName, varLat, address, varAddress, sensor, varSensor;
    Switch swLoc, swGPS;

    boolean updateOn = false;

    //Location Request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallBack;

    //Google's API for location services. The majority of the app functions using this class
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_match);

        longName = findViewById(R.id.longName);
        varLong = findViewById(R.id.varLong);
        latName = findViewById(R.id.latName);
        varLat = findViewById(R.id.varLat);
        address = findViewById(R.id.address);
        varAddress = findViewById(R.id.varAddress);
        swLoc = findViewById(R.id.locUpdate);
        swGPS = findViewById(R.id.gpsSavePower);
        sensor = findViewById(R.id.sensor);
        varSensor = findViewById(R.id.varSensor);

        //set all propoerties of LocationRequest

        locationRequest = new LocationRequest(); // instanciation

        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL); //how often the default location check occur?
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL); //how often does the location check occur when set to the most frequent update?

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //event is trigged whenever the update interval is met
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // save the location
                updateValues(locationResult.getLastLocation());
            }
        };

        swGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swGPS.isChecked()) {
                    //most accurate - use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    //Using GPS sensors
                    varSensor.setText("Using GPS sensors");

                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    //Using Towers + Wifi
                    varSensor.setText("Using Towers + Wifi");
                }
            }
        });

        swLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swLoc.isChecked()) {
                    //turn on location tracking
                    startLocationUpdates();
                } else {
                    //turn off tracking
                    stopLocationUpdates();
                }
            }
        });

        updateGPS();

    }//END ONCREATE()


    @SuppressLint("MissingPermission")
    private void stopLocationUpdates() {


        varAddress.setText("Not Tracking location");
        varLong.setText("Not Tracking location");
        varLat.setText("Not Tracking location");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);

    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();
    }

    //tells the programm to trigger a method after permission have been granted
    //requestCode = 99, Permission wich is which thin that actually get permission for
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_FINE_LOCATION:
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                updateGPS();
            }
            else{
                Toast.makeText(this,"This app requiers permission to be granted in order to work properly",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void updateGPS(){
        //get permission
        //get current location from FusedClient
        //updateTheUi

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationMatch.this);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permissions, put the values in the UI Components
                    updateValues(location);
                }
            });
        }else{
            //permissions not granted yet
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void updateValues(Location location) {
        //update all of the values in UI
        varLat.setText(String.valueOf(location.getLatitude()));
        varLong.setText(String.valueOf(location.getLongitude()));

        Geocoder geocoder = new Geocoder(LocationMatch.this);

        try{
            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            varAddress.setText(address.get(0).getAddressLine(0));
        }
        catch (Exception e){
            varAddress.setText("Unable to get street address");
        }
    }
}