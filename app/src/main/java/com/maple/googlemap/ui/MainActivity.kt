package com.maple.googlemap.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.location.LocationServices
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseTopBarActivity

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
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun getMyLocation() {
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
