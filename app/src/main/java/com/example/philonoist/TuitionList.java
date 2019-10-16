package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TuitionList extends AppCompatActivity {

    ListView listView;
    String[] tuitionAdvertisers;
    String[] location;
    String[] salary;
    TuitionListAdapter adapter;
    ArrayList<Tuition> tuitionArrayList = new ArrayList<>();
    ArrayList<ArrayList<String>> listOfSubjects = new ArrayList<>();
    FloatingActionButton fabMaps;

    final int PROFILEACTIVITIES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_list);

        setTitle("Tuition List");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_TuitionList);
        setSupportActionBar(toolbar);

        fabMaps = findViewById(R.id.fabTuitionList_Map);

        fabMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(getApplicationContext(), Maps_Show_Tuitions.class);
                startActivity(intentMap);
            }
        });

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