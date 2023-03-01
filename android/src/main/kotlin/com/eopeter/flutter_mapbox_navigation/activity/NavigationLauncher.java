package com.eopeter.flutter_mapbox_navigation.activity;

import android.app.Activity;
import android.content.Intent;
import android.app.PendingIntent;
import android.os.Build;
import com.mapbox.geojson.Point;
import java.io.Serializable;
import java.util.List;

public class NavigationLauncher {

    public static final String KEY_STOP_NAVIGATION = "com.my.mapbox.broadcast.STOP_NAVIGATION";
    
    public static void startNavigation(Activity activity, List<Point> wayPoints) {
        Intent navigationIntent = new Intent(activity, NavigationActivity.class);
        navigationIntent.putExtra("waypoints", (Serializable) wayPoints);
//        activity.startActivity(navigationIntent);

        try {
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            // should be >= S, but there is a S device which SDK_INT==R
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                flags |= PendingIntent.FLAG_IMMUTABLE;
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, navigationIntent, flags);
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void stopNavigation(Activity activity) {
        Intent stopIntent = new Intent();
        stopIntent.setAction(KEY_STOP_NAVIGATION);
        activity.sendBroadcast(stopIntent);
    }
    
}