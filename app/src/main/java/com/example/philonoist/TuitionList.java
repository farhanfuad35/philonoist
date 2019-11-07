package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TuitionList extends AppCompatActivity {

    ViewTuitionAdapter viewTuitionAdapter;
    //ListView listView;
    String[] tuitionAdvertisers;
    String[] location;
    String[] salary;
    TuitionListAdapter adapter;
    ArrayList<Tuition> tuitionArrayList = new ArrayList<>();
    ArrayList<ArrayList<String>> listOfSubjects = new ArrayList<>();
    FloatingActionButton fabMaps;

    final int PROFILEACTIVITIES = 10;
    final  int resultCodeForTuitionDetails = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_list);

        setTitle("Tuition List");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_TuitionList);
        setSupportActionBar(toolbar);

        fabMaps = findViewById(R.id.fabTuitionList_Map);


        final List<String> tuitionList = new ArrayList<>();
        final ListView lvTuitionList = findViewById(R.id.lvTuitionList_TuitionList);
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tuitionList);

        fabMaps = findViewById(R.id.fabTuitionList_Map);

        Toast.makeText(getApplicationContext(), "On TuitionList", Toast.LENGTH_SHORT).show();

        viewTuitionAdapter = new ViewTuitionAdapter(TuitionList.this, CONSTANTS.offers);
        lvTuitionList.setAdapter(viewTuitionAdapter);


//        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
//        //dataQueryBuilder.addRelated("_class");
//        String whereClause = "_class is not null";
//        System.out.println(whereClause);
//        dataQueryBuilder.setWhereClause(whereClause);
//        dataQueryBuilder.addProperty("subject");
//        dataQueryBuilder.addProperty("salary");
//        dataQueryBuilder.addProperty("_class");
//        dataQueryBuilder.addProperty("objectId");
//        dataQueryBuilder.addProperty("remarks");
//        dataQueryBuilder.addProperty("contact");
//        //dataQueryBuilder.setGroupBy("_class");
//        //dataQueryBuilder.setSortBy("_class");
//
//
//        Backendless.Data.of(Offer.class).find(dataQueryBuilder, new AsyncCallback<List<Offer>>() {
//            @Override
//            public void handleResponse(List<Offer> response) {
//                CONSTANTS.offers = response;
//                viewTuitionAdapter = new ViewTuitionAdapter(TuitionList.this, CONSTANTS.offers);
//                lvTuitionList.setAdapter(viewTuitionAdapter);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//
//                Toast.makeText(TuitionList.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        viewTuitionAdapter = new ViewTuitionAdapter(TuitionList.this, CONSTANTS.offers);
//        lvTuitionList.setAdapter(viewTuitionAdapter);

        Log.i("Subject", "looping"+Integer.toString(tuitionList.size()));

        lvTuitionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TuitionList.this, TuitionDetails.class);
                //intent.putExtra("index", i);
                //startActivityForResult(intent, resultCodeForTuitionDetails);
                Log.i("details", "details activity going to be created");
                intent.putExtra("offer", CONSTANTS.offers.get(i));
                intent.putExtra("index", i);
                startActivity(intent);


                Log.d("details", "details activity created");
            }
        });


        fabMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(getApplicationContext(), Maps_Show_Tuitions.class);
                startActivity(intentMap);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==resultCodeForTuitionDetails){
            viewTuitionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuMain_Setting){

            return true;
        }
        if(id == R.id.menuMain_profile){
            Intent intent = new Intent(this, com.example.philonoist.ProfileActivities.class);
            startActivity(intent);
        }
        if(id == R.id.menuMain_Logout){
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Toast.makeText(getApplicationContext(), "You are successfully logged out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(getApplicationContext(), "Sorry couldn't logout right now. Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return super.onOptionsItemSelected(item);
    }

}