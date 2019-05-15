package com.maple.googlemap.utils

import android.annotation.TargetApi
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
        var verCode = 1
        if (packageInfo != null) {
            verName = packageInfo.versionName
            verCode = packageInfo.versionCode
        }
        return "Version $verName - (Build $verCode)"
    }

    /**
     * APP版本名
     */
    fun getVersionName(context: Context): String {
        val packageInfo = getPackageInfo(context)
        return if (packageInfo != null) packageInfo.versionName else "1.0"
    }

    /**
     * APP版本号
     */
    fun getVersionCode(context: Context): Int {
        val packageInfo = getPackageInfo(context)
        return packageInfo?.versionCode ?: 1
    }

    /**
     * 获取APP包信息
     */
    @TargetApi(Build.VERSION_CODES.N)
    fun getPackageInfo(context: Context): PackageInfo? {
        val pm = context.packageManager
        // PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
        // PackageManager.GET_CONFIGURATIONS);
        // PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
        return pm.getPackageInfo(context.packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES)

    }

}