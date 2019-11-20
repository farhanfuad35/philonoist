package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import java.util.ArrayList;
import java.util.List;

public class TuitionDetails extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private String[] subjects;
    private TextView hostName;
    private TextView salary;
    private Offer offer;
    private Button btnMap;
    private String lat;
    private String lng;
    private Button btnInterested;
    private Button btnCandidates;
    private Button btnCall;
    private TextView tvRemarksContent;
    private TextView tvAssigned;
    private String loggedInUserEmail;
    private String offerPostedByEmail;
    public  int interestedUserID;
    private int callerActivityID;
    final int candidatesList = 49;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == candidatesList){
            if(resultCode == RESULT_OK){
                tvAssigned.setVisibility(View.VISIBLE);
                btnCandidates.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_details);
        setTitle("Tuition Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_TuitionDetails);
        setSupportActionBar(toolbar);

        initializeFields();

        btnInterested.setVisibility(View.GONE);
        btnCandidates.setVisibility(View.GONE);
        tvAssigned.setVisibility(View.GONE);
        tvRemarksContent.setVisibility(View.GONE);
        btnCall.setVisibility(View.GONE);
        btnMap.setVisibility(View.GONE);



        callerActivityID = (int) getIntent().getIntExtra("ID", 0);          // From Maps, ID = 65 | From List, ID = 75
        offer = (Offer) getIntent().getSerializableExtra("offer");
//        lat = getIntent().getStringExtra("lat");
//        lng = getIntent().getStringExtra("lng");

        if(callerActivityID == CONSTANTS.getActivityIdTuitionlist()) {
            lat = (String) offer.getLocation().getLatitude().toString();
            lng = (String) offer.getLocation().getLongitude().toString();
        }
        else if(callerActivityID == CONSTANTS.getActivityIdMapsShowTuitions()){
            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("lng");
        }


        if(offer.isActive()){
            Log.i("activeStatus", "active True");
        }else{
            Log.i("activeStatus", "active False");
            tvAssigned.setVisibility(View.VISIBLE);
        }

        Log.i("offerIDinTuitionDetails", offer.getObjectId());
        Log.i("contact", offer.getContact());
        final int index = getIntent().getIntExtra("index", 0);
        Log.i("index", CONSTANTS.offers.get(index).getObjectId());

        //getNameFromUsersTable();
        getEmailFromUsersTable();

        setFieldValues();

        btnInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String userEmail = FileMethods.load(getApplicationContext());
                System.out.println("in interested: "+loggedInUserEmail);
                String offerID = offer.getObjectId();
                System.out.println("Offer ID: "+ offerID);
                //String userEmail = Backendless.UserService.CurrentUser().getEmail();
                saveNewApplicant(loggedInUserEmail, offerID);
                saveNewNotification(offerPostedByEmail, loggedInUserEmail, offerID);

            }
        });

        btnCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuitionDetails.this, CandidateList.class);
                intent.putExtra("offerID", offer.getObjectId());
                intent.putExtra("index", index);
                //startActivity(intent);
                startActivityForResult(intent, candidatesList);
            }
        });


        Log.i("location", "entering button click location");
        //Log.i("location", offer.getLocation().getLatitude().toString());

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("location", "came to location");

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: ,0"));

                Log.i("location", "lattitude = " + lat + "longitude = " + lng);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 0,0?q=" + lat + ", " + lng));
                startActivity(intent);
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + offer.getContact()));
                startActivity(intent);
            }
        });


    }

    private void setFieldValues(){
        salary.setText(offer.getSalary());
        //subjects = new String[]{offer.getSubject()};            // Cannot be Null

        // TODO

        //subjects = processSubjectString(offer.getSubject());                      // Returns a string of subjects processed from the single line fetched from the database
        subjects = FileMethods.processSubjectString(offer.getSubject());
        //Log.i("subjects", "After split :\t" + subjects[1]);

        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(listViewAdapter);
        tvRemarksContent.setText(offer.getRemarks());
        tvRemarksContent.setVisibility(View.VISIBLE);
        hostName.setText(offer.getName());
    }

    private void initializeFields(){
        listView = findViewById(R.id.lvDetails_Subject);
        hostName = findViewById(R.id.tvDetails_hostName);
        salary = findViewById(R.id.tvDetails_salaryNumber);
        btnInterested = findViewById(R.id.btnInterested);
        btnCandidates = findViewById(R.id.btnCandidates);
        btnMap = findViewById(R.id.btnTuitionDetails_map);
        btnCall = findViewById(R.id.btnTuitionDetails_call);
        tvRemarksContent = findViewById(R.id.tvDetails_remarksContent);
        tvAssigned = findViewById(R.id.tvAssigned);
    }

    private LoadRelationsQueryBuilder prepareLoadRelationQuery(String relationFieldName)
    {
        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
        loadRelationsQueryBuilder.setRelationName( relationFieldName );

        return loadRelationsQueryBuilder;
    }


    private void getEmailFromUsersTable(){
        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelationQuery("email");
        Backendless.Data.of("Offer").loadRelations(offer.getObjectId(), loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> users) {
                offerPostedByEmail = (String) users.get(0).getEmail().trim();

                loggedInUserEmail = FileMethods.load(getApplicationContext()).trim();

                if(loggedInUserEmail.equals(offerPostedByEmail)) {
                    System.out.println("loaded email "+loggedInUserEmail);
                    System.out.println("offer posted by "+offerPostedByEmail);
                    btnCall.setVisibility(View.GONE);
                    btnMap.setVisibility(View.GONE);
                    if(offer.isActive()){
                        btnCandidates.setVisibility(View.VISIBLE);
                    }
                }else{
                    System.out.println("else loaded email "+loggedInUserEmail);
                    System.out.println("else offer posted by "+offerPostedByEmail);
                    if(offer.isActive()){
                        btnCall.setVisibility(View.VISIBLE);
                        btnMap.setVisibility(View.VISIBLE);
                        btnInterested.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("relation query", "relation query error " + fault.getMessage());
            }
        });
    }

    private  void setRelation(final Applicants applicants, ArrayList<BackendlessUser>userList) {

        Backendless.Data.of(Applicants.class).addRelation(applicants, "email", userList, new AsyncCallback<Integer>(){
            @Override
            public void handleResponse(Integer response) {
                Log.i("addRelation", "Relation has been set");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });


    }

    public void saveNewApplicant(String email, String offerID) {
        Applicants applicants = new Applicants();
        String ID = email+offerID;
        applicants.setOfferID(offerID);
        applicants.setEmail(email);
        applicants.setID(ID);


        final ArrayList<BackendlessUser> userlist = new ArrayList<>();
        BackendlessUser user = Backendless.UserService.CurrentUser();
        userlist.add(user);
        Backendless.Data.of(Applicants.class).save(applicants, new AsyncCallback<Applicants>() {

            @Override
            public void handleResponse(Applicants applicants1) {
                Toast.makeText(getApplicationContext(), "Your Application Submitted!", Toast.LENGTH_LONG).show();
                Log.i("Applicants1", applicants1.getID());
                setRelation(applicants1, userlist);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(TuitionDetails.this, "You have already applied for this tuition!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveNewNotification(String user_email, String teacher_email, String offerID){
        Notifications notifications = new Notifications();
        notifications.setUser_email(user_email);
        notifications.setTeacher_email(teacher_email);
        notifications.setMessage(" applied for your tuition offer ");
        notifications.setOfferID(offerID);

        Backendless.Data.of(Notifications.class).save(notifications, new AsyncCallback<Notifications>() {
            @Override
            public void handleResponse(Notifications response) {
                Log.i("notification", "notification saved in database");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("notification", "notification NOT!!! saved in database");
            }
        });
    }

}