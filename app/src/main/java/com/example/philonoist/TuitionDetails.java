package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_details);
        setTitle("Tuition Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_TuitionDetails);
        setSupportActionBar(toolbar);


        offer = (Offer) getIntent().getSerializableExtra("offer");
        Log.i("objectId", offer.getObjectId());

        initializeFields();
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
    }

    private LoadRelationsQueryBuilder prepareLoadRelaionQuery(String relationFieldName)
    {
        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
        loadRelationsQueryBuilder.setRelationName( relationFieldName );

        return loadRelationsQueryBuilder;
    }
}
