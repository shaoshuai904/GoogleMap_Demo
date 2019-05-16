package com.maple.googlemap.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * APP管理类
 *
 * @author shaoshuai
 */
object AppUtils {

    /**
     * APP版本名
     */
    @JvmStatic
    fun getAppVersionString(context: Context): String {
        val packageInfo = getPackageInfo(context)
        var verName = "1.0"
        var verCode = 1L
        if (packageInfo != null) {
            verName = packageInfo.versionName
            verCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        }
        return "Version $verName - (Build $verCode)"
    }

    /**
     * 获取APP包信息
     */
    private fun getPackageInfo(context: Context): PackageInfo? {
        val pm = context.packageManager
        return pm.getPackageInfo(context.packageName, PackageManager.GET_CONFIGURATIONS)
    }

}