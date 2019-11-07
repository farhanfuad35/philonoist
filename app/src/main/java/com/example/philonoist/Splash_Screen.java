package com.example.philonoist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

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
        //dataQueryBuilder.setGroupBy("_class");
        //dataQueryBuilder.setSortBy("_class");


        Backendless.Data.of(Offer.class).find(dataQueryBuilder, new AsyncCallback<List<Offer>>() {
            @Override
            public void handleResponse(List<Offer> response) {

                CONSTANTS.offers = response;


                if (Backendless.UserService.loggedInUser() == "") {

                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.Login.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                } else {


                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                }




                Log.i("Subject", "response size: "+Integer.toString(response.size()));
                Log.i("Subject", "CONSTANTS' offers' size: "+Integer.toString(CONSTANTS.offers.size()));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Connection Failed!")
                        .setMessage("Please connect to the internet")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                //Toast.makeText(getApplicationContext(), "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
