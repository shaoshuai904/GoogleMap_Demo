package com.maple.googlemap.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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
import com.maple.googlemap.utils.permission.RxPermissions;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 获取我的位置
 *
 * @author maple
 * @time 2018/8/8.
 */
public class MyLocationFragment extends BaseFragment implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_base_map;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, view);

        mActivity.setTitle("My Location");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);

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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);
            getMyLocation();
        }

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
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

    private void moveToLatLng(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
    }

    private void checkPermission() {
        new RxPermissions(mActivity)
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
