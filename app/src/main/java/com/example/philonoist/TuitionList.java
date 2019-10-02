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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TuitionList extends AppCompatActivity {

    ListView listView;
    String[] tuitionAdvertisers;
    String[] location;
    String[] remuneration;
    TuitionListAdapter adapter;
    ArrayList<Tuition> tuitionArrayList = new ArrayList<Tuition>();
    ArrayList<ArrayList<String>> listOfSubjects = new ArrayList<ArrayList<String>>();

    final int PROFILEACTIVITIES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_list);

        setTitle("Tuition List");

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_TuitionList);
        setSupportActionBar(toolbar);

        tuitionAdvertisers = new String[]{"Farhan Fuad",
        "Nafisa Naznin", "Sakib Al Mahmud"};
        remuneration = new String[]{"2000/=", "4000/=", "8000/="};

        //String[] subjectString = new String[]{"Bangla", "English", "Global Studies"};
        List<String> subjectString = new ArrayList<String>();
        subjectString.add("Bangla");
        subjectString.add("English");
        subjectString.add("Global Studies");
        ListIterator<String> itr = subjectString.listIterator();
        ArrayList<String> subjects = new ArrayList<String>();

        while(itr.hasNext()){
            subjects.add(itr.next());
        }

        listOfSubjects.add(subjects);

        subjects.clear();
        subjectString.clear();

        subjectString.add("Chemistry");

        while(itr.hasNext()){
            subjects.add(itr.next());
        }

        listOfSubjects.add(subjects);

        subjects.clear();
        subjectString.clear();

        subjectString.add("Math");
        subjectString.add("Physics");

        while(itr.hasNext()){
            subjects.add(itr.next());
        }

        listOfSubjects.add(subjects);

        subjects.clear();
        subjectString.clear();

        location = new String[]{"Dhanmondi", "Uttara", "Mohammadpur"};


        Toast.makeText(getApplicationContext(), "On TuitionList", Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.lvTuitionList_TuitionList);

        for(int i=0; i< tuitionAdvertisers.length; i++){
            Tuition tuition = new Tuition(tuitionAdvertisers[i], remuneration[i], listOfSubjects.get(i), location[i]);
            tuitionArrayList.add(tuition);
        }

        adapter = new TuitionListAdapter(this, tuitionArrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);


        MenuItem actionMenuItem = menu.findItem(R.id.menuMain_Search);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.menuMain_Search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else{
                    adapter.filter(s);
                }
                return false;
            }
        });

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
            startActivityForResult(intent, PROFILEACTIVITIES);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == PROFILEACTIVITIES){
            if(resultCode == RESULT_OK){
                Tuition tuition = (Tuition) data.getSerializableExtra("newTuition");
                tuitionArrayList.add(tuition);
            }
        }
    }
}