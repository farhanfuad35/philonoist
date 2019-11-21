package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class postOffer extends AppCompatActivity {

    int lastENabledEditText = 1;            // all other fields exccept the last one are added to the string when the add icon is clicked. But add icon
                                        // is not clicked for the last field. hence the input needs to be taken manually

    EditText etName;
    EditText etClass;
    EditText etsalary;

    EditText etsubject1;
    EditText etsubject2;
    EditText etsubject3;
    EditText etsubject4;
    EditText etsubject5;
    EditText etsubject6;
    EditText etsubject7;
    EditText etsubject8;
    EditText etsubject9;
    EditText etsubject10;


    EditText etContact;
    EditText etRemarks;
    Button btnlocation;
    Button btnPost;

    LinearLayout llSubject1;
    LinearLayout llSubject2;
    LinearLayout llSubject3;
    LinearLayout llSubject4;
    LinearLayout llSubject5;
    LinearLayout llSubject6;
    LinearLayout llSubject7;
    LinearLayout llSubject8;
    LinearLayout llSubject9;
    LinearLayout llSubject10;

    ImageView ivAdd1;
    ImageView ivAdd2;
    ImageView ivAdd3;
    ImageView ivAdd4;
    ImageView ivAdd5;
    ImageView ivAdd6;
    ImageView ivAdd7;
    ImageView ivAdd8;
    ImageView ivAdd9;

    String name;
    String _class;
    String salary;
    String finalSubject;
    String contact;
    String remarks;
    boolean active;

    ProgressBar progressBar;
    TextView tvPostingOffer;
    ScrollView svPostOffer;

    final int SELECT_LOCATION_INTENT_ID = 99;
    final int FINE_LOCATION_PERMISSION_CODE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        setTitle("Post Offer");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_PostOffer);
        setSupportActionBar(toolbar);

        InitializeFields();




        btnlocation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions

                    Log.i("location", "calling permission request window");


                    ActivityCompat.requestPermissions(postOffer.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_CODE);

                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.

                    return;
                }

                else {

                    Intent intent = new Intent(getApplicationContext(), Select_Tuition_Location.class);
                    startActivityForResult(intent, SELECT_LOCATION_INTENT_ID);
                }

            }
        });

        ivAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject1.getText().toString().isEmpty()){
                    etsubject1.setError("Please enter a subject");
                }
                else {

                    Log.i("post", "came here");

                    llSubject2.setVisibility(View.VISIBLE);
                    ivAdd1.setVisibility(View.INVISIBLE);
                    //etsubject1.setEnabled(false);
                }

            }
        });

        ivAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject2.getText().toString().isEmpty()){
                    etsubject2.setError("Please enter a subject");
                }
                else {
                    llSubject3.setVisibility(View.VISIBLE);
                    ivAdd2.setVisibility(View.INVISIBLE);
                    //etsubject2.setEnabled(false);
                }
            }
        });

        ivAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject3.getText().toString().isEmpty()){
                    etsubject3.setError("Please enter a subject");
                }
                else {
                    llSubject4.setVisibility(View.VISIBLE);
                    ivAdd3.setVisibility(View.INVISIBLE);
                    //etsubject3.setEnabled(false);
                }
            }
        });

        ivAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject4.getText().toString().isEmpty()){
                    etsubject4.setError("Please enter a subject");
                }
                else {
                    llSubject5.setVisibility(View.VISIBLE);
                    ivAdd4.setVisibility(View.INVISIBLE);
                    //etsubject4.setEnabled(false);
                }
            }
        });

        ivAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject5.getText().toString().isEmpty()){
                    etsubject5.setError("Please enter a subject");
                }
                else {
                    llSubject6.setVisibility(View.VISIBLE);
                    ivAdd5.setVisibility(View.INVISIBLE);
                    //etsubject5.setEnabled(false);
                }
            }
        });

        ivAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject6.getText().toString().isEmpty()){
                    etsubject6.setError("Please enter a subject");
                }
                else {
                    llSubject7.setVisibility(View.VISIBLE);
                    ivAdd6.setVisibility(View.INVISIBLE);
                    //etsubject6.setEnabled(false);
                }
            }
        });

        ivAdd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etsubject7.getText().toString().isEmpty()){
                    etsubject7.setError("Please enter a subject");
                }
                else {
                    llSubject8.setVisibility(View.VISIBLE);
                    ivAdd7.setVisibility(View.INVISIBLE);
                    //etsubject7.setEnabled(false);
                }
            }
        });

        ivAdd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etsubject8.getText().toString().isEmpty()) {
                    etsubject8.setError("Please enter a subject");
                } else {
                    llSubject9.setVisibility(View.VISIBLE);
                    ivAdd8.setVisibility(View.INVISIBLE);
                    //etsubject8.setEnabled(false);
                }
            }
        });

        ivAdd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etsubject9.getText().toString().isEmpty()) {
                    etsubject9.setError("Please enter a subject");
                } else {
                    llSubject10.setVisibility(View.VISIBLE);
                    ivAdd9.setVisibility(View.INVISIBLE);
                    //etsubject9.setEnabled(false);
                }
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

                    final GeoPoint geoPoint = (GeoPoint) data.getSerializableExtra("geoPoint");

                    if(etsubject1.getText().toString().isEmpty()){
                        etsubject1.setError("Please enter a subject");
                    }
                    else{


                        Dialog dialog = new Dialog(postOffer.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loadingdialog);
                        dialog.show();


                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                syncOfferWithDatabase(geoPoint);
                            }
                        });

                        thread.start();






                    }
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
        String subject;

        _class = etClass.getText().toString().trim();
        salary = etsalary.getText().toString().trim();
        name = etName.getText().toString().trim();
        contact = etContact.getText().toString().trim();
        remarks = etRemarks.getText().toString();
        active = true;

        subject = etsubject1.getText().toString() + "|" + etsubject2.getText().toString() + "|" + etsubject3.getText().toString() + "|" +
                etsubject4.getText().toString() + "|" + etsubject5.getText().toString() + "|" + etsubject6.getText().toString() + "|" +
                etsubject7.getText().toString() + "|" + etsubject8.getText().toString() + "|" + etsubject9.getText().toString() + "|" +
                etsubject10.getText().toString();

        String[] subjects = FileMethods.processSubjectString(subject);

        Log.i("subject" , "subject : " + subject);

        finalSubject = new String();

