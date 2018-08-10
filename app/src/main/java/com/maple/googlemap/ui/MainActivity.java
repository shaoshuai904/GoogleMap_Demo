package com.maple.googlemap.ui;

import android.os.Bundle;
import android.widget.Toast;

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

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
//        new AlertDialog(this)
//                .setTitle("Do you want to exit?")
//                .setNegativeButton("Cancel", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                })
//                .setPositiveButton("Exit", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                })
//                .show();
//    }

}
