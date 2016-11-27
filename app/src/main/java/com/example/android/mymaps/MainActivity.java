package com.example.android.mymaps;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
    GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(servicesOK()){
            setContentView(R.layout.activity_map);

            if(initMap()){
                Toast.makeText(this, "Ready to map!",Toast.LENGTH_SHORT).show();
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
}
