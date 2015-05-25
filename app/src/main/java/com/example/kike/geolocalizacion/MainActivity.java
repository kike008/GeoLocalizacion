package com.example.kike.geolocalizacion;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Button btnActualizar;
    private Button btnDesactivar;
    private TextView lblLactitud;
    private TextView lblLongitud;
    private TextView lblPrecision;
    private TextView lblEstado;

    private LocationManager locationManager;
    private LocationListener locationListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActualizar = (Button) findViewById(R.id.BtnActualizar);
        btnDesactivar = (Button) findViewById(R.id.BtnDesactivar);

        lblLactitud = (TextView) findViewById(R.id.LblPosLatitud);
        lblLongitud = (TextView) findViewById(R.id.LblPosLongitud);
        lblPrecision = (TextView) findViewById(R.id.LblPosPrecision);
        lblEstado = (TextView) findViewById(R.id.LblEstado);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    actualizarPosicion();
            }
        });

        btnDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(locationListener);
            }
        });
    }

    private void actualizarPosicion()
    {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        muestraPosicion(location);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) { muestraPosicion(location);}
            public void onProviderDisabled(String provider){lblEstado.setText("Provider OFF"); }
            public void onProviderEnabled(String provider) {lblEstado.setText("Provider ON"); }
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("LocAndroid", "Provider Status: " + status);
                lblEstado.setText("Provider Status: " + status);

            }

        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
    }

    private void muestraPosicion(Location location) {
        if (location !=null)
        {
            lblLactitud.setText("Latiitud: " + String.valueOf(location.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(location.getLongitude()));
            lblPrecision.setText("Presicion" + String.valueOf(location.getAccuracy()));
            Log.i("LocAndroid", String.valueOf(location.getLatitude() + " - " + String.valueOf(location.getLongitude())));
        }
        else
        {
            lblLactitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");
            lblPrecision.setText("Presicion: (sin_datos)");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
