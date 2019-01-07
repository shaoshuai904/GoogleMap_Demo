package com.maple.googlemap.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.fragment_base);
        initView();
    }

    private void initView() {
        addView(new HomeFragment());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getMyLocation() {
        // 最新方法
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(mContext)
                .getLastLocation()
                .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Toast.makeText(mContext, "success: " + location.getLatitude() + " - " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener((Activity) mContext, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Fail:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
//        new AlertDialog(this)
//                .setTitle("Do you want to exit?")
//                .setNegativeButton("Cancel", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                })
//                .setPositiveButton("Exit", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                })
//                .show();
//    }

}
