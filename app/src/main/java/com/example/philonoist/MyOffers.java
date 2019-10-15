package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyOffers extends AppCompatActivity {

    // BLA BALA BLA BLA

    final int POSTOFFER = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        FloatingActionButton fab = findViewById(R.id.fab_add);

        setTitle("My Offers");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_MyOffer);
        setSupportActionBar(toolbar);

        String[] myOffers = new String[]{"Offer #1", "Offer #2", "Offer #3"};

        final ListView listView = findViewById(R.id.lvOffers_MyOffers);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myOffers);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.getContext(), com.example.philonoist.postOffer.class);
                startActivityForResult(intent, POSTOFFER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == POSTOFFER && resultCode == Activity.RESULT_OK){
            Tuition tuition = (Tuition) data.getSerializableExtra("newTuition");
            Intent intent = new Intent();
            intent.putExtra("newTuition", tuition);
            setResult(Activity.RESULT_OK, intent);
            MyOffers.this.finish();
        }
    }
}
