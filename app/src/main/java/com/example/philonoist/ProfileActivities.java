package com.example.philonoist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;
import java.util.zip.Inflater;

public class ProfileActivities extends AppCompatActivity {
    
    final int MYOFFER = 55;
    final int profileActivitiesRequestCode = -9898;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activities);

        setTitle("Profile Activities");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_ProfileActivities);
        setSupportActionBar(toolbar);

        String[] options = new String[]{"My Offers", "Notifications", "Candidate List", "User Info"};

        ListView listView = findViewById(R.id.lvProfAct);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.MyOffers.class);
                    startActivityForResult(intent, MYOFFER);
                }
                if(position == 1){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.Notifications.class);
                    startActivity(intent);
                }
                if(position == 2){
                    Intent intent = new Intent(view.getContext(), com.example.philonoist.CandidateList.class);
                    startActivity(intent);
                }

                if(position == 3){
                    Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                    startActivity(intent);
                }


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuGeneral_Logout){
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Toast.makeText(getApplicationContext(), "You are successfully logged out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(getApplicationContext(), "Sorry couldn't logout right now. Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_general, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(ProfileActivities.this, "yes", Toast.LENGTH_SHORT).show();

        if(requestCode == MYOFFER){
            if(resultCode == RESULT_OK){
                //Toast.makeText(ProfileActivities.this, "yes came here", Toast.LENGTH_SHORT).show();
                Offer tuition = (Offer) data.getSerializableExtra("newTuition");
                Intent intent = new Intent();
                intent.putExtra("newTuition", tuition);
                setResult(Activity.RESULT_OK, intent);
                ProfileActivities.this.finish();
            }
        }
    }
}
