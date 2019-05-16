package com.maple.googlemap.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.ui.MainActivity
import com.maple.googlemap.utils.permission.RxPermissions
import com.maple.googlemap.view.SlidingUpPanelLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_sliding_up_panel.*
import java.util.*

/**
 * 多级悬浮滑动面板
 *
 * @author maple
 * @time 2019/3/6
 */
class SlidingUpPanelFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var mActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_sliding_up_panel, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        mActivity.title = "多级悬浮滑动面板"
        mActivity.setLeftBtnState("Back", View.VISIBLE, true)
        mActivity.setRightBtnState(View.GONE, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bt_clear.text = "隐藏面板"
        bt_compute.text = "启用锚点"
        initListView()
        bt_follow.setOnClickListener { Toast.makeText(mContext, "click custom button", Toast.LENGTH_SHORT).show() }
        sliding_layout.setFadeOnClickListener { sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED }
        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.e("onPanelSlide", "onPanelSlide, offset $slideOffset")
            }

            override fun onPanelStateChanged(panel: View, previousState: SlidingUpPanelLayout.PanelState, newState: SlidingUpPanelLayout.PanelState) {
                Log.e("onPanelStateChanged", "onPanelStateChanged $newState")
            }
        })

        bt_help.setOnClickListener { onClickHelp() }
        bt_clear.setOnClickListener { onClickClearAll() }
        bt_compute.setOnClickListener { onClickCompute() }
    }

    fun onClickHelp() {

    }

    fun onClickClearAll() {
        if (sliding_layout.panelState != SlidingUpPanelLayout.PanelState.HIDDEN) {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            bt_clear.text = "显示面板"
        } else {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            bt_clear.text = "隐藏面板"
        }
    }

    fun onClickCompute() {
        if (sliding_layout.anchorPoint == 1.0f) {
            sliding_layout.anchorPoint = 0.7f
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            bt_compute.text = "禁用锚点"
        } else {
            sliding_layout.anchorPoint = 1.0f
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            bt_compute.text = "启用锚点"
        }
    }

    override fun onKeyBackPressed(): Boolean {
        if (sliding_layout != null && (sliding_layout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED ||
                        sliding_layout.panelState == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return true
        } else {
            // 不消耗back事件，交由父类处理。
            return false
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        checkPermission()
    }

    private fun checkPermission() {
        RxPermissions(mActivity)
                .request(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .subscribe(object : Observer<Boolean> {

                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(aBoolean: Boolean) {
                        Log.e("on next", "return:  $aBoolean")
                    }

                    override fun onError(e: Throwable) {}

                    @SuppressLint("MissingPermission")
                    override fun onComplete() {
                        if (!mMap.isMyLocationEnabled)
                            mMap.isMyLocationEnabled = true
                        getMyLocation()
                    }
                })
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // 最新方法
        LocationServices.getFusedLocationProviderClient(mActivity)
                .lastLocation
                .addOnSuccessListener(mActivity) { location ->
                    if (location != null) {
                        moveToLatLng(location)
                    }
                }
                .addOnFailureListener(mActivity) { e ->
                    Toast.makeText(mActivity, "Fail:" + e.message, Toast.LENGTH_SHORT).show()
                }
    }

    private fun moveToLatLng(location: Location) {
        val userLocation = LatLng(location.latitude, location.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14f), 1500, null)
    }

    private fun initListView() {
        val arrayList = ArrayList<String>()
        for (i in 0..29) {
            arrayList.add("item $i")
        }
        lv_list.adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, arrayList)
        lv_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(mContext, "onItemClick$position", Toast.LENGTH_SHORT).show()
        }
    }

}
