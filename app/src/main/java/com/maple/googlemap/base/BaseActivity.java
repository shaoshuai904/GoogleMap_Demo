package com.maple.googlemap.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maple.googlemap.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


/**
 * @author maple
 * @time 16/4/13
 */
public class BaseActivity extends FragmentActivity {
    public RelativeLayout rl_top_bar;
    public TextView tv_left_title;
    public TextView tv_title;
    public TextView tv_right_title;
    public LinearLayout ll_root;

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 保持竖屏

        setContentView(R.layout.activity_base_fragment);
        mContext = this;

        findView();
    }


    private void findView() {
        rl_top_bar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);

        tv_left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClick();
            }
        });
    }

    public void setBaseContentView(int layoutID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutID, ll_root);
    }

    public void isShowTopBar(boolean isShow) {
        if (isShow) {
            rl_top_bar.setVisibility(View.VISIBLE);
        } else {
            rl_top_bar.setVisibility(View.GONE);
        }
    }

    // ------------------ fragment ------------------

    public void addView(Fragment fgView) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, fgView).commit();
    }

    public void replaceView(Fragment fgView) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fgView);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void backFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (fragment instanceof BaseFragment) {
            if (!((BaseFragment) fragment).onKeyBackPressed()) {
                backFragment();
            }
        } else {
            super.onBackPressed();
        }
    }

    // ----------- left right button ------------------

    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public void setLeftBtnState(String str, int visibility, boolean isEnabled) {
        tv_left_title.setText(str);
        setLeftBtnState(visibility, isEnabled);
    }

    public void setLeftBtnState(int visibility, boolean isEnabled) {
        tv_left_title.setVisibility(visibility);
        tv_left_title.setEnabled(isEnabled);
    }

    public void setRightBtnState(String str, int visibility, boolean isEnabled) {
        tv_right_title.setText(str);
        setRightBtnState(visibility, isEnabled);
    }

    public void setRightBtnState(int visibility, boolean isEnabled) {
        tv_right_title.setVisibility(visibility);
        tv_right_title.setEnabled(isEnabled);
    }

    public void onRightClick() {

    }
}
