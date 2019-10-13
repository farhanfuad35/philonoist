package com.example.philonoist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

class Location_Methods {

    static List<GeoPoint> geoPoints;

    // This method saves a geoPoint to given category

    public static void saveGeoPoints(final Context context, double latitude, double longitude, String category)
    {
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        geoPoint.addCategory(category);

        Backendless.Geo.savePoint(geoPoint, new AsyncCallback<GeoPoint>() {
            @Override
            public void handleResponse(GeoPoint response) {
                Log.i(CONSTANTS.getTAG_MAPS_SHOW_TUITIONS(), "Saved Succesfully");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i(CONSTANTS.getTAG_MAPS_SHOW_TUITIONS(), "Saving Unsuccessfull");
            }
        });
    }


//    // This method returns a list of GeoPoints containing all the geopoints of the given category
//
//    public static List<GeoPoint> getGeoPoints(final Context context, String category, boolean receiveMetadata)
//    {
//        final BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
//        geoQuery.addCategory(category);
//        geoQuery.setIncludeMeta(receiveMetadata);
//
//
//
//        Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
//            @Override
//            public void handleResponse(List<GeoPoint> geoPoints_list) {
//                geoPoints = geoPoints_list;
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Toast.makeText(context, "Couldn't Retrieve", Toast.LENGTH_LONG).show();
//                Log.e(CONSTANTS.getTagMapsGetgeopoints(), "Couldn't retrieve geopoints from the server");
//            }
//        });
//
//        return geoPoints;
//    }
}
