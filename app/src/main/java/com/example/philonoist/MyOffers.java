package com.example.philonoist;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyOffers extends AppCompatActivity {

    public boolean flag = true;
    ViewTuitionAdapter viewTuitionAdapter;
    List<Offer> myPostedOffers;
    final int POSTOFFER = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        FloatingActionButton fab = findViewById(R.id.fab_add);

        setTitle("My Offers");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_MyOffer);
        setSupportActionBar(toolbar);

        final ListView lvMyOffersList = findViewById(R.id.lvOffers_MyOffers);
        String loggedInUserEmail = FileMethods.load(getApplicationContext());
        Log.i("myOfferUser", loggedInUserEmail);

//        BackendlessUser user = Backendless.UserService.CurrentUser();
//        //String useremail = user.getEmail();
//        String useremail = FileMethods.load(getApplicationContext());
//        System.out.println(useremail);
//        DataQueryBuilder dataQuery = DataQueryBuilder.create();
//        dataQuery.addRelated("email");
//        String whereClause = "email.email = '" + useremail+ "'";
//        System.out.println(whereClause);
//        dataQuery.setWhereClause(whereClause);
//        dataQuery.addProperty("subject");
//        dataQuery.addProperty("salary");
//        dataQuery.addProperty("_class");
//        dataQuery.addProperty("objectId");
//        dataQuery.addProperty("remarks");
//        dataQuery.addProperty("contact");
//        dataQuery.addProperty("location");
//        dataQuery.addProperty("active");
//
//        Backendless.Data.of(Offer.class).find(dataQuery, new AsyncCallback<List<Offer>>() {
//            @Override
//            public void handleResponse(List<Offer> offerList) {
//                myPostedOffers = offerList;
//                viewTuitionAdapter = new ViewTuitionAdapter(MyOffers.this, myPostedOffers);
//                lvMyOffersList.setAdapter(viewTuitionAdapter);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//
//            }
//        });

        for(int i=0; i<CONSTANTS.offers.size(); i++){
            if(loggedInUserEmail.equals((String)CONSTANTS.offers.get(i).getMailAddress())){
                myPostedOffers.add((Offer)CONSTANTS.offers.get(i));
                Log.i("mailAddress" +i, CONSTANTS.offers.get(i).getMailAddress());
            }
        }

        if(myPostedOffers.size() == 0){
            Log.i("myOfferSize", "00000000000");
        }else{
            Log.i("MyOffersSize", Integer.toString(myPostedOffers.size()));
        }
        viewTuitionAdapter = new ViewTuitionAdapter(getApplicationContext(), myPostedOffers);
        lvMyOffersList.setAdapter(viewTuitionAdapter);

        lvMyOffersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyOffers.this, TuitionDetails.class);
                //intent.putExtra("index", i);
                //startActivityForResult(intent, resultCodeForTuitionDetails);
                intent.putExtra("offer", myPostedOffers.get(i));
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lvMyOffersList.getContext(), com.example.philonoist.postOffer.class);
                startActivityForResult(intent, POSTOFFER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == POSTOFFER && resultCode == Activity.RESULT_OK){
            Offer tuition = (Offer) data.getSerializableExtra("newTuition");
            Intent intent = new Intent();
            intent.putExtra("newTuition", tuition);
            setResult(Activity.RESULT_OK, intent);
            MyOffers.this.finish();
        }
    }

}