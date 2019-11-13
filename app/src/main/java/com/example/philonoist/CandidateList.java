package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class CandidateList extends AppCompatActivity {
    ListView lvCandidatesList;
    CandidatesListAdapter candidatesListAdapter;
    final int userInfo = 39;

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == userInfo){
            if(resultCode == RESULT_OK){
                //Intent intent = new Intent();
                setResult(RESULT_OK);
                //CandidateList.this.finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        final String offerID = getIntent().getStringExtra("offerID");
        Log.i("offerIDinCandidatesList", offerID);
        final int index = getIntent().getIntExtra("index", 0);

        lvCandidatesList = findViewById(R.id.lvCandidateList);

        final List<String> names = new ArrayList<>();
        final List<String> emails = new ArrayList<>();
        final ArrayAdapter<String> listViewNamesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
        final List<BackendlessUser> users = new ArrayList<>();

        final DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
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
                        if(applicants.get(i).getID() != null){
                            int length = applicants.get(i).getID().length();
                            length -= offerID.length();
                            final String userEmail = applicants.get(i).getID().substring(0, length);
                            System.out.println("Check: "+userEmail);

                            DataQueryBuilder dataQueryBuilder1 = DataQueryBuilder.create();
                            String whereClauseForUser = "email = '" + userEmail +"'";
                            dataQueryBuilder1.setWhereClause(whereClauseForUser);
                            dataQueryBuilder1.addProperty("first_name");
                            dataQueryBuilder1.addProperty("last_name");
                            dataQueryBuilder1.addProperty("email");
                            dataQueryBuilder1.addProperty("contact_no");

                            Backendless.Data.of(BackendlessUser.class).find(dataQueryBuilder1, new AsyncCallback<List<BackendlessUser>>() {
                                @Override
                                public void handleResponse(List<BackendlessUser> response) {
                                    Log.i("responseSize", Integer.toString(response.size()));
//                                    for(int i=0; i<response.size(); i++){
//                                        response.get(i).setEmail(userEmail);
//                                    }
                                    users.addAll(response);
                                    Log.i("userSize", Integer.toString(users.size()));

                                    candidatesListAdapter = new CandidatesListAdapter(getApplicationContext(), users);
                                    lvCandidatesList.setAdapter(candidatesListAdapter);

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(getApplicationContext(), "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "No applicants yet!", Toast.LENGTH_SHORT).show();
            }
        });


        lvCandidatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                intent.putExtra("user", users.get(i));
                intent.putExtra("offerID", offerID);
                //startActivity(intent);
                startActivityForResult(intent, userInfo);
            }
        });
    }


    private void loadListview(List<BackendlessUser> users){
        Log.i("userSize", Integer.toString(users.size()));
        candidatesListAdapter = new CandidatesListAdapter(getApplicationContext(), users);
        lvCandidatesList.setAdapter(candidatesListAdapter);
    }
//    private LoadRelationsQueryBuilder prepareLoadRelaionQuery(String relationFieldName)
//    {
//        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
//        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
//        loadRelationsQueryBuilder.setRelationName( relationFieldName );
//
//        return loadRelationsQueryBuilder;
//    }

//    private void getCandidatesList(int index){
//        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelaionQuery("email");
//        Backendless.Data.of("Applicants").loadRelations(CONSTANTS.offers.get(index).getObjectId(), loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
//            @Override
//            public void handleResponse(List<BackendlessUser> users) {
//                for(int i=0; i<users.size(); i++){
//                    String text = users.get(i).getProperty("first_name") + " " + users.get(i).getProperty("last_name");
//                    Log.i("NAME", text);
//                }
//                //loadListview(users);
//                Log.i("userSize", Integer.toString(users.size()));
//                candidatesListAdapter = new CandidatesListAdapter(getApplicationContext(), users);
//                lvCandidatesList.setAdapter(candidatesListAdapter);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.i("relation query", "relation query error " + fault.getMessage());
//            }
//        });
//    }

}
