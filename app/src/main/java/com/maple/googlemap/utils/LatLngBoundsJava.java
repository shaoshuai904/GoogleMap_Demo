package com.maple.googlemap.utils;

/**
 * 经纬度范围类
 * <p>
 * 复写com.google.android.gms.maps.model.LatLngBounds中核心方法
 *
 * @author maple
 * @time 2019-05-28
 */
@Deprecated //("条件允许，请使用com.google.android.gms.maps.model.LatLngBounds")
public class LatLngBoundsJava {
    public final JLatLng southwest;// 左下角 点
    public final JLatLng northeast;// 右上角 点

    private LatLngBoundsJava(JLatLng southwest, JLatLng northeast) {
//        Preconditions.checkNotNull(southwest, "null southwest");
//        Preconditions.checkNotNull(northeast, "null northeast");
//        Preconditions.checkArgument(northeast.latitude >= southwest.latitude, "southern latitude exceeds northern latitude (%s > %s)", new Object[]{southwest.latitude, northeast.latitude});
        this.southwest = southwest;
        this.northeast = northeast;
    }

    // 获取中心点
    public JLatLng getCenter() {
        // 计算中心点纬度
        double centerLat = (this.southwest.latitude + this.northeast.latitude) / 2.0;
        // 计算中心点经度
        double neLng = this.northeast.longitude;// 右上角 经度
        double swLng = this.southwest.longitude; // 左下角 经度
        double centerLng;
        if (swLng <= neLng) {
            centerLng = (neLng + swLng) / 2.0;
        } else {
            centerLng = (neLng + 360.0 + swLng) / 2.0;
        }
        return new JLatLng(centerLat, centerLng);
    }

    // 小数据量可以使用该方法，大数据量建议使用Builder中的include()
    public LatLngBoundsJava including(JLatLng point) {
        double swLat = Math.min(this.southwest.latitude, point.latitude);
        double neLat = Math.max(this.northeast.latitude, point.latitude);
        double neLng = this.northeast.longitude;
        double swLng = this.southwest.longitude;
        double pLng = point.longitude;
        if (!this.lngContains(pLng)) {
            if (zza(swLng, pLng) < zzb(neLng, pLng)) {
                swLng = pLng;
            } else {
                neLng = pLng;
            }
        }
        return new LatLngBoundsJava(new JLatLng(swLat, swLng), new JLatLng(neLat, neLng));
    }

    // 某个点是否在该范围内（包含边界）
    public boolean contains(JLatLng point) {
        return latContains(point.latitude) && this.lngContains(point.longitude);
    }

    // 某个纬度值是否在该范围内（包含边界）
    public boolean latContains(Double lat) {
        return this.southwest.latitude <= lat && lat <= this.northeast.latitude;
    }

    // 某个经度值是否在该范围内（包含边界）
    public boolean lngContains(Double lng) {
        if (this.southwest.longitude <= this.northeast.longitude) {
            return this.southwest.longitude <= lng && lng <= this.northeast.longitude;
        } else {
            return this.southwest.longitude <= lng || lng <= this.northeast.longitude;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // 前者 - 后者
    private static double zza(double var0, double var2) {
        return (var0 - var2 + 360.0D) % 360.0D;
    }

    // 后者 - 前者
    private static double zzb(double var0, double var2) {
        return (var2 - var0 + 360.0D) % 360.0D;
    }

    /**
     * LatLngBounds生成器
     */
    @Deprecated //("条件允许，请使用com.google.android.gms.maps.model.LatLng")
    public static final class Builder {
        private double swLat = 1.0D / 0.0; // 左下角 纬度
        private double swLng = 0.0D / 0.0; // 左下角 经度
        private double neLat = -1.0D / 0.0; // 右上角 纬度
        private double neLng = 0.0D / 0.0; // 右上角 经度

        public Builder() {
        }

        public final Builder include(JLatLng point) {
            this.swLat = Math.min(this.swLat, point.latitude);
            this.neLat = Math.max(this.neLat, point.latitude);
            double pLng = point.longitude;
            if (Double.isNaN(this.swLng)) {
                this.swLng = pLng;
            } else {
                // 某个经度值是否在该范围内（包含边界）
                if (this.swLng <= this.neLng ?
                        this.swLng <= pLng && pLng <= this.neLng :
                        this.swLng <= pLng || pLng <= this.neLng) {
                    return this;
                }

                if (zza(this.swLng, pLng) < zzb(this.neLng, pLng)) {
                    this.swLng = pLng;
                    return this;
                }
            }

            this.neLng = pLng;
            return this;
        }

        public final LatLngBoundsJava build() {
            // Preconditions.checkState(!Double.isNaN(this.swLng), "no included points");
            return new LatLngBoundsJava(new JLatLng(this.swLat, this.swLng), new JLatLng(this.neLat, this.neLng));
        }
    }

    @Deprecated //("条件允许，请使用com.google.android.gms.maps.model.LatLng")
    public static final class JLatLng {
        public final double latitude;
        public final double longitude;

        public JLatLng(double lat, double lng) {
            if (-180.0D <= lng && lng < 180.0D) {
                // 经度合格，直接赋值
                this.longitude = lng;
            } else {
                // 修正经度。经度必须在[-180,180]之间
                this.longitude = ((lng - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
            }
            // 纬度必须在[-90,90]范围内
            this.latitude = Math.max(-90.0D, Math.min(90.0D, lat));
        }

    }
}