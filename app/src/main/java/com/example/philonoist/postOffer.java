package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class postOffer extends AppCompatActivity {

    EditText etName;
    EditText etClass;
    EditText etsalary;
    EditText etsubject1;
    EditText etContact;
    EditText etRemarks;
    Button btnlocation;
    Button btnPost;

    String name;
    String _class;
    String salary;
    String subject1;
    String contact;
    String remarks;



    final int SELECT_LOCATION_INTENT_ID = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        setTitle("Post Offer");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_PostOffer);
        setSupportActionBar(toolbar);

        etName = findViewById(R.id.etpostoffer_name);
        etClass = findViewById(R.id.etpostoffer_class);
        etsalary = findViewById(R.id.etpostoffer_salary);
        etsubject1 = findViewById(R.id.etpostoffer_Subject1);
        etContact = findViewById(R.id.etpostoffer_Contact);
        etRemarks = findViewById(R.id.etpostoffer_Remarks);
        btnlocation = findViewById(R.id.btnPostoffer_location);
        btnPost = findViewById(R.id.btnpostoffer_Post);

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Select_Tuition_Location.class);
                startActivityForResult(intent, SELECT_LOCATION_INTENT_ID);
            }
        });

    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultCode == RESULT_OK && requestCode == SELECT_LOCATION_INTENT_ID) {
                    Log.i("post with map", "returned with intent data");

                    GeoPoint geoPoint = (GeoPoint) data.getSerializableExtra("geoPoint");

                    syncOfferWithDatabase(geoPoint);
                }

                else{
                    Log.i("post with map", "clicked btnPost but result or request code not ok");
                }

            }
        });
    }

    protected void syncOfferWithDatabase(GeoPoint geoPoint)
    {


        Log.i("post with map", "in Sync Offer with Database");

        _class = etClass.getText().toString().trim();
        salary = etsalary.getText().toString().trim();
        subject1 = etsubject1.getText().toString().trim();
        name = etName.getText().toString().trim();
        contact = etContact.getText().toString().trim();
        remarks = etRemarks.getText().toString();

        saveNewOffer(_class, salary, subject1,contact, remarks, geoPoint);
        postOffer.this.finish();
    }




    public void saveNewOffer(String _class, String salary, String subject,String contact,String remarks, final GeoPoint geoPoint) {
        Offer newoffer = new Offer();
        newoffer.set_class(_class);
        newoffer.setSalary(salary);
        newoffer.setSubject(subject);
        newoffer.setContact(contact);
        newoffer.setRemarks(remarks);
        final ArrayList<BackendlessUser> userlist = new ArrayList<>();
        BackendlessUser user = Backendless.UserService.CurrentUser();
        userlist.add(user);


        Log.i("post with map", "in save new offer");


        Backendless.Data.of(Offer.class).save(newoffer, new AsyncCallback<Offer>() {

            @Override
            public void handleResponse(Offer NewOffer) {
                Toast.makeText(getApplicationContext(), "STRING MESSAGE", Toast.LENGTH_LONG).show();
                // Log.i(TAG, "Order has been saved");
                 setRelation(NewOffer, userlist, geoPoint);

                Log.i("post with map", "saved offer successfully");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //Log.e(TAG, fault.getMessage());
            }
        });





        Log.i("post with map", "set relation successfully");
    }

    private  void setRelation(final Offer Newoffer, ArrayList<BackendlessUser>userList, final GeoPoint geoPoint) {

        Backendless.Data.of(Offer.class).addRelation(Newoffer, "email", userList, new AsyncCallback<Integer>(){
            @Override
            public void handleResponse(Integer response) {
                Log.i("addRelation", "Relation has been set");

                final ArrayList<GeoPoint> geoPointList = new ArrayList<>();
                geoPointList.add(geoPoint);

                Backendless.Data.of(Offer.class).addRelation(Newoffer, "location:GeoPoint:1", geoPointList, new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {
                        Log.i("addRelation", "GeoRelation created successfully");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("addRelation", "Georelation unsuccessfull " + fault.getMessage() + "\tArraylist size = " + Integer.toString(geoPointList.size()));
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("SetRelation", "Relation wasn't set" + fault.getMessage());
            }
        });


    }
}
