package com.maple.googlemap.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
    public int getLayoutRes() {
        return R.layout.fragment_custom_polygon;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle("Custom Polygon");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);

        tv_clear.setOnClickListener(this);
    }

    PolygonOptions options;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (options == null) {
                    options = new PolygonOptions()
                            .strokeColor(Color.RED)
                            .strokeWidth(2.0f);
                }
                options.add(latLng);
                mMap.clear();
                mMap.addPolygon(options);
                for (LatLng lng : options.getPoints()) {
                    mMap.addMarker(new MarkerOptions()
                            .draggable(true)
                            .position(lng));
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                marker.setPosition(marker.getPosition());
//                marker.setPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
//                marker.setPosition();
            }
        });
    }

    @Override
    public boolean onKeyBackPressed() {
        // 具体操作
        if (1 == 1) {
            Toast.makeText(mContext, "back Fragment", Toast.LENGTH_SHORT).show();
            mActivity.backFragment();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                options = null;
                mMap.clear();
                break;
            default:
                break;
        }
    }
}
