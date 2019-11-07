package com.example.philonoist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.backendless.Backendless;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Collection;
import java.util.List;

import static android.os.SystemClock.sleep;

public class Splash_Screen extends AppCompatActivity {

    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        setStatusbarColor();


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        splashImage = findViewById(R.id.ivSplashScreen_philonoistLogo);

        Animation anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        splashImage.startAnimation(anim);


        runWaitingThread();



    }


    private void setStatusbarColor()
    {
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }


    private void runWaitingThread()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                callTuitionListFromBackendless();

            }
        });

        thread.start();
    }

    private void callTuitionListFromBackendless(){
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        //dataQueryBuilder.addRelated("_class");
        String whereClause = "_class is not null";
        System.out.println(whereClause);
        dataQueryBuilder.setWhereClause(whereClause);
        dataQueryBuilder.addProperty("subject");
        dataQueryBuilder.addProperty("salary");
        dataQueryBuilder.addProperty("_class");
        dataQueryBuilder.addProperty("objectId");
        dataQueryBuilder.addProperty("remarks");
        dataQueryBuilder.addProperty("contact");
        dataQueryBuilder.addProperty("location");
        //dataQueryBuilder.setGroupBy("_class");
        //dataQueryBuilder.setSortBy("_class");


        Backendless.Data.of(Offer.class).find(dataQueryBuilder, new AsyncCallback<List<Offer>>() {
            @Override
            public void handleResponse(List<Offer> response) {

                CONSTANTS.offers = response;
                getPointsFromDatabase();



                Log.i("Subject", "response size: "+Integer.toString(response.size()));
                Log.i("Subject", "CONSTANTS' offers' size: "+Integer.toString(CONSTANTS.offers.size()));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                if( fault.getMessage() == getString(R.string.connectionErrorMessageBackendless))
                    showConnectionFailedDialog();

                else{
                     Backendless.UserService.logout(new AsyncCallback<Void>() {
                         @Override
                         public void handleResponse(Void response) {
                             Intent intent = getIntent();
                             finish();
                             startActivity(intent);
                         }

                         @Override
                         public void handleFault(BackendlessFault fault) {
                             Log.i("logout", "logout failed");
                         }
                     });
                }

                Log.e("fault", fault.getMessage());

                Toast.makeText(getApplicationContext(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void getPointsFromDatabase() {

        final BackendlessGeoQuery geoQuery = new BackendlessGeoQuery();
        geoQuery.addCategory("tuition_locations");


        Backendless.Geo.getPoints(geoQuery, new AsyncCallback<List<GeoPoint>>() {
            @Override
            public void handleResponse(List<GeoPoint> response) {



                CONSTANTS.setGeoPointList(response);




                double lat_min = CONSTANTS.getLatMin();
                double lat_max = CONSTANTS.getLatMax();
                double lng_min = CONSTANTS.getLngMin();
                double lng_max = CONSTANTS.getLngMax();

                int test = 0;


                CONSTANTS.setGeoPointList(response);

                for (GeoPoint geoPoint : response) {
                    if (geoPoint.getLatitude() > lat_max)
                        CONSTANTS.setLatMax(geoPoint.getLatitude() + 0.00001);
                    else if (geoPoint.getLatitude() < lat_min)
                        CONSTANTS.setLatMin(geoPoint.getLatitude() - 0.00001);
                    if (geoPoint.getLongitude() > lng_max)
                        CONSTANTS.setLngMax(geoPoint.getLongitude() + 0.00001);
                    else if (geoPoint.getLongitude() < lng_min)
                        CONSTANTS.setLngMin(geoPoint.getLongitude() - 0.00001);


//                    LatLng temp = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
//
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(temp).title("Title"));
//                    marker.setTag(geoPoint.getObjectId());
//
//
//                    Log.i("lat lang", geoPoint.getLatitude().toString());
//                    test++;
                }

                Log.i("lat lang", "test = " + Integer.toString(test));









                String userToken = UserTokenStorageFactory.instance().getStorage().get();

                if( userToken != null && !userToken.equals( "" ) )
                {   Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.Login.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showConnectionFailedDialog();


            }
        });


    }

    private void showConnectionFailedDialog()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Splash_Screen.this);
        alertDialogBuilder.setTitle("Connection Failed!");
        alertDialogBuilder.setMessage("Please check your internet connection and try again");
        alertDialogBuilder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(Splash_Screen.this,"You clicked yes button",Toast.LENGTH_LONG).show();

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
