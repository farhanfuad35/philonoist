package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class postOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        setTitle("Post Offer");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_PostOffer);
        setSupportActionBar(toolbar);
    }
}
