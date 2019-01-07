package com.maple.googlemap.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * @author maple
 * @time 2018/12/17
 */
public abstract class BaseFragment extends Fragment {
    public View view;
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutRes(), container, false);
        view.setClickable(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    public boolean onKeyBackPressed() {
        // 是否消耗掉back事件
        return false;
    }

    public abstract int getLayoutRes();

    public abstract void initData(Bundle savedInstanceState);

}
