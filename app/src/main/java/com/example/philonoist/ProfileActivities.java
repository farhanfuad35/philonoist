package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileActivities extends AppCompatActivity {

    final int POSTOFFER = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activities);

        setTitle("Profile Activities");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_ProfileActivities);
        setSupportActionBar(toolbar);

        String[] options = new String[]{"My Offers", "Notifications", "Post Offer", "Candidate List"};

        ListView listView = findViewById(R.id.lvProfAct);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.MyOffers.class);
                    startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.Notifications.class);
                    startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.postOffer.class);
                    startActivityForResult(intent, POSTOFFER);
                }
                if(position == 3){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.CandidateList.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == POSTOFFER){
            if(resultCode == RESULT_OK){
                Tuition tuition = (Tuition) data.getSerializableExtra("newTuition");
                Intent intent = new Intent();
                intent.putExtra("newTuition", tuition);
                setResult(RESULT_OK, intent);
                ProfileActivities.this.finish();
            }
        }
    }
}
