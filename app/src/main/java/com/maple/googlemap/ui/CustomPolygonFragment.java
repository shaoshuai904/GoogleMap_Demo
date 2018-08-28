package com.maple.googlemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义Polygon
 *
 * @author maple
 * @time 2018/8/8.
 */
public class CustomPolygonFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {
    @BindView(R.id.tv_clear) TextView tv_clear;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_custom_polygon, null);
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
        tv_clear.setOnClickListener(this);
    }


    PolygonOptions polygonOptions;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        polygonOptions = new PolygonOptions();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                polygonOptions.add(latLng);

                mMap.clear();

                mMap.addPolygon(polygonOptions);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left_title:
                mActivity.onBack();
                break;
            case R.id.tv_clear:
                mMap.clear();
                break;
            default:
                break;
        }
    }
}
