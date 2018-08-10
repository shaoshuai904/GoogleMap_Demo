package com.maple.googlemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;
import com.maple.googlemap.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.bt_draw_polygon) Button bt_draw_polygon;
    @BindView(R.id.bt_my_location) Button bt_my_location;
    @BindView(R.id.tv_about) TextView tv_about;

    MainActivity mActivity;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);

        mActivity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity.setTitle("Home");
        mActivity.setLeftBtnState(View.GONE, false);
        mActivity.setRightBtnState(View.GONE, false);

        tv_about.setText(AppUtils.getAppVersionString(getContext()));
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_draw_polygon)
    public void onDrawPolygon() {
        mActivity.replaceView(new CustomPolygonFragment());
    }

    @OnClick(R.id.bt_my_location)
    public void onMyLocation() {
        mActivity.replaceView(new MyLocationFragment());
    }

}
