package com.example.philonoist;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

class Location_Methods {

    static List<GeoPoint> geoPoints;

    // This method saves a geoPoint to given category

    public static void saveGeoPoints(final Context context, LatLng latLng, String category)
    {
        GeoPoint geoPoint = new GeoPoint(latLng.latitude, latLng.longitude);
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





    // Getting all the geoPoints from database and showing them on the map

    public static void getPointsFromDatabase(final Context context, final GoogleMap mMap) {

        final BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
        geoQuery.addCategory("tuition_locations");


        Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
            @Override
            public void handleResponse(List<GeoPoint> response) {
                double lat_min = CONSTANTS.getLatMin();
                double lat_max = CONSTANTS.getLatMax();
                double lng_min = CONSTANTS.getLngMin();
                double lng_max = CONSTANTS.getLngMax();

                int test = 0;


                for (GeoPoint geoPoint : response) {
                    if (geoPoint.getLatitude() > lat_max)
                        CONSTANTS.setLatMax(geoPoint.getLatitude() + 0.00001);
                    else if (geoPoint.getLatitude() < lat_min)
                        CONSTANTS.setLatMin(geoPoint.getLatitude() - 0.00001);
                    if (geoPoint.getLongitude() > lng_max)
                        CONSTANTS.setLngMax(geoPoint.getLongitude() + 0.00001);
                    else if (geoPoint.getLongitude() < lng_min)
                        CONSTANTS.setLngMin(geoPoint.getLongitude() - 0.00001);


                    LatLng temp = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

                    Marker marker = mMap.addMarker(new MarkerOptions().position(temp).title("Title"));
                    marker.setTag(geoPoint.getObjectId());


                    Log.i("lat lang", geoPoint.getLatitude().toString());
                    test++;
                }

                Log.i("lat lang", "test = " + Integer.toString(test));

                LatLngBounds cameraView = new LatLngBounds(new LatLng(CONSTANTS.getLatMin(),CONSTANTS.getLngMin()), new LatLng(CONSTANTS.getLatMax(), CONSTANTS.getLngMax()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 10));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 11));

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Couldn't Retrieve", Toast.LENGTH_LONG).show();
            }
        });


    }



    // Method to save geoPoints on server

    public static void saveGeoPointsOnDatabase(Context context, LatLng latLng)
    {

//        Location_Methods.saveGeoPoints(context, 23.752219, 90.351246, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.793516, 90.381235, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.748526, 90.395976, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.791532, 90.403645, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.728864, 90.419908, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.741643, 90.438777, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.706655, 90.447356, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.777345, 90.478654, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.735970, 90.496212, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.723285, 90.447733, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.787455, 90.469967, "tuition_locations");
//        Location_Methods.saveGeoPoints(context, 23.703783, 90.3416737, "tuition_locations");



    }

    public static void showMyLocation(final Context context, GoogleMap mMap){
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);



        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            LatLng myPosition = new LatLng(latitude, longitude);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myPosition)      // Sets the center of the map to Mountain View
                    .zoom(14)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


}
