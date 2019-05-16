package com.maple.googlemap.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.bean.CustomAreaBean
import com.maple.googlemap.ui.MainActivity
import com.maple.googlemap.utils.BitmapUtils
import com.maple.googlemap.utils.SpatialRelationUtil
import kotlinx.android.synthetic.main.fragment_polygon_barycenter.*

/**
 * 计算不规则多边形的心 - 中心/重心。
 *
 * @author maple
 * @time 2019/2/22
 */
class PolygonCenterFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var curArea: CustomAreaBean
    private lateinit var mActivity: MainActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_polygon_barycenter, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity
        mActivity.updateTitle("计算重心")
        mActivity.setLeftBtnState(View.VISIBLE)

        curArea = CustomAreaBean("custom_area")
        curArea.setSelState(true)
        updateState(curArea)

        mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bt_help.setOnClickListener { onClickHelp() }
        bt_clear.setOnClickListener { onClickClearAll() }
        bt_compute.setOnClickListener { onClickCompute() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Map 点击监听
        mMap.setOnMapClickListener { latLng ->
            curArea.addMarker(mMap.addMarker(MarkerOptions()
                    .position(latLng)
                    .draggable(true))
            )
            if (curArea.polygon == null) {
                curArea.polygon = mMap.addPolygon(PolygonOptions()
                        .addAll(curArea.points)
                        .fillColor(-0x55223211)
                        .strokeColor(-0x10000)
                        .strokeWidth(4f))
            } else {
                curArea.polygon.points = curArea.points
            }
            updateState(curArea)
        }
        // 标记拖拽监听
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {
                onMarkerMoved(marker)
            }

            override fun onMarkerDrag(marker: Marker) {
                onMarkerMoved(marker)
            }

            override fun onMarkerDragEnd(marker: Marker) {
                onMarkerMoved(marker)
            }
        })
    }

    private fun onMarkerMoved(marker: Marker) {
        curArea.updateMarker(marker)
    }

    fun onClickHelp() {

    }

    fun onClickClearAll() {
        mMap.clear()
        curArea.clear()
        updateState(curArea)
    }

    fun onClickCompute() {
        curArea.setMarkerVisible(false)
        // 计算bounds
        val boundsBuilder = LatLngBounds.builder()
        for (ll in curArea.points)
            boundsBuilder.include(ll)
        val bounds = boundsBuilder.build()
        // draw bounds
        mMap.addPolygon(PolygonOptions()
                .add(LatLng(bounds.northeast.latitude, bounds.southwest.longitude))// 左上
                .add(bounds.northeast)// 右上
                .add(LatLng(bounds.southwest.latitude, bounds.northeast.longitude))// 右下
                .add(bounds.southwest)// 左下
                .strokeColor(-0x10000)
                .strokeWidth(1f))
        // 横线
        mMap.addPolyline(PolylineOptions()
                .add(LatLng(bounds.center.latitude, bounds.southwest.longitude))
                .add(LatLng(bounds.center.latitude, bounds.northeast.longitude))
                .color(-0x10000)
                .width(1f))
        // 竖线
        mMap.addPolyline(PolylineOptions()
                .add(LatLng(bounds.northeast.latitude, bounds.center.longitude))
                .add(LatLng(bounds.southwest.latitude, bounds.center.longitude))
                .color(-0x10000)
                .width(1f))
        // 几何中心
        // LatLng center = SpatialRelationUtil.getCenterPoint(curArea.getPoints());
        val center = bounds.center
        mMap.addMarker(MarkerOptions()
                .draggable(false)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapUtils.getCustomTextBitmap(40, Color.RED, "中心"))
                )
                .position(center))
        // 几何重心
        val barycenter = SpatialRelationUtil.getCenterOfGravityPoint(curArea!!.points)
        mMap.addMarker(MarkerOptions()
                .draggable(false)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapUtils.getCustomTextBitmap(40, Color.BLUE, "重心"))
                )
                .position(barycenter))
    }

    private fun updateState(curArea: CustomAreaBean) {
        bt_clear.isEnabled = !curArea.isNull
        bt_compute.isEnabled = curArea.isFormingArea
    }

}
