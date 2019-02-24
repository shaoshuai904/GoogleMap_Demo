package com.maple.googlemap.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * @author maple
 * @time 2019/2/22
 */
public class SpatialRelationUtil {

//    public class LatLng {
//        double latitude;
//        double longitude;
//    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param mPoints 多边形坐标点列表
     * @param point   待判断点
     * @return true 多边形包含这个点,false 多边形未包含这个点。
     */
    public static boolean isPolygonContainsPoint(List<LatLng> mPoints, LatLng point) {
        int nCross = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            LatLng p1 = mPoints.get(i);
            LatLng p2 = mPoints.get((i + 1) % mPoints.size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.longitude == p2.longitude)
                continue;
            // point 在p1p2 底部 --> 无交点
            if (point.longitude < Math.min(p1.longitude, p2.longitude))
                continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.longitude >= Math.max(p1.longitude, p2.longitude))
                continue;
            // 求解 point点水平线与当前p1p2边的交点的 X 坐标
            double x = (point.longitude - p1.longitude) * (p2.latitude - p1.latitude) / (p2.longitude - p1.longitude) + p1.latitude;
            if (x > point.latitude) // 当x=point.x时,说明point在p1p2线段上
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }

    /**
     * 返回一个点是否在一个多边形边界上
     *
     * @param mPoints 多边形坐标点列表
     * @param point   待判断点
     * @return true 点在多边形边上,false 点不在多边形边上。
     */
    public static boolean isPointInPolygonBoundary(List<LatLng> mPoints, LatLng point) {
        for (int i = 0; i < mPoints.size(); i++) {
            LatLng p1 = mPoints.get(i);
            LatLng p2 = mPoints.get((i + 1) % mPoints.size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数

            // point 在p1p2 底部 --> 无交点
            if (point.longitude < Math.min(p1.longitude, p2.longitude))
                continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.longitude > Math.max(p1.longitude, p2.longitude))
                continue;

            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.longitude == p2.longitude) {
                double minX = Math.min(p1.latitude, p2.latitude);
                double maxX = Math.max(p1.latitude, p2.latitude);
                // point在水平线段p1p2上,直接return true
                if ((point.longitude == p1.longitude) && (point.latitude >= minX && point.latitude <= maxX)) {
                    return true;
                }
            } else { // 求解交点
                double x = (point.longitude - p1.longitude) * (p2.latitude - p1.latitude) / (p2.longitude - p1.longitude) + p1.latitude;
                if (x == point.latitude) // 当x=point.x时,说明point在p1p2线段上
                    return true;
            }
        }
        return false;
    }

}