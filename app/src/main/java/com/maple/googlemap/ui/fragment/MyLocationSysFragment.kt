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
        RxPermissions(this).request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe { granted ->
            if (granted) {
                val lm: LocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var myLocation: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (myLocation == null) {
                    tv_info.text = "GPS 获取位置失败，尝试网络获取..."
                    myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
                getLocationCity(myLocation)
            } else {
                AlertDialog(mContext).apply {
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
    private fun getLocationCity(location: Location?) {
        if (location == null) {
            tv_info.text = "location is null !"
            return
        }
        val sb = StringBuilder()
        sb.append("经纬度： ${location.latitude}    ${location.longitude} \n")
        Observable.just(location)
                .map {
                    val gc = Geocoder(mActivity, Locale.getDefault())
                    gc.getFromLocation(location.latitude, location.longitude, 1)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.forEach { item ->
                        sb.append("locale ： ${item.locale} \n" +
                                "countryName ： ${item.countryName} \n" +
                                "countryCode ： ${item.countryCode} \n" +
                                "locality ： ${item.locality} \n" +
                                "subLocality ： ${item.subLocality} \n" +
                                "thoroughfare ： ${item.thoroughfare} \n" +
                                "subThoroughfare ： ${item.subThoroughfare} \n" +
                                "featureName ： ${item.featureName} \n" +
                                "item info ： ${item.toString()} \n")
                    }
                    tv_info.text = sb.toString()
                }
    }

}
