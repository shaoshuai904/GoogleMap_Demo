package com.maple.googlemap.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * APP管理类
 *
 * @author shaoshuai
 */
public class AppUtils {

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * APP版本名
     */
    public static String getAppVersionString(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        String verName = "1.0";
        int verCode = 1;
        if (packageInfo != null) {
            verName = packageInfo.versionName;
            verCode = packageInfo.versionCode;
        }
        return "Version " + verName + " - (Build " + verCode + ")";
    }

    /**
     * APP版本名
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null)
            return packageInfo.versionName;
        return "1.0";
    }

    /**
     * APP版本号
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null)
            return packageInfo.versionCode;
        return 1;
    }

    /**
     * 获取APP包信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            // PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
            // PackageManager.GET_CONFIGURATIONS);
            // PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}