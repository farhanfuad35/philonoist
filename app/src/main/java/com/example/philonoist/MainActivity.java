package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] options = new String[]{"Tuition Details", "My Offers", "Tuition List", "Candidate List", "Notifications","Login","SignUp","Post Offer"};

        ListView listView = findViewById(R.id.lvMain_Options);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.TuitionDetails.class);
                    startActivity(intent);
                }
                if(i == 1){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.MyOffers.class);
                    startActivity(intent);
                }
                if(i == 2){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.TuitionList.class);
                    startActivity(intent);
                }
                if(i == 3){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.CandidateList.class);
                    startActivity(intent);
                }
                if(i == 4){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.Notifications.class);
                    startActivity(intent);
                }
                if(i == 5){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.Login.class);
                    startActivity(intent);
                }
                if(i == 6){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.SignUp.class);
                    startActivity(intent);
                }
                



            }
        });
    }

}
