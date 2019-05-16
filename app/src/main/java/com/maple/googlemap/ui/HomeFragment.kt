package com.maple.googlemap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maple.googlemap.R
import com.maple.googlemap.base.BaseFragment
import com.maple.googlemap.ui.fragment.*
import com.maple.googlemap.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author maple
 * @time 2018/8/8.
 */
class HomeFragment : BaseFragment() {
    private lateinit var mActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.isClickable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as MainActivity

        mActivity.title = "Google Map Demo"
        mActivity.setLeftBtnState(View.GONE, false)
        mActivity.setRightBtnState(View.GONE, false)

        tv_about.text = AppUtils.getAppVersionString(mContext)

        initListener()
    }

    private fun initListener() {
        bt_my_location.setOnClickListener {
            mActivity.replaceView(MyLocationFragment())
        }
        bt_draw_polygons.setOnClickListener {
            mActivity.replaceView(CustomPolygonFragment())
        }
        bt_polygon_barycenter.setOnClickListener {
            mActivity.replaceView(PolygonCenterFragment())
        }
        bt_find_point_in_polygon.setOnClickListener {
            mActivity.replaceView(FindPointInPolygonFragment())
        }
        bt_sliding_up_panel.setOnClickListener {
            mActivity.replaceView(SlidingUpPanelFragment())
        }
    }

}
