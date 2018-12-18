package com.maple.googlemap.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.utils.permission.PermissionFragment;
import com.maple.googlemap.utils.permission.PermissionListener;

import butterknife.ButterKnife;

/**
 * 获取我的位置
 *
 * @author maple
 * @time 2018/8/8.
 */
public class MyLocationFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_base_map, null);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity.setTitle("My Location");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);

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

    @Override
    public void initListener() {
        mActivity.setLeftBtnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);

//            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            if (myLocation == null) {
//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//                String provider = lm.getBestProvider(criteria, true);
//                myLocation = lm.getLastKnownLocation(provider);
//            }
//
//            if (myLocation != null) {
//                moveToLatLng(myLocation);
//            }
            // 最新方法
            LocationServices.getFusedLocationProviderClient(mActivity)
                    .getLastLocation()
                    .addOnSuccessListener(mActivity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                moveToLatLng(location);
                            }
                        }
                    })
                    .addOnFailureListener(mActivity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mActivity, "Fail:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        // 过时
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//            @Override
//            public void onMyLocationChange(Location location) {
//                moveToLatLng(location);
//            }
//        });

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void moveToLatLng(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_title:
                mActivity.onBack();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        PermissionFragment.getPermissionFragment(getActivity())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {

                    }
                })
                .checkPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
    }


}
