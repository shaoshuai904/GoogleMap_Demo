package com.maple.googlemap.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseTopBarActivity
import com.maple.googlemap.utils.permission.RxPermissions

/**
 * @author maple
 * @time 2018/8/8.
 */
class MainActivity : BaseTopBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView(R.layout.fragment_base)

        addView(HomeFragment())
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation() {
        RxPermissions(this)
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .subscribe()
        // 最新方法
        LocationServices.getFusedLocationProviderClient(mContext)
                .lastLocation
                .addOnSuccessListener(mContext as Activity) { location ->
                    if (location != null) {
                        Toast.makeText(mContext, "success: " + location.latitude + " - " + location.longitude, Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener(mContext as Activity) { e ->
                    // fail
                    Toast.makeText(mContext, "Fail:" + e.message, Toast.LENGTH_SHORT).show()
                }
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
