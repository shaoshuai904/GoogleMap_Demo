package com.maple.googlemap;

import android.app.Application;

/**
 * @author maple
 * @time 2018/8/8.
 */
public class MapApp extends Application {
    private static MapApp app;

    @Override
    public void onCreate() {
        app = this;
        super.onCreate();
    }

    public static MapApp app() {
        return app;
    }

}
