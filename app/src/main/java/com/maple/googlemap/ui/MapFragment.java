package com.maple.googlemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_maps, null);
        // ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
