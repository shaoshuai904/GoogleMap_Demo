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

}
