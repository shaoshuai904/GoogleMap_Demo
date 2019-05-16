package com.maple.googlemap.base

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * @author maple
 * @time 2018/10/11
 */
abstract class BaseFragment : Fragment() {
    lateinit var mContext: Context
//    val mProgressDialog: ProgressDialog by lazy {
//        LoadingDialog().getDefault(context)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    open fun onKeyBackPressed(): Boolean {
        // 是否消耗掉back事件
        return false
    }

//    open fun showProgressDialog(@StringRes resString: Int) {
//        showProgressDialog(getString(resString))
//    }
//
//    open fun showProgressDialog(message: CharSequence) {
//        dismissDialog()
//        mProgressDialog.setMessage(message)
//        mProgressDialog.show()
//    }
//
//    open fun dismissDialog() {
//        if (mProgressDialog.isShowing) {
//            mProgressDialog.cancel()
//        }
//    }

}

