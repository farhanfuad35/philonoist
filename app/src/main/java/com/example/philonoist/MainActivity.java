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

import com.backendless.Backendless;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.setUrl(backendless_credentials.SERVER_URL);
        Backendless.initApp(getApplicationContext(), backendless_credentials.APPLICATION_ID, backendless_credentials.API_KEY);

        if(Backendless.UserService.loggedInUser()== "") {
            Intent intent = new Intent(this, com.example.philonoist.Login.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, com.example.philonoist.TuitionList.class);
            startActivity(intent);
        }
            
    }

}
