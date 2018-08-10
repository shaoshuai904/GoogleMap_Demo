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

import butterknife.ButterKnife;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class CustomPolygonFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_maps, null);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity.setTitle("Custom Polygon");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);
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
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
}
