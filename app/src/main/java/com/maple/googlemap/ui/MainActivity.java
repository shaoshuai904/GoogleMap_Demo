package com.maple.googlemap.ui;

import android.os.Bundle;

import com.maple.googlemap.R;
import com.maple.googlemap.base.BaseActivity;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.fragment_base);
        initView();
    }

    private void initView() {
        addView(new HomeFragment());
    }

}
