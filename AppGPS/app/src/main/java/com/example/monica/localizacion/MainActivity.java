package com.example.monica.localizacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    Button btnGPS;
    TextView txvUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvUbicacion = (TextView) findViewById(R.id.txvUbicacion);
        btnGPS = (Button) findViewById(R.id.button);


        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
                final LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        String pattern = "dd-MM-yyyy HH:mm";
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        System.out.println("Latitud:"+location.getLatitude());
                        System.out.println(" Longitud:"+location.getLongitude());
                        System.out.println(" Altitud:"+location.getAltitude());
                        txvUbicacion.setText("\tLatitud:"+location.getLatitude()
                                +"\n\tLongitud:"+location.getLongitude()
                                +"\n\tAltitud:"+location.getAltitude()
                                +"\n\tProveedor: "+location.getProvider()
                                +"\n\tPrecision: "+location.getAccuracy()
                                +"\n\tVelocidad: "+location.getSpeed()
                                +"\n\tBearing: "+location.getBearing()
                                +"\n\tTiempo: "+sdf.format(location.getTime()));

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
// Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }
    }
}
