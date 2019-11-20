package com.example.philonoist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.philonoist.CONSTANTS.getActivityIdTuitionlist;
import static com.example.philonoist.CONSTANTS.offers;

public class    Maps_Show_Tuitions extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


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

        for(int i = 0; i< offers.size(); i++){
            Offer offer = offers.get(i);
            GeoPoint geoPoint = offer.getLocation();

            LatLng temp = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions().position(temp));
            marker.setTag(i);
        }

        LatLngBounds cameraView = new LatLngBounds(new LatLng(CONSTANTS.getLatMin(), CONSTANTS.getLngMin()), new LatLng(CONSTANTS.getLatMax(), CONSTANTS.getLngMax()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 10));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraView.getCenter(), 11));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {


            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), TuitionDetails.class);
                intent.putExtra("offer", CONSTANTS.offers.get((Integer) marker.getTag()));
                intent.putExtra("ID", CONSTANTS.getActivityIdMapsShowTuitions());
                startActivity(intent);

                return false;
            }
        });


    }

}
