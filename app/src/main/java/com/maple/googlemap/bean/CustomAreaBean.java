package com.maple.googlemap.bean;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义区域
 *
 * @author maple
 * @time 2019/2/22
 */
public class CustomAreaBean {

    private String name;
    private Polygon polygon;
    private List<Marker> markers;

    public CustomAreaBean() {
    }

    public CustomAreaBean(String name) {
        this.name = name;
    }

    public CustomAreaBean(String name, List<Marker> markers) {
        this.name = name;
        this.markers = markers;
    }

    public int findMarkerIndex(Marker marker) {
        if (markers == null)
            return -1;
        return markers.indexOf(marker);
    }

    // 是否包含某个标记
    public boolean isContainMarker(Marker marker) {
        return findMarkerIndex(marker) != -1;
    }

    public void addMarker(Marker marker) {
        if (markers == null)
            markers = new ArrayList<>();
        markers.add(marker);
    }

    public boolean updateMarker(Marker marker) {
        if (markers == null)
            return false;
        int index = findMarkerIndex(marker);
        if (index != -1) {
            markers.get(index).setPosition(marker.getPosition());
            polygon.setPoints(getPoints());
            return true;
        }
        return false;
    }

    public void setMarkerVisible(boolean visible) {
        if (markers != null) {
            for (Marker marker : markers) {
                marker.setVisible(visible);
            }
        }
    }

    public void clear() {
        if (markers != null) {
            setMarkerVisible(false);
            markers.clear();
            markers = null;
        }
        if (polygon != null) {
            polygon.setVisible(false);
            polygon = null;
        }
    }

    public void setSelState(boolean isSelect) {
        if (polygon == null)
            return;
        if (isSelect) {
            polygon.setFillColor(0xAAddcdef);
            polygon.setStrokeColor(0xFFff0000);
            polygon.setStrokeWidth(4f);
        } else {
            polygon.setFillColor(0xFFff0000);
            polygon.setStrokeColor(0xFFffff00);
            polygon.setStrokeWidth(7f);
        }
    }

    public List<LatLng> getPoints() {
        List<LatLng> latLngs = new ArrayList<>();
        for (Marker marker : markers) {
            latLngs.add(marker.getPosition());
        }
        return latLngs;
    }

    public boolean isNull() {
        if (markers == null || markers.size() == 0) {
            return true;
        }
        return false;
    }

    //是否形成区域
    public boolean isFormingArea() {
        if (markers != null && markers.size() >= 3) {
            return true;
        }
        return false;
    }

    //------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    public void setMarkers(List<Marker> markers) {
        this.markers = markers;
    }

    @Override
    public String toString() {
        return "CustomAreaBean{" +
                "name = '" + name + '\'' +
                ", polygon point size = " + polygon.getPoints().size() +
                ", markers = " + getPointInfo() +
                '}';
    }

    public String getPointInfo() {
        StringBuilder sb = new StringBuilder();
        for (Marker marker : markers) {
            LatLng latLng = marker.getPosition();
            sb.append("new LatLng(" + latLng.latitude + "," + latLng.longitude + "),");
        }
        return sb.toString();
    }
}
