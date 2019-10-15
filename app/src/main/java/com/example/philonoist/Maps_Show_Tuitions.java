package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.Geo;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.QueryOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class    Maps_Show_Tuitions extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int FINE_LOCATION_CODE = 10;
    int COARSE_LOCATION_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__show__tuitions);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)

    {
        mMap = googleMap;

//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.752219, 90.351246, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.793516, 90.381235, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.748526, 90.395976, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.791532, 90.403645, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.728864, 90.419908, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.741643, 90.438777, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.706655, 90.447356, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.777345, 90.478654, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.735970, 90.496212, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.723285, 90.447733, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.787455, 90.469967, "tuition_locations");
//        Location_Methods.saveGeoPoints(getApplicationContext(), 23.703783, 90.3416737, "tuition_locations");









        // Showing My location on the map

        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        //Getting Current Location
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
//                // here to request the missing permissions, and then overriding
//
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//        }


        Location location = locationManager.getLastKnownLocation(provider);



        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            LatLng myPosition = new LatLng(latitude, longitude);

            googleMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(myPosition)      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }









       // Getting the list of geopoints

        final BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
        geoQuery.addCategory("tuition_locations");
        geoQuery.setIncludeMeta(true);


        Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
            @Override
            public void handleResponse(List<GeoPoint> response) {
                double lat_min = CONSTANTS.getLatMin();
                double lat_max = CONSTANTS.getLatMax();
                double lng_min = CONSTANTS.getLngMin();
                double lng_max = CONSTANTS.getLngMax();


                for(GeoPoint geoPoint : response){
                    if(geoPoint.getLatitude() > lat_max)
                        CONSTANTS.setLatMax(geoPoint.getLatitude() + 0.00001);
                    else if(geoPoint.getLatitude() < lat_min)
                        CONSTANTS.setLatMin(geoPoint.getLatitude() - 0.00001);
                    if(geoPoint.getLongitude() > lng_max)
                        CONSTANTS.setLngMax(geoPoint.getLongitude() + 0.00001);
                    else if(geoPoint.getLongitude() < lng_min)
                        CONSTANTS.setLngMin(geoPoint.getLongitude() - 0.00001);


                    LatLng temp = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

                    Marker marker = mMap.addMarker(new MarkerOptions().position(temp).title("Title"));
                    marker.setTag(geoPoint.getObjectId());


                    Log.i("Lat Lang", geoPoint.getLatitude().toString());
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "Couldn't Retrieve", Toast.LENGTH_LONG).show();
            }
        });






        // Move the camera

        LatLngBounds cameraView = new LatLngBounds(new LatLng(CONSTANTS.getLatMin(),CONSTANTS.getLngMin()), new LatLng(CONSTANTS.getLatMax(), CONSTANTS.getLngMax()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 10));



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                GeoPoint geoPoint = new GeoPoint(latLng.latitude, latLng.longitude);

                String where = "location = '" +(String) marker.getTag() + "'";
                DataQueryBuilder queryBuilder =  DataQueryBuilder.create();
                queryBuilder.setWhereClause(where);
                queryBuilder.addProperty("subject");
                queryBuilder.addProperty("salary");
                queryBuilder.addProperty("_class");
                queryBuilder.addProperty("objectId");
                queryBuilder.addRelated("email");


                Log.i("marker", where);

                Backendless.Data.of(Offer.class).find(queryBuilder, new AsyncCallback<List<Offer>>() {
                    @Override
                    public void handleResponse(List<Offer> offers) {
                        Log.i("offer" , offers.get(0).getSalary());
                        Log.i("offer" , offers.get(0).getSubject());
                        Log.i("offer" , offers.get(0).get_class());
                        Log.i("offer" , offers.get(0).getEmail());
                        Log.i("offer" , offers.get(0).getObjectId());
                        Intent intent = new Intent(getApplicationContext(), TuitionDetails.class);
                        intent.putExtra("offer", offers.get(0));
                        startActivity(intent);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("offer retrieve", "Not Retrieved");
                    }
                });



                return false;
            }
        });
    }
}
