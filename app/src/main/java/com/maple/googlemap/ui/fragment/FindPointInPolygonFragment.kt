package com.maple.googlemap.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.ui.MainActivity
import com.maple.googlemap.utils.AreaFactory
import com.maple.googlemap.utils.SpatialRelationUtil

/**
 * 判断一个点，是否在不规则多边形内部。
 *
 * @author maple
 * @time 2019/2/22
 */
class FindPointInPolygonFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var mActivity: MainActivity
    private lateinit var marker: Marker

    // 获取基础区域
    private val baseArea = arrayListOf(
            AreaFactory.libiya,
            AreaFactory.niriliya,
            AreaFactory.zhongfei
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_base_map, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        mActivity.title = "点与多边形关系"
        mActivity.setLeftBtnState("Back", View.VISIBLE, true)
        mActivity.setRightBtnState(View.GONE, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val boundsBuilder = LatLngBounds.builder()
        // add base area
        for (area in baseArea) {
            for (latLng in area) {
                boundsBuilder.include(latLng)
            }
            mMap.addPolygon(PolygonOptions()
                    .fillColor(-0x55223211)
                    .strokeColor(-0x10000)
                    .strokeWidth(2f)
                    .addAll(area)
            )
        }

        marker = mMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(LatLng(1.0, 1.0))
                .draggable(true)
        )
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(boundsBuilder.build().center, 4f))
    }

    private fun onMarkerMoved(marker: Marker) {
        var isContains = false
        for (area in baseArea) {
            if (SpatialRelationUtil.isPolygonContainsPoint(area, marker.position)) {
                isContains = true
            }
        }
        if (isContains) {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        } else {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        }
    }

}
