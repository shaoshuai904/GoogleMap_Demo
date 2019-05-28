package com.maple.googlemap.utils

import com.google.android.gms.maps.model.LatLng

/**
 * 经纬度范围类
 *
 * 复写com.google.android.gms.maps.model.LatLngBounds中核心方法
 *
 * @author maple
 * @time 2019-05-28
 */
@Deprecated("条件允许，请使用com.google.android.gms.maps.model.LatLngBounds")
class LatLngBounds constructor(
        private val southwest: LatLng,// 左下角 点
        private val northeast: LatLng // 右上角 点
) {

    val center: LatLng
        get() {
            // 计算中心点纬度
            val centerLat = (this.southwest.latitude + this.northeast.latitude) / 2.0
            // 计算中心点经度
            val neLng = this.northeast.longitude // 右上角 经度
            val swLng: Double = this.southwest.longitude // 左下角 经度
            val centerLng: Double = if (swLng <= neLng) {
                (neLng + swLng) / 2.0
            } else {
                (neLng + 360.0 + swLng) / 2.0
            }
            return LatLng(centerLat, centerLng)
        }

    // 某个点是否在该范围内（包含边界）
    fun contains(point: LatLng): Boolean {
        return latContains(point.latitude) && this.lngContains(point.longitude)
    }

    // 某个纬度值是否在该范围内（包含边界）
    private fun latContains(lat: Double): Boolean {
        return this.southwest.latitude <= lat && lat <= this.northeast.latitude
    }

    // 某个经度值是否在该范围内（包含边界）
    private fun lngContains(lng: Double): Boolean {
        return if (this.southwest.longitude <= this.northeast.longitude) {
            this.southwest.longitude <= lng && lng <= this.northeast.longitude
        } else {
            this.southwest.longitude <= lng || lng <= this.northeast.longitude
        }
    }

    // 小数据量可以使用该方法，大数据量建议使用Builder中的include()
    fun including(point: LatLng): LatLngBounds {
        val swLat = Math.min(this.southwest.latitude, point.latitude)
        val neLat = Math.max(this.northeast.latitude, point.latitude)
        var neLng = this.northeast.longitude
        var swLng = this.southwest.longitude
        val pLng = point.longitude
        if (!this.lngContains(pLng)) {
            if (zza(swLng, pLng) < zzb(neLng, pLng)) {
                swLng = pLng
            } else {
                neLng = pLng
            }
        }
        return LatLngBounds(LatLng(swLat, swLng), LatLng(neLat, neLng))
    }

    /**
     * LatLngBounds生成器
     */
    class Builder {
        private var swLat = 1.0 / 0.0   // 左下角 纬度
        private var swLng = 0.0 / 0.0   // 左下角 经度
        private var neLat = -1.0 / 0.0  // 右上角 纬度
        private var neLng = 0.0 / 0.0   // 右上角 经度

        fun include(point: LatLng): Builder {
            this.swLat = Math.min(this.swLat, point.latitude)
            this.neLat = Math.max(this.neLat, point.latitude)
            val pLng = point.longitude
            if (java.lang.Double.isNaN(this.swLng)) {
                this.swLng = pLng
            } else {
                if (lngContains(pLng)) {
                    return this
                }
                if (zza(this.swLng, pLng) < zzb(this.neLng, pLng)) {
                    this.swLng = pLng
                    return this
                }
            }
            this.neLng = pLng
            return this
        }

        // 某个经度值是否在该范围内（包含边界）
        private fun lngContains(lng: Double): Boolean {
            return if (this.swLng <= this.neLng) {
                this.swLng <= lng && lng <= this.neLng
            } else {
                this.swLng <= lng || lng <= this.neLng
            }
        }

        fun build(): LatLngBounds {
            // Preconditions.checkState(!java.lang.Double.isNaN(this.swLng), "no included points")
            return LatLngBounds(LatLng(this.swLat, this.swLng), LatLng(this.neLat, this.neLng))
        }
    }

//    /**
//     * 经纬度点
//     */
//    @Deprecated("条件允许，请使用com.google.android.gms.maps.model.LatLng")
//    class LatLng(
//            val latitude: Double,   // 纬度
//            val longitude: Double   // 经度
//    ) {
//        override fun toString(): String {
//            return "LatLng(latitude=$latitude, longitude=$longitude)"
//        }
//    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }

        // 前者 - 后者
        private fun zza(var0: Double, var2: Double): Double {
            return (var0 - var2 + 360.0) % 360.0
        }

        // 后者 - 前者
        private fun zzb(var0: Double, var2: Double): Double {
            return (var2 - var0 + 360.0) % 360.0
        }
    }

}
