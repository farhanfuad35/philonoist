package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.LoadRelationsQueryBuilder;

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

        BackendlessUser user = Backendless.UserService.CurrentUser();
        String useremail = CONSTANTS.getCurrentUserEmail();
        System.out.println(useremail);

        if(useremail == offer.getEmail()) {
            //so the user who posted this offer is logged in
            //therefore he/she sees the candidates button

            btnCandidates.setVisibility(View.VISIBLE);
        }else{
            //any other user will see the interested button
            btnInterested.setVisibility(View.VISIBLE);
        }

        setFieldValues();

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
}