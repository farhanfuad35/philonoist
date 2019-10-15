package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyOfferDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offer_details);

        setTitle("My Offer Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_MyOfferDetails);
        setSupportActionBar(toolbar);

        Offer offer = (Offer) getIntent().getSerializableExtra("offer");

    }
}