//        for(String sub : subjects){
//            finalSubject = finalSubject + sub + "|";
//        }

        for(int i=0; i<subjects.length-1; i++){
            finalSubject = finalSubject + subjects[i] + "|";
        }

        finalSubject = finalSubject + subjects[subjects.length - 1];

        //finalSubject.substring(0, finalSubject.length()-1);

        Log.i("subject", "finalSubject : " + finalSubject);

        saveNewOffer(geoPoint);
        //postOffer.this.finish();
    }




    public void saveNewOffer(final GeoPoint geoPoint) {
        Offer newoffer = new Offer();
        newoffer.set_class(_class);
        newoffer.setSalary(salary);
        newoffer.setSubject(finalSubject);
        newoffer.setContact(contact);
        newoffer.setRemarks(remarks);
        newoffer.setActive(active);
        newoffer.setName(name);
        newoffer.setDatLatitude(geoPoint.getLatitude().toString());
        newoffer.setDatLongitude(geoPoint.getLongitude().toString());

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



                        Intent intent = new Intent(postOffer.this, TuitionList.class);
                        startActivity(intent);
                        finish();




//                        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
//                        //dataQueryBuilder.addRelated("_class");
//                        String whereClause = "active = true";
//                        dataQueryBuilder.setWhereClause(whereClause);
//                        dataQueryBuilder.addProperty("subject");
//                        dataQueryBuilder.addProperty("salary");
//                        dataQueryBuilder.addProperty("_class");
//                        dataQueryBuilder.addProperty("objectId");
//                        dataQueryBuilder.addProperty("remarks");
//                        dataQueryBuilder.addProperty("contact");
//                        dataQueryBuilder.addProperty("location");
//                        dataQueryBuilder.addProperty("active");
//                        dataQueryBuilder.addProperty("name");
//                        dataQueryBuilder.setPageSize(20);               // Number of objects retrieved per page
//
//
//                        Backendless.Data.of(Offer.class).find(dataQueryBuilder, new AsyncCallback<List<Offer>>() {
//                            @Override
//                            public void handleResponse(List<Offer> response) {
//                                CONSTANTS.offers = response;
//
//                                Intent intent = new Intent(postOffer.this, TuitionList.class);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                            @Override
//                            public void handleFault(BackendlessFault fault) {
//                                Log.e("refresh", "on TuitionList/swipeRefresh\t" + fault.getMessage());
//                            }
//                        });




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


    private void InitializeFields(){
        etName = findViewById(R.id.etpostoffer_name);
        etClass = findViewById(R.id.etpostoffer_class);
        etsalary = findViewById(R.id.etpostoffer_salary);
        etContact = findViewById(R.id.etpostoffer_Contact);
        etRemarks = findViewById(R.id.etpostoffer_Remarks);
        btnlocation = findViewById(R.id.btnPostoffer_location);
        btnPost = findViewById(R.id.btnpostoffer_Post);

        llSubject1 = findViewById(R.id.llPostOffer_Subject1);
        llSubject2 = findViewById(R.id.llPostOffer_Subject2);
        llSubject3 = findViewById(R.id.llPostOffer_Subject3);
        llSubject4 = findViewById(R.id.llPostOffer_Subject4);
        llSubject5 = findViewById(R.id.llPostOffer_Subject5);
        llSubject6 = findViewById(R.id.llPostOffer_Subject6);
        llSubject7 = findViewById(R.id.llPostOffer_Subject7);
        llSubject8 = findViewById(R.id.llPostOffer_Subject8);
        llSubject9 = findViewById(R.id.llPostOffer_Subject9);
        llSubject10 = findViewById(R.id.llPostOffer_Subject10);

        ivAdd1 = findViewById(R.id.ivPostOffer_Subject1);
        ivAdd2 = findViewById(R.id.ivPostOffer_Subject2);
        ivAdd3 = findViewById(R.id.ivPostOffer_Subject3);
        ivAdd4 = findViewById(R.id.ivPostOffer_Subject4);
        ivAdd5 = findViewById(R.id.ivPostOffer_Subject5);
        ivAdd6 = findViewById(R.id.ivPostOffer_Subject6);
        ivAdd7 = findViewById(R.id.ivPostOffer_Subject7);
        ivAdd8 = findViewById(R.id.ivPostOffer_Subject8);
        ivAdd9 = findViewById(R.id.ivPostOffer_Subject9);

        etsubject1 = findViewById(R.id.etpostoffer_Subject1);
        etsubject2 = findViewById(R.id.etpostoffer_Subject2);
        etsubject3 = findViewById(R.id.etpostoffer_Subject3);
        etsubject4 = findViewById(R.id.etpostoffer_Subject4);
        etsubject5 = findViewById(R.id.etpostoffer_Subject5);
        etsubject6 = findViewById(R.id.etpostoffer_Subject6);
        etsubject7 = findViewById(R.id.etpostoffer_Subject7);
        etsubject8 = findViewById(R.id.etpostoffer_Subject8);
        etsubject9 = findViewById(R.id.etpostoffer_Subject9);
        etsubject10 = findViewById(R.id.etpostoffer_Subject10);

        progressBar = findViewById(R.id.pbPostOffer_Loading);
        tvPostingOffer = findViewById(R.id.tvPostOffer_PostingOffer);
        svPostOffer = findViewById(R.id.svPostOffer_scrollview);
    }
}
