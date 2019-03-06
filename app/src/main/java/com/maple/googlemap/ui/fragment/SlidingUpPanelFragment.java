package com.maple.googlemap.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.ui.MainActivity;
import com.maple.googlemap.utils.permission.RxPermissions;
import com.maple.googlemap.view.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 多级悬浮滑动面板
 *
 * @author maple
 * @time 2019/3/6
 */
public class SlidingUpPanelFragment extends BaseFragment implements OnMapReadyCallback {
    @BindView(R.id.sliding_layout) SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.bt_compute) Button bt_compute;
    @BindView(R.id.bt_clear) Button bt_clear;
    @BindView(R.id.bt_help) Button bt_help;
    @BindView(R.id.ll_dragView) LinearLayout ll_dragView;
    @BindView(R.id.bt_follow) Button bt_follow;
    @BindView(R.id.lv_list) ListView lv_list;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    MainActivity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_sliding_up_panel;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        ButterKnife.bind(this, view);

        mActivity.setTitle("多级悬浮滑动面板");
        mActivity.setLeftBtnState("Back", View.VISIBLE, true);
        mActivity.setRightBtnState(View.GONE, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fm_map);
        mapFragment.getMapAsync(this);


        bt_clear.setText("隐藏面板");
        bt_compute.setText("启用锚点");
        initListView();
        sliding_layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e("onPanelSlide", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.e("onPanelStateChanged", "onPanelStateChanged " + newState);
            }
        });
        sliding_layout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    @OnClick(R.id.bt_follow)
    void onClickButton() {
        Toast.makeText(mContext, "click custom button", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_help)
    void onClickHelp() {

    }

    @OnClick(R.id.bt_clear)
    void onClickClearAll() {
        if (sliding_layout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            bt_clear.setText("显示面板");
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            bt_clear.setText("隐藏面板");
        }
    }

    @OnClick(R.id.bt_compute)
    void onClickCompute() {
        if (sliding_layout.getAnchorPoint() == 1.0f) {
            sliding_layout.setAnchorPoint(0.7f);
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
            bt_compute.setText("禁用锚点");
        } else {
            sliding_layout.setAnchorPoint(1.0f);
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            bt_compute.setText("启用锚点");
        }
    }

    @Override
    public boolean onKeyBackPressed() {
        if (sliding_layout != null && (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        } else {
            // 不消耗back事件，交由父类处理。
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkPermission();
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
                        Log.e("on next", "return:  " + aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onComplete() {
                        if (!mMap.isMyLocationEnabled())
                            mMap.setMyLocationEnabled(true);
                        getMyLocation();
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
        // 最新方法
        LocationServices.getFusedLocationProviderClient(mActivity)
                .getLastLocation()
                .addOnSuccessListener(mActivity, location -> {
                    if (location != null) {
                        moveToLatLng(location);
                    }
                })
                .addOnFailureListener(mActivity, e -> {
                    // fail
                    Toast.makeText(mActivity, "Fail:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void moveToLatLng(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
    }


    private void initListView() {
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            arrayList.add("item " + i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_list_item_1,
                arrayList);
        lv_list.setAdapter(arrayAdapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
