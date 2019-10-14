package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TuitionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_details);

        setTitle("Tuition Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_TuitionDetails);
        setSupportActionBar(toolbar);


        Offer offer = (Offer) getIntent().getSerializableExtra("offer");

        Log.i("tuition" , offer.getSalary());
        Log.i("tuition" , offer.getSubject());
        Log.i("tuition" , offer.get_class());


        String[] subjects = new String[]{offer.getSubject(), "English"};

        ListView listView = findViewById(R.id.lvDetails_Subject);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects);

        listView.setAdapter(listViewAdapter);

        TextView hostName = findViewById(R.id.tvDetails_hostName);
        hostName.setText("Abraham Lincoln");

        TextView salary = findViewById(R.id.tvDetails_salaryNumber);
        salary.setText(offer.getSalary());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
