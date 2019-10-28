package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class Select_Tuition_Location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AutocompleteSupportFragment autocompleteSupportFragment;
    FloatingActionButton fabSelect;
    private Boolean markerSelected = false;
    private LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__tuition__location);

        fabSelect = findViewById(R.id.fabSelectTuition_select);






        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    public void searchPlaces(final GoogleMap mMap){

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }



        autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("SelectTuition", "Trying to select Item");

                LatLng newLatLng =  place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(newLatLng).title(place.getName()));


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(newLatLng)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("SelectTuition", "Error occured trying to select Item");
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Location_Methods.showMyLocation(this, mMap);
        searchPlaces(mMap);



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Title").draggable(true));
                markerSelected = true;
                currentLatLng = latLng;
            }
        });

        fabSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markerSelected){

                    // Save this location on GeoTable
                    //Location_Methods.saveGeoPoints(v.getContext(), currentLatLng, "tuition_locations");


                    GeoPoint geoPoint = new GeoPoint(currentLatLng.latitude, currentLatLng.longitude);
                    geoPoint.addCategory("tuition_locations");

                    Backendless.Geo.savePoint(geoPoint, new AsyncCallback<GeoPoint>() {
                        @Override
                        public void handleResponse(GeoPoint response) {
                            Log.i(CONSTANTS.getTAG_MAPS_SHOW_TUITIONS(), "Saved Succesfully");

                            Intent intent = new Intent();
                            intent.putExtra("geoPoint" , response);

                            Log.i("post with map", "returning from select location on map");

                            setResult(RESULT_OK, intent);
                            Select_Tuition_Location.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i(CONSTANTS.getTAG_MAPS_SHOW_TUITIONS(), "Saving Unsuccessfull");
                        }
                    });



                }
                else{
                    Toast.makeText(v.getContext(), "Please search or tap to select a location", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
