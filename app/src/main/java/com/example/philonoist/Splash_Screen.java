package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.backendless.Backendless;
import com.backendless.persistence.local.UserTokenStorageFactory;

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
                sleep(3000);

//                if (Backendless.UserService.loggedInUser() == "") {
//
//                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.Login.class);
//                    startActivity(intent);
//                    Splash_Screen.this.finish();
//                } else {
//
//
//                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
//                    startActivity(intent);
//                    Splash_Screen.this.finish();
//                }

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
        });

        thread.start();
    }

}
