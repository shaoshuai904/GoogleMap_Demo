package com.maple.googlemap.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.ui.MainActivity;
import com.maple.googlemap.utils.AreaFactory;
import com.maple.googlemap.utils.SpatialRelationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 判断一个点，是否在不规则多边形内部。
 *
 * @author maple
 * @time 2019/2/22
 */
public class FindPointInPolygonFragment extends BaseFragment implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;
    Marker marker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_map, container, false);
        ButterKnife.bind(this, view);
        view.setClickable(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle("点与多边形关系");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
        // add base area
        for (List<LatLng> area : getBaseArea()) {
            for (LatLng latLng : area) {
                boundsBuilder.include(latLng);
            }
            mMap.addPolygon(new PolygonOptions()
                    .fillColor(0xAAddcdef)
                    .strokeColor(0xFFff0000)
                    .strokeWidth(2f)
                    .addAll(area)
            );
        }

        marker = mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(new LatLng(1, 1))
                .draggable(true)
        );
        mMap.setOnMarkerDragListener(markerDragListener);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(boundsBuilder.build().getCenter(), 4));
    }

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
        boolean isContains = false;
        for (List<LatLng> area : getBaseArea()) {
            if (SpatialRelationUtil.isPolygonContainsPoint(area, marker.getPosition())) {
                isContains = true;
            }
        }
        if (isContains) {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        } else {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
    }

    // 获取基础区域
    private List<List<LatLng>> getBaseArea() {
        List<List<LatLng>> baseAreas = new ArrayList<>();
        baseAreas.add(AreaFactory.getLibiya());
        baseAreas.add(AreaFactory.getNiriliya());
        baseAreas.add(AreaFactory.getZhongfei());
        return baseAreas;
    }

}
