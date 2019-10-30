package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

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
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TuitionDetails extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private String[] subjects;
    private TextView hostName;
    private TextView salary;
    private Offer offer;
    private Button btnInterested;
    private Button btnCandidates;
    public  int interestedUserID;

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


        offer = (Offer) getIntent().getSerializableExtra("offer");
        Log.i("objectId", offer.getObjectId());
        final int index = getIntent().getIntExtra("index", 0);
        //the offer that came is basically CONSTANTS.offer.get(index)
        //but what about the offer that came from the maps??!!!!!!!!!!!!!


        getNameFromUsersTable();
        getEmailFromUsersTable();

        setFieldValues();

        btnInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = FileMethods.load(getApplicationContext());
                System.out.println("in interested: "+userEmail);
                String offerID = offer.getObjectId();
                System.out.println("Offer ID: "+ offerID);
                //String userEmail = Backendless.UserService.CurrentUser().getEmail();
                saveNewApplicant(userEmail, offerID);

                Backendless.Data.of(Offer.class).save(offer, new AsyncCallback<Offer>() {
                    @Override
                    public void handleResponse(Offer response) {
                        Toast.makeText(TuitionDetails.this, "Your Application Submitted!", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(TuitionDetails.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuitionDetails.this, CandidateList.class);
                intent.putExtra("offerID", offer.getObjectId());
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });
    }

    private void setFieldValues(){
        salary.setText(offer.getSalary());
        subjects = new String[]{offer.getSubject()};            // Cannot be Null
        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(listViewAdapter);
    }

    private void initializeFields(){
        listView = findViewById(R.id.lvDetails_Subject);
        hostName = findViewById(R.id.tvDetails_hostName);
        salary = findViewById(R.id.tvDetails_salaryNumber);
        btnInterested = findViewById(R.id.btnInterested);
        btnCandidates = findViewById(R.id.btnCandidates);
    }

    private LoadRelationsQueryBuilder prepareLoadRelaionQuery(String relationFieldName)
    {
        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
        loadRelationsQueryBuilder.setRelationName( relationFieldName );

        return loadRelationsQueryBuilder;
    }



    private void getNameFromUsersTable(){
        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelaionQuery("email");
        Backendless.Data.of("Offer").loadRelations(offer.getObjectId(), loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> users) {
                String text = users.get(0).getProperty("first_name") + " " + users.get(0).getProperty("last_name");
                hostName.setText(text);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("relation query", "relation query error " + fault.getMessage());
            }
        });
    }


    private void getEmailFromUsersTable(){
        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelaionQuery("email");
        Backendless.Data.of("Offer").loadRelations(offer.getObjectId(), loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> users) {
                String email = (String) users.get(0).getEmail();

                String useremail = FileMethods.load(getApplicationContext());
                System.out.println("loaded email "+useremail);
                System.out.println("offer posted by "+email);

                if(useremail.equals(email)) {
                    btnCandidates.setVisibility(View.VISIBLE);
                }else{
                    btnInterested.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("relation query", "relation query error " + fault.getMessage());
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
                Toast.makeText(getApplicationContext(), "STRING MESSAGE", Toast.LENGTH_LONG).show();
                setRelation(applicants1, userlist);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(TuitionDetails.this, "You have already applied for this tuition!", Toast.LENGTH_SHORT).show();
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
}