package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.android.gms.maps.CameraUpdate;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap)

    {
        mMap = googleMap;

        // Show My Location on the map


        Location_Methods.showMyLocation(this, mMap);

       // Getting the list of geopoints and show it on map and move the camera

        //Location_Methods.getPointsFromDatabase(this, mMap);

        for(GeoPoint geoPoint : CONSTANTS.getGeoPointList()){
            LatLng temp = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions().position(temp));
            marker.setTag(geoPoint.getObjectId());
        }

        LatLngBounds cameraView = new LatLngBounds(new LatLng(CONSTANTS.getLatMin(), CONSTANTS.getLngMin()), new LatLng(CONSTANTS.getLatMax(), CONSTANTS.getLngMax()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 10));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 11));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


            @Override
            public boolean onMarkerClick(Marker marker) {
                String where = "location = '" +(String) marker.getTag() + "'";
                DataQueryBuilder queryBuilder =  DataQueryBuilder.create();
                queryBuilder.setWhereClause(where);
                queryBuilder.addProperty("subject");
                queryBuilder.addProperty("salary");
                queryBuilder.addProperty("_class");
                queryBuilder.addProperty("objectId");
                queryBuilder.addProperty("remarks");
                queryBuilder.addProperty("contact");
                queryBuilder.addRelated("email");
                queryBuilder.addProperty("active");

                LatLng latLng = marker.getPosition();

                Log.i("marker", where);

                geoQueryOfferDetails(queryBuilder, latLng);

                return false;
            }
        });


    }


    // GeoPoint to Offer details query

    private void geoQueryOfferDetails(DataQueryBuilder queryBuilder, final LatLng latLng){
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
                intent.putExtra("lat", Double.toString(latLng.latitude) );
                intent.putExtra("lng", Double.toString(latLng.longitude) );
                intent.putExtra("ID", CONSTANTS.getActivityIdMapsShowTuitions());
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("offer retrieve", "Not Retrieved");
            }
        });
    }

}
