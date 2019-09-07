package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TuitionList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_list);

        //Tuition[]
        String[] tuitions = new String[]{"Tuition1", "Tuition2", "Tuition3"};

        ListView listView = findViewById(R.id.lvTuitionList);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tuitions);
        listView.setAdapter(listViewAdapter);

    }
}