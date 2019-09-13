package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
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

public class TuitionList extends AppCompatActivity {

    ListView listView;
    String[] tuitionTitles;
    SearchView editSearch;
    TuitionListAdapter adapter;
    ArrayList<Tuition> tuitionArrayList = new ArrayList<Tuition>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_list);

        tuitionTitles = new String[]{"Tuition 1",
        "Tuition 2", "Tuition 3"};

        listView = findViewById(R.id.lvTuitionList_TuitionList);

        for(int i=0; i< tuitionTitles.length; i++){
            Tuition tuition = new Tuition(tuitionTitles[i]);
            tuitionArrayList.add(tuition);
        }

        adapter = new TuitionListAdapter(this, tuitionArrayList);

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);


        MenuItem actionMenuItem = menu.findItem(R.id.menuMain_Search);

        //SearchManager searchManager =
                //(SearchManager) getSystemService(Context.SEARCH_SERVICE);
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
        return super.onOptionsItemSelected(item);
    }
}