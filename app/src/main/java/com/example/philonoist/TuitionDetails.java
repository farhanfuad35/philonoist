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
import com.backendless.persistence.LoadRelationsQueryBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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



//
//        final BackendlessUser user = Backendless.UserService.CurrentUser();
//        String useremail = CONSTANTS.getCurrentUserEmail();
//        System.out.println(useremail);


        setFieldValues();

        btnInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // offer.setID(user.getUserId());//here is an user id issue one is string another is integer


                //and also how to update this offer in the backendless daatabase? --> working on it

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

    public String load(){
        FileInputStream fileInputStream = null;
        String email ="";

        try {
            fileInputStream = openFileInput(CONSTANTS.getUserData());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((email = bufferedReader.readLine()) != null){
                stringBuilder.append(email);
            }
            email = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(email);
        return email;
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

                String useremail = load();
                System.out.println("loaded email "+useremail);
                System.out.println("offer posted by "+email);

                if(useremail.equals(email)) {
                    //so the user who posted this offer is logged in
                    //therefore he/she sees the candidates button

                    btnCandidates.setVisibility(View.VISIBLE);
                }else{
                    //any other user will see the interested button
                    btnInterested.setVisibility(View.VISIBLE);


                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("relation query", "relation query error " + fault.getMessage());
            }
        });
    }
}