package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Backendless.setUrl(backendless_credentials.SERVER_URL);
        Backendless.initApp(getApplicationContext(), backendless_credentials.APPLICATION_ID, backendless_credentials.API_KEY);

    //        Intent intent = new Intent(this, TuitionList.class);
    //        startActivity(intent);


        Intent intent = new Intent(this, Splash_Screen.class);
        startActivity(intent);
            
    }

}
