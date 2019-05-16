package com.maple.googlemap.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绘制自定义Polygon
 *
 * @author maple
 * @time 2018/8/8.
 */
public class CustomPolygonFragment extends BaseFragment implements OnMapReadyCallback {
    @BindView(R.id.tv_add) TextView tv_add;
    @BindView(R.id.tv_delete) TextView tv_delete;
    @BindView(R.id.tv_clear_all) TextView tv_clear_all;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;
    private List<CustomAreaBean> allArea;
    private CustomAreaBean curArea;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_polygon, container, false);
        ButterKnife.bind(this, view);
        view.setClickable(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle("Custom Polygons");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);

        allArea = new ArrayList<>();
        curArea = new CustomAreaBean("custom_area_number_0");
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
        CustomAreaBean superArea = curArea;
        if (!curArea.isNull() && curArea.isContainMarker(marker)) {
            superArea = curArea;
        } else {
            for (CustomAreaBean area : allArea) {
                if (area.isContainMarker(marker)) {
                    superArea = area;
                }
            }
        }
        superArea.updateMarker(marker);
    }

    @OnClick(R.id.tv_clear_all)
    void onClickClearAll() {
//        for (CustomAreaBean areaBean : allArea) {
//            Log.e("[ points ]", "   " + areaBean.toString());
//        }
        mMap.clear();
        curArea.clear();
        allArea.clear();
    }

    @OnClick(R.id.tv_delete)
    void onClickDelete() {
        if (!curArea.isNull())
            curArea.clear();
        if (allArea.size() >= 1) {
            curArea = allArea.get(allArea.size() - 1);
            allArea.remove(curArea);
        }
        curArea.setSelState(true);
        updateState(curArea);
    }

    @OnClick(R.id.tv_add)
    void onClickAdd() {
        if (curArea.getMarkers() == null || curArea.getMarkers().size() == 0) {
            Toast.makeText(getMContext(), "没有东西，你让我添加什么？", Toast.LENGTH_SHORT).show();
        } else {
            curArea.setSelState(false);
            allArea.add(curArea);
            curArea = new CustomAreaBean("custom_area_number_" + allArea.size());
            curArea.setSelState(true);
        }
    }

    private void updateState(CustomAreaBean curArea) {
        tv_delete.setEnabled(allArea.size() > 0 || !curArea.isNull());
        tv_clear_all.setEnabled(allArea.size() > 0 || !curArea.isNull());
        tv_add.setEnabled(curArea.isFormingArea());
    }

}
