package com.maple.googlemap.ui

import android.os.Bundle
import android.view.View
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pager_top_bar.*


/**
 * @author maple
 * @time 2018/8/8.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_fragment)

        addView(HomeFragment())

        tv_left.setOnClickListener { onBackPressed() }
        tv_right.visibility = View.GONE
    }

    fun updateTitle(title: CharSequence) {
        tv_title.text = title
    }

    fun setLeftBtnState(visibility: Int = View.VISIBLE, isEnabled: Boolean = true) {
        tv_left.text = "Back"
        tv_left.visibility = visibility
        tv_left.isEnabled = isEnabled
    }

//    @SuppressLint("MissingPermission")
//    fun getMyLocation() {
//        RxPermissions(this)
//                .request(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//                .subscribe()
//        // 最新方法
//        LocationServices.getFusedLocationProviderClient(mContext)
//                .lastLocation
//                .addOnSuccessListener(mContext as Activity) { location ->
//                    if (location != null) {
//                        Toast.makeText(mContext, "success: " + location.latitude + " - " + location.longitude, Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .addOnFailureListener(mContext as Activity) { e ->
//                    // fail
//                    Toast.makeText(mContext, "Fail:" + e.message, Toast.LENGTH_SHORT).show()
//                }
//    }

}
