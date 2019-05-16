package com.maple.googlemap.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.bean.CustomAreaBean
import com.maple.googlemap.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_custom_polygon.*
import java.util.*

/**
 * 绘制自定义Polygon
 *
 * @author maple
 * @time 2018/8/8.
 */
class CustomPolygonFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var mActivity: MainActivity

    private var allArea: MutableList<CustomAreaBean> = ArrayList()
    private lateinit var curArea: CustomAreaBean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_custom_polygon, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        mActivity.title = "Custom Polygons"
        mActivity.setLeftBtnState("Back", View.VISIBLE, true)
        mActivity.setRightBtnState(View.GONE, false)

        curArea = CustomAreaBean("custom_area_number_0")
        curArea.setSelState(true)
        updateState(curArea)

        mapFragment = childFragmentManager.findFragmentById(R.id.fm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        tv_clear_all.setOnClickListener { onClickClearAll() }
        tv_delete.setOnClickListener { onClickDelete() }
        tv_add.setOnClickListener { onClickAdd() }
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
        var superArea = curArea
        if (!curArea.isNull && curArea.isContainMarker(marker)) {
            superArea = curArea
        } else {
            for (area in allArea) {
                if (area.isContainMarker(marker)) {
                    superArea = area
                }
            }
        }
        superArea.updateMarker(marker)
    }

    fun onClickClearAll() {
        mMap.clear()
        curArea.clear()
        allArea.clear()
    }

    fun onClickDelete() {
        if (!curArea.isNull)
            curArea.clear()
        if (allArea.size >= 1) {
            curArea = allArea[allArea.size - 1]
            allArea.remove(curArea)
        }
        curArea.setSelState(true)
        updateState(curArea)
    }

    fun onClickAdd() {
        if (curArea.markers == null || curArea.markers.size == 0) {
            Toast.makeText(mContext, "没有东西，你让我添加什么？", Toast.LENGTH_SHORT).show()
        } else {
            curArea.setSelState(false)
            allArea.add(curArea)
            curArea = CustomAreaBean("custom_area_number_" + allArea.size)
            curArea.setSelState(true)
        }
    }

    private fun updateState(curArea: CustomAreaBean) {
        tv_delete.isEnabled = allArea.size > 0 || !curArea.isNull
        tv_clear_all.isEnabled = allArea.size > 0 || !curArea.isNull
        tv_add.isEnabled = curArea.isFormingArea
    }

}
