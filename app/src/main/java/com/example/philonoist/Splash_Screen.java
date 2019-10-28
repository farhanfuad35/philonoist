package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

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

import static android.os.SystemClock.sleep;

public class Splash_Screen extends AppCompatActivity {

    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        splashImage = findViewById(R.id.ivSplashScreen_philonoistLogo);

        Animation anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        splashImage.startAnimation(anim);

        Log.i("splash", "in Splash Screen");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(3000);

                if(Backendless.UserService.loggedInUser()== "") {

                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.Login.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                }
                else{


                    Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                }
            }
        });

        thread.start();




    }
}
