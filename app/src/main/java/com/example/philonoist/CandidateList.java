package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;

import java.util.List;
import java.lang.String;

public class CandidateList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        System.out.println("IN CANDIDATES LIST!!!!!!!!!!!!!!!!!!!!!!");
        final String offerID = getIntent().getStringExtra("offerID");
        final int index = getIntent().getIntExtra("index", 0);

        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        String whereClause = "offerID = '" + offerID + "'";
        System.out.println("whereclause in candidatesList: "+whereClause);
        dataQueryBuilder.setWhereClause(whereClause);
        dataQueryBuilder.addProperty("ID");
        dataQueryBuilder.addProperty("objectId");
        dataQueryBuilder.addProperty("offerID");


        Backendless.Data.of(Applicants.class).find(dataQueryBuilder, new AsyncCallback<List<Applicants>>() {
            @Override
            public void handleResponse(List<Applicants> applicants) {
                for (int i=0; i<applicants.size(); i++) {
                    //System.out.println("applicant: " + applicants.get(i).getID());
                        if(applicants.get(i).getID() != null){
                            int length = applicants.get(i).getID().length();
                            length -= offerID.length();
                            String userEmail = applicants.get(i).getID().substring(0, length);
                            System.out.println("Check: "+userEmail);
                        }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "No applicants yet!", Toast.LENGTH_SHORT).show();
            }
        });
        //eita hoitese CONSTANTS.offer er index jar candidates list dekhte chawa hoise
        //ekhon ei offer tay joto uiser id connected tader ekta listView dekhaite hobe
        //listView theke abar aager moto (tuitionList e jmn dekhaisilam) user details e jaite hobe --> user details banaite hobe

        ListView listView = findViewById(R.id.lvCandidateList);
        //ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>();
        //listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        System.out.println("BYE BYE");
    }

//    private LoadRelationsQueryBuilder prepareLoadRelaionQuery(String relationFieldName)
//    {
//        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
//        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
//        loadRelationsQueryBuilder.setRelationName( relationFieldName );
//
//        return loadRelationsQueryBuilder;
//    }
//
//    private void getNameFromUsersTable(){
//        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelaionQuery("email");
//        Backendless.Data.of("Applicants").loadRelations(offerID, loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
//            @Override
//            public void handleResponse(List<BackendlessUser> users) {
//                String text = users.get(0).getProperty("first_name") + " " + users.get(0).getProperty("last_name");
//                hostName.setText(text);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.i("relation query", "relation query error " + fault.getMessage());
//            }
//        });
//    }

}
