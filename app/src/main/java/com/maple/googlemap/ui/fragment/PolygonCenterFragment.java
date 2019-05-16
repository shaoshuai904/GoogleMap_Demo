package com.maple.googlemap.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.bean.CustomAreaBean;
import com.maple.googlemap.ui.MainActivity;
import com.maple.googlemap.utils.BitmapUtils;
import com.maple.googlemap.utils.SpatialRelationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 计算不规则多边形的心 - 中心/重心。
 *
 * @author maple
 * @time 2019/2/22
 */
public class PolygonCenterFragment extends BaseFragment implements OnMapReadyCallback {
    @BindView(R.id.bt_compute) Button bt_compute;
    @BindView(R.id.bt_clear) Button bt_clear;
    @BindView(R.id.bt_help) Button bt_help;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private CustomAreaBean curArea;
    MainActivity mActivity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_polygon_barycenter, container, false);
        ButterKnife.bind(this, view);
        view.setClickable(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        curArea.setMarkerVisible(false);
        // 计算bounds
        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
        for (LatLng ll : curArea.getPoints())
            boundsBuilder.include(ll);
        LatLngBounds bounds = boundsBuilder.build();
        // draw bounds
        mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(bounds.northeast.latitude, bounds.southwest.longitude))// 左上
                .add(bounds.northeast)// 右上
                .add(new LatLng(bounds.southwest.latitude, bounds.northeast.longitude))// 右下
                .add(bounds.southwest)// 左下
                .strokeColor(0xFFff0000)
                .strokeWidth(1f)
        );
        // 横线
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(bounds.getCenter().latitude, bounds.southwest.longitude))
                .add(new LatLng(bounds.getCenter().latitude, bounds.northeast.longitude))
                .color(0xFFff0000)
                .width(1f)

        );
        // 竖线
        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(bounds.northeast.latitude, bounds.getCenter().longitude))
                .add(new LatLng(bounds.southwest.latitude, bounds.getCenter().longitude))
                .color(0xFFff0000)
                .width(1f)

        );
        // 几何中心
        // LatLng center = SpatialRelationUtil.getCenterPoint(curArea.getPoints());
        LatLng center = bounds.getCenter();
        mMap.addMarker(new MarkerOptions()
                .draggable(false)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapUtils.getCustomTextBitmap(40, Color.RED, "中心"))
                )
                .position(center)
        );
        // 几何重心
        LatLng barycenter = SpatialRelationUtil.getCenterOfGravityPoint(curArea.getPoints());
        mMap.addMarker(new MarkerOptions()
                .draggable(false)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapUtils.getCustomTextBitmap(40, Color.BLUE, "重心"))
                )
                .position(barycenter)
        );
    }

    private void updateState(CustomAreaBean curArea) {
        bt_clear.setEnabled(!curArea.isNull());
        bt_compute.setEnabled(curArea.isFormingArea());
    }

}
