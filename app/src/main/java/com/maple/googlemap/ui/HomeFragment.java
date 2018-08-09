package com.maple.googlemap.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseFragment;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class HomeFragment extends BaseFragment {

    MainActivity mActivity;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_home, null);
        // ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mActivity.setTitle("Home");
        mActivity.setLeftBtnState(View.GONE, false);
        mActivity.setRightBtnState(View.GONE, false);

    }

    @Override
    public void initListener() {

    }


}
