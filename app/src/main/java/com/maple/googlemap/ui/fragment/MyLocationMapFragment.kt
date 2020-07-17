package com.maple.googlemap.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.ui.MainActivity
import com.maple.googlemap.utils.permission.RxPermissions
import com.maple.msdialog.AlertDialog

/**
 * 通过Google地图获取我的位置
 *
 * @author maple
 * @time 2018/8/8.
 */
class MyLocationMapFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var mActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_base_map, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity
        mActivity.updateTitle("My Location")
        mActivity.setLeftBtnState(View.VISIBLE)

        mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //        mActivity.setLeftBtnClickListener(this);
        //        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        //        mFusedLocationClient.requestLocationUpdates(new LocationRequest(),
        //                new LocationCallback() {
        //                    @Override
        //                    public void onLocationResult(LocationResult locationResult) {
        //                        if (locationResult != null) {
        //                            for (Location location : locationResult.getLocations()) {
        //                                // Update UI with location data
        //                                Log.e("result", "  " + location.toString());
        //                                // ...
        //                            }
        //                        }
        //                    }
        //                },
        //                null /* Looper */);

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    @SuppressLint("CheckResult", "MissingPermission")
    private fun checkPermission() {
        RxPermissions(this).request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe { granted ->
            if (granted) {
                if (!mMap.isMyLocationEnabled)
                    mMap.isMyLocationEnabled = true
                getMyLocation()
            } else {
                AlertDialog(mContext).apply {
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                    setTitle("权限不足！")
                    setMessage("获取地理位置必须要有“ACCESS_FINE_LOCATION”和“ACCESS_COARSE_LOCATION”权限哦。")
                    setLeftButton("退出")
                    setRightButton("再选一次", View.OnClickListener { checkPermission() })
                }.show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // 最新方法
        LocationServices.getFusedLocationProviderClient(mActivity)
                .lastLocation
                .addOnSuccessListener(mActivity) { location ->
                    if (location != null) {
                        moveToLatLng(location)
                    }
                }
                .addOnFailureListener(mActivity) { e ->
                    // fail
                    Toast.makeText(mActivity, "Fail:" + e.message, Toast.LENGTH_SHORT).show()
                }

        // System Service
        //        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //        if (myLocation == null) {
        //            Criteria criteria = new Criteria();
        //            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //            String provider = lm.getBestProvider(criteria, true);
        //            myLocation = lm.getLastKnownLocation(provider);
        //        }
        //        if (myLocation != null) {
        //            moveToLatLng(myLocation);
        //        }
        // 过时
        //        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
        //            @Override
        //            public void onMyLocationChange(Location location) {
        //                moveToLatLng(location);
        //            }
        //        });
    }

    private fun moveToLatLng(location: Location) {
        val userLocation = LatLng(location.latitude, location.longitude)
        Log.e("my_location", "get my location:  $userLocation")
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14f), 1500, null)
    }

}
