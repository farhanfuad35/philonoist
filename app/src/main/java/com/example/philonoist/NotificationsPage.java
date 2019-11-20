package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class NotificationsPage extends AppCompatActivity {
    ListView lvNotificationsList;
    NotificationListAdapter notificationListAdapter;
    List<Notifications> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_page);
        setTitle("Notifications");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_Notifications);
        setSupportActionBar(toolbar);

        lvNotificationsList = findViewById(R.id.lvNotificationsList);

        String loggedInUserEmail = FileMethods.load(getApplicationContext()).trim();

        final DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create();
        String whereClause = "user_email = '" + loggedInUserEmail + "'";
        dataQueryBuilder.setWhereClause(whereClause);
        dataQueryBuilder.addProperty("teacher_email");
        dataQueryBuilder.addProperty("user_email");
        dataQueryBuilder.addProperty("student_name");
        dataQueryBuilder.addProperty("offerID");
        dataQueryBuilder.addProperty("message");
        dataQueryBuilder.addProperty("created");
        dataQueryBuilder.setSortBy("created");

        Backendless.Data.of(Notifications.class).find(dataQueryBuilder, new AsyncCallback<List<Notifications>>() {
            @Override
            public void handleResponse(List<Notifications> response) {
                notifications = response;
                notificationListAdapter = new NotificationListAdapter(getApplicationContext(), notifications);
                lvNotificationsList.setAdapter(notificationListAdapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        lvNotificationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(notifications.get(i).getTeacher_email().equals("Null")){
                    DataQueryBuilder dataQuery = DataQueryBuilder.create();
                    String whereClause = "objectId = '" + notifications.get(i).getOfferID() + "'";
                    dataQuery.setWhereClause(whereClause);
                    dataQuery.addProperty("subject");
                    dataQuery.addProperty("name");
                    dataQuery.addProperty("salary");
                    dataQuery.addProperty("_class");
                    dataQuery.addProperty("objectId");
                    dataQuery.addProperty("remarks");
                    dataQuery.addProperty("contact");
                    dataQuery.addProperty("location");
                    dataQuery.addProperty("active");


                    Backendless.Data.of(Offer.class).find(dataQuery, new AsyncCallback<List<Offer>>() {
                        @Override
                        public void handleResponse(List<Offer> response) {

                            Intent intent = new Intent(getApplicationContext(), TuitionDetails.class);
                            intent.putExtra("offer", response.get(0));
                            startActivity(intent);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getApplicationContext(), "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{

                    DataQueryBuilder dataQueryBuilder2 = DataQueryBuilder.create();
                    String whereClause = "email = '" + notifications.get(i).getTeacher_email() +"'";
                    dataQueryBuilder2.setWhereClause(whereClause);
                    dataQueryBuilder2.addProperty("email");
                    dataQueryBuilder2.addProperty("first_name");
                    dataQueryBuilder2.addProperty("last_name");
                    dataQueryBuilder2.addProperty("year");
                    dataQueryBuilder2.addProperty("department");
                    dataQueryBuilder2.addProperty("contact_no");
                    dataQueryBuilder2.addProperty("device_id");
                    dataQueryBuilder2.addProperty("lastLogin");


                    Backendless.Data.of(BackendlessUser.class).find(dataQueryBuilder2, new AsyncCallback<List<BackendlessUser>>() {
                        @Override
                        public void handleResponse(List<BackendlessUser> response) {

                            Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                            intent.putExtra("user", response.get(0));
                            intent.putExtra("offerID", notifications.get(i).getOfferID());
                            startActivity(intent);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getApplicationContext(), "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }
}
