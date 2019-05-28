package com.maple.googlemap.utils

import com.google.android.gms.maps.model.LatLng


/**
 * Polygon 与 Point 空间关系 工具类
 *
 * @author maple
 * @time 2019/2/22
 */
object SpatialRelationUtil {

    /**
     * 获取不规则多边形几何中心点
     *
     * @param mPoints
     * @return - 几何中心
     */
    fun getCenterPoint(mPoints: List<LatLng>): LatLng {
        // 使用Google map API提供的方法（不建议自己计算）
        val boundsBuilder = LatLngBounds.builder()
        for (ll in mPoints)
            boundsBuilder.include(ll)
        return boundsBuilder.build().center
    }

    /**
     * 获取不规则多边形重心点
     *
     * @param mPoints
     * @return - 重心
     */
    fun getCenterOfGravityPoint(mPoints: List<LatLng>): LatLng {
        var area = 0.0//多边形面积
        var Gx = 0.0
        var Gy = 0.0// 重心的x、y
        for (i in 1..mPoints.size) {
            val iLat = mPoints[i % mPoints.size].latitude
            val iLng = mPoints[i % mPoints.size].longitude
            val nextLat = mPoints[i - 1].latitude
            val nextLng = mPoints[i - 1].longitude
            val temp = (iLat * nextLng - iLng * nextLat) / 2.0
            area += temp
            Gx += temp * (iLat + nextLat) / 3.0
            Gy += temp * (iLng + nextLng) / 3.0
        }
        Gx /= area
        Gy /= area
        return LatLng(Gx, Gy)
    }

    /**
     * 返回一个点是否在一个多边形区域内（推荐）
     *
     * @param mPoints 多边形坐标点列表
     * @param point   待判断点
     * @return true 多边形包含这个点,false 多边形未包含这个点。
     */
    fun isPolygonContainsPoint1(mPoints: List<LatLng>, point: LatLng): Boolean {
        val boundsBuilder = LatLngBounds.builder()
        for (ll in mPoints)
            boundsBuilder.include(ll)
        // 如果point不在多边形Bounds范围内，直接返回false。
        return if (boundsBuilder.build().contains(point)) {
            isPolygonContainsPoint(mPoints, point)
        } else {
            false
        }
    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param mPoints 多边形坐标点列表
     * @param point   待判断点
     * @return true 多边形包含这个点,false 多边形未包含这个点。
     */
    fun isPolygonContainsPoint(mPoints: List<LatLng>, point: LatLng): Boolean {
        var nCross = 0
        for (i in mPoints.indices) {
            val p1 = mPoints[i]
            val p2 = mPoints[(i + 1) % mPoints.size]
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.longitude == p2.longitude)
                continue
            // point 在p1p2 底部 --> 无交点
            if (point.longitude < Math.min(p1.longitude, p2.longitude))
                continue
            // point 在p1p2 顶部 --> 无交点
            if (point.longitude >= Math.max(p1.longitude, p2.longitude))
                continue
            // 求解 point点水平线与当前p1p2边的交点的 X 坐标
            val x = (point.longitude - p1.longitude) * (p2.latitude - p1.latitude) / (p2.longitude - p1.longitude) + p1.latitude
            if (x > point.latitude)
            // 当x=point.x时,说明point在p1p2线段上
                nCross++ // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return nCross % 2 == 1
    }

    /**
     * 返回一个点是否在一个多边形边界上
     *
     * @param mPoints 多边形坐标点列表
     * @param point   待判断点
     * @return true 点在多边形边上,false 点不在多边形边上。
     */
    fun isPointInPolygonBoundary(mPoints: List<LatLng>, point: LatLng): Boolean {
        for (i in mPoints.indices) {
            val p1 = mPoints[i]
            val p2 = mPoints[(i + 1) % mPoints.size]
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数

            // point 在p1p2 底部 --> 无交点
            if (point.longitude < Math.min(p1.longitude, p2.longitude))
                continue
            // point 在p1p2 顶部 --> 无交点
            if (point.longitude > Math.max(p1.longitude, p2.longitude))
                continue

            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.longitude == p2.longitude) {
                val minX = Math.min(p1.latitude, p2.latitude)
                val maxX = Math.max(p1.latitude, p2.latitude)
                // point在水平线段p1p2上,直接return true
                if (point.longitude == p1.longitude && point.latitude >= minX && point.latitude <= maxX) {
                    return true
                }
            } else { // 求解交点
                val x = (point.longitude - p1.longitude) * (p2.latitude - p1.latitude) / (p2.longitude - p1.longitude) + p1.latitude
                if (x == point.latitude)
                // 当x=point.x时,说明point在p1p2线段上
                    return true
            }
        }
        return false
    }


}