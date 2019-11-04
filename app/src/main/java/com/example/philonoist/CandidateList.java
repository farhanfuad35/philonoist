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

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class CandidateList extends AppCompatActivity {
    ListView lvCandidatesList;
    CandidatesListAdapter candidatesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        final String offerID = getIntent().getStringExtra("offerID");
        final int index = getIntent().getIntExtra("index", 0);

        final List<String> names = new ArrayList<>();
        lvCandidatesList = findViewById(R.id.lvCandidateList);
        final ArrayAdapter<String> listViewNamesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);

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
                    //System.out.println("applicant: " + applicants.get(i).getID());
                        if(applicants.get(i).getID() != null){
                            int length = applicants.get(i).getID().length();
                            length -= offerID.length();
                            String userEmail = applicants.get(i).getID().substring(0, length);
                            System.out.println("Check: "+userEmail);

                            DataQueryBuilder dataQueryBuilder1 = DataQueryBuilder.create();
                            String whereClauseForUser = "email = '" + userEmail +"'";
                            dataQueryBuilder1.setWhereClause(whereClauseForUser);
                            dataQueryBuilder1.addProperty("first_name");
                            dataQueryBuilder1.addProperty("last_name");

                            Backendless.Data.of(BackendlessUser.class).find(dataQueryBuilder1, new AsyncCallback<List<BackendlessUser>>() {
                                @Override
                                public void handleResponse(List<BackendlessUser> users) {
                                    List<BackendlessUser> tempUsers = new ArrayList<>();
                                    int i;
                                    for( i=0; i<users.size(); i++){
                                        BackendlessUser user = new BackendlessUser();
                                        user.setProperty("first_name", users.get(i).getProperty("first_name"));
                                        user.setProperty("last_name", users.get(i).getProperty("last_name"));
                                        user.setEmail(users.get(i).getEmail());
                                        tempUsers.add(user);
                                        String name = users.get(i).getProperty("first_name") + " " + users.get(i).getProperty("last_name");
                                        names.add(name);
                                        lvCandidatesList.setAdapter(listViewNamesAdapter);
                                    }
                                    Log.i("nameSize", Integer.toString(names.size()));
                                    Log.i("cList", Integer.toString(users.size()));
                                    Log.i("cListSize", Integer.toString(i));
//                                    BackendlessUser use = new BackendlessUser();
//                                    use.setEmail("asdfjsdfkn@gmail.com");
//                                    use.setProperty("first_name", "Kabir");
//                                    use.setProperty("last_name", "Ahmed");
//                                    tempUsers.add(use);
//
//                                        candidatesListAdapter = new CandidatesListAdapter(getApplicationContext(), tempUsers);
//                                        lvCandidatesList.setAdapter(candidatesListAdapter);

                                    Log.i("cList2", Integer.toString(users.size()));
                                    Log.i("cListSize2", Integer.toString(i));
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

        //ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>();
        //listView.setAdapter(listViewAdapter);

        lvCandidatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
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
