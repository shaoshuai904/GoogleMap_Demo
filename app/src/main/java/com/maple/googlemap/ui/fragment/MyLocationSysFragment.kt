package com.maple.googlemap.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.ui.MainActivity
import com.maple.googlemap.utils.permission.RxPermissions
import com.maple.msdialog.AlertDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my_location.*
import java.util.*

/**
 * 通过系统API获取我的位置
 *
 * @author maple
 * @time 2020/7/16.
 */
class MyLocationSysFragment : BaseFragment() {
    private lateinit var mActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_my_location, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity
        mActivity.updateTitle("My Location")
        mActivity.setLeftBtnState(View.VISIBLE)

        getMyLocation()
    }

    @SuppressLint("CheckResult", "MissingPermission")
    private fun getMyLocation() {
        RxPermissions(mActivity).request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe { granted ->
            if (granted) {
                val lm: LocationManager = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val myLocation: Location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                getLocationCity(myLocation)
            } else {
                AlertDialog(mActivity).apply {
                    setCancelable(false)
                    setCanceledOnTouchOutside(false)
                    setTitle("权限不足！")
                    setMessage("获取地理位置必须要有“ACCESS_FINE_LOCATION”和“ACCESS_COARSE_LOCATION”权限哦。")
                    setLeftButton("退出")
                    setRightButton("再选一次", View.OnClickListener { getMyLocation() })
                }.show()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getLocationCity(location: Location) {
        val sb = StringBuilder()
        sb.append("经纬度： ${location.latitude}   ${location.longitude} \n")
        Observable.just(location)
                .map {
                    val gc = Geocoder(mActivity, Locale.getDefault())
                    gc.getFromLocation(location.latitude, location.longitude, 1)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it.forEach { item ->
                        sb.append("Address ： ${item.toString()} \n")
                    }
                    tv_info.text = sb.toString()
                }
    }

}
