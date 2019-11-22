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


        final List<String> myOffers = new ArrayList<>();

        BackendlessUser user = Backendless.UserService.CurrentUser();
        //String useremail = user.getEmail();
        String useremail = FileMethods.load(getApplicationContext());
        System.out.println(useremail);
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        //dataQuery.addRelated("email");
        String whereClause = "email.email = '" + useremail+ "'";
        System.out.println(whereClause);
        dataQuery.setWhereClause(whereClause);
        dataQuery.addProperty("subject");
        dataQuery.addProperty("salary");
        dataQuery.addProperty("_class");
        dataQuery.addProperty("objectId");
        dataQuery.addProperty("remarks");
        dataQuery.addProperty("name");
        dataQuery.addProperty("contact");
        dataQuery.addProperty("mailAddress");
        dataQuery.addProperty("location");
        dataQuery.addProperty("active");

        final ListView listView = findViewById(R.id.lvOffers_MyOffers);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myOffers);

        Backendless.Data.of(Offer.class).find(dataQuery, new AsyncCallback<List<Offer>>() {
            @Override
            public void handleResponse(List<Offer> offerList) {
                myPostedOffers = offerList;
                viewTuitionAdapter = new ViewTuitionAdapter(MyOffers.this, myPostedOffers);
                listView.setAdapter(viewTuitionAdapter);

                Log.i("location", "location query in my offers : " + offerList.get(0).getLocation().getLatitude().toString());
                Log.i("mail", "mail in my offer : " + offerList.get(0).getEmail());

//                for (Offer offer : offerList) {
//                    myOffers.add(offer.getSubject());
//                    Log.i("subject", "in loop " + Integer.toString(myOffers.size()));
//                    listView.setAdapter(listViewAdapter);
//
//                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        Log.i("subject", Integer.toString(myOffers.size()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyOffers.this, TuitionDetails.class);
                //intent.putExtra("index", i);
                //startActivityForResult(intent, resultCodeForTuitionDetails);
                intent.putExtra("offer", myPostedOffers.get(i));
                intent.putExtra("index", i);
                intent.putExtra("ID", CONSTANTS.getActivityIdMyoffers());
                startActivity(intent);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.getContext(), com.example.philonoist.postOffer.class);
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