package com.maple.googlemap.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.bean.CustomAreaBean;
import com.maple.googlemap.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 计算不规则多边形-重心。
 *
 * @author maple
 * @time 2019/2/22
 */
public class PolygonBarycenterFragment extends BaseFragment implements OnMapReadyCallback {
    @BindView(R.id.bt_compute) Button bt_compute;
    @BindView(R.id.bt_clear) Button bt_clear;
    @BindView(R.id.bt_help) Button bt_help;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private CustomAreaBean curArea;
    MainActivity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_polygon_barycenter;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle("计算重心");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);


        curArea = new CustomAreaBean("custom_area");
        curArea.setSelState(true);
        updateState(curArea);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(mapClickListener);
        mMap.setOnMarkerDragListener(markerDragListener);
    }

    // Map 点击监听
    GoogleMap.OnMapClickListener mapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            curArea.addMarker(mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .draggable(true))
            );
            if (curArea.getPolygon() == null) {
                curArea.setPolygon(mMap.addPolygon(new PolygonOptions()
                        .addAll(curArea.getPoints())
                        .fillColor(0xAAddcdef)
                        .strokeColor(0xFFff0000)
                        .strokeWidth(4f))
                );
            } else {
                curArea.getPolygon().setPoints(curArea.getPoints());
            }
            updateState(curArea);
        }
    };

    // 标记拖拽监听
    GoogleMap.OnMarkerDragListener markerDragListener = new GoogleMap.OnMarkerDragListener() {
        @Override
        public void onMarkerDragStart(Marker marker) {
            onMarkerMoved(marker);
        }

        @Override
        public void onMarkerDrag(Marker marker) {
            onMarkerMoved(marker);
        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            onMarkerMoved(marker);
        }
    };

    private void onMarkerMoved(Marker marker) {
        curArea.updateMarker(marker);
    }

    @OnClick(R.id.bt_help)
    void onClickHelp() {

    }

    @OnClick(R.id.bt_clear)
    void onClickClearAll() {
        mMap.clear();
        curArea.clear();
        updateState(curArea);
    }

    @OnClick(R.id.bt_compute)
    void onClickCompute() {

    }

    private void updateState(CustomAreaBean curArea) {
        bt_clear.setEnabled(!curArea.isNull());
        bt_compute.setEnabled(curArea.isFormingArea());
    }

}
