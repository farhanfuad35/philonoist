package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CandidateList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        final int index = getIntent().getIntExtra("index", 0);
        //eita hoitese CONSTANTS.offer er index jar candidates list dekhte chawa hoise
        //ekhon ei offer tay joto uiser id connected tader ekta listView dekhaite hobe
        //listView theke abar aager moto (tuitionList e jmn dekhaisilam) user details e jaite hobe --> user details banaite hobe

        //Teacher[]
        String[] candidates = new String[]{"Candidate1", "Candidate2", "Candidate3"};

        ListView listView = findViewById(R.id.lvCandidateList);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, candidates);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
