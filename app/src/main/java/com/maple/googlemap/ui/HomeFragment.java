package com.maple.googlemap.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.ui.fragment.CustomPolygonFragment;
import com.maple.googlemap.ui.fragment.FindPointInPolygonFragment;
import com.maple.googlemap.ui.fragment.MyLocationFragment;
import com.maple.googlemap.ui.fragment.PolygonBarycenterFragment;
import com.maple.googlemap.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_about) TextView tv_about;

    MainActivity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        mActivity.setTitle("Google Map Demo");
        mActivity.setLeftBtnState(View.GONE, false);
        mActivity.setRightBtnState(View.GONE, false);

        tv_about.setText(AppUtils.getAppVersionString(getContext()));
    }

    @OnClick(R.id.bt_my_location)
    public void onMyLocation() {
        mActivity.replaceView(new MyLocationFragment());
    }

    @OnClick(R.id.bt_draw_polygons)
    public void onDrawPolygon() {
        mActivity.replaceView(new CustomPolygonFragment());
    }

    @OnClick(R.id.bt_polygon_barycenter)
    public void onPolygonBarycenter() {
        mActivity.replaceView(new PolygonBarycenterFragment());
    }

    @OnClick(R.id.bt_find_point_in_polygon)
    public void onFindPointInPolygon() {
        mActivity.replaceView(new FindPointInPolygonFragment());
    }

}
