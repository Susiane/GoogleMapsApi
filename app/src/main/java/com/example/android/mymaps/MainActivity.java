package com.example.android.mymaps;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GoogleMap mMap;
    private static final double
            SEATTLE_LAT = 47.60621,
            SEATTLE_LNG =-122.33207,
            SYDNEY_LAT = -33.867487,
            SYDNEY_LNG = 151.20699,
            NEWYORK_LAT = 40.714353,
            NEWYORK_LNG = -74.005973;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(servicesOK()){
            setContentView(R.layout.activity_map);

            if(initMap()){
                Toast.makeText(this, "Ready to map!",Toast.LENGTH_SHORT).show();
                gotoLocation(SEATTLE_LAT,SEATTLE_LNG, 15);
            } else{
                Toast.makeText(this, "Map not connected!",Toast.LENGTH_SHORT).show();
            }
        }else{
            setContentView(R.layout.activity_main);
        }
    }

    public boolean servicesOK(){
        int isAvaliable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(isAvaliable == ConnectionResult.SUCCESS){
            return true;
        } else if(GooglePlayServicesUtil.isUserRecoverableError(isAvaliable)){
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvaliable,this,ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service",Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private boolean initMap(){
        if (mMap == null){
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }
        return(mMap != null);
    }

    private void gotoLocation(double lat, double lng, float zoom){
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    public void geoLocate(View v) throws IOException {

        hideSoftKeyboard(v);

        TextView tv = (TextView) findViewById(R.id.editText1);
        String searchString = tv.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(searchString, 1); //segundo argumento é maximo de resultados que eu quero receber

        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this, "Found: " + locality, Toast.LENGTH_SHORT).show();

            double lat = add.getLatitude();
            double lng = add.getLongitude();
            gotoLocation(lat, lng, 15);
        }

    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm =
                (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
