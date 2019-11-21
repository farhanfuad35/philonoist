package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.DeviceRegistration;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuitionDetails extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;
    private String[] subjects;
    private TextView hostName;
    private TextView salary;
    private Offer offer;
    private Button btnMap;
    private String lat;
    private String lng;
    private String notficationemail;
    private String deviceid;
    private Button btnInterested;
    private Button btnCandidates;
    private Button btnCall;
    private TextView tvRemarksContent;
    private TextView tvAssigned;
    private String loggedInUserEmail;
    private String offerPostedByEmail;
    public  int interestedUserID;
    private int callerActivityID;
    final int candidatesList = 49;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == candidatesList){
            if(resultCode == RESULT_OK){
                tvAssigned.setVisibility(View.VISIBLE);
                btnCandidates.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_details);
        setTitle("Tuition Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_TuitionDetails);
        setSupportActionBar(toolbar);

        initializeFields();

        btnInterested.setVisibility(View.GONE);
        btnCandidates.setVisibility(View.GONE);
        tvAssigned.setVisibility(View.GONE);
        tvRemarksContent.setVisibility(View.GONE);
        btnCall.setVisibility(View.GONE);
        btnMap.setVisibility(View.GONE);



        callerActivityID = (int) getIntent().getIntExtra("ID", 0);          // From Maps, ID = 65 | From List, ID = 75
        offer = (Offer) getIntent().getSerializableExtra("offer");
//        lat = getIntent().getStringExtra("lat");
//        lng = getIntent().getStringExtra("lng");

        Log.i("newoffer", "check next line");
        Log.i("newoffer", offer.getSubject());
        Log.i("newoffer", offer.getLocation().getLatitude().toString());

        if(callerActivityID == CONSTANTS.getActivityIdTuitionlist()) {

            Log.i("latlng", "On tuitiondetails, lat = " + offer.getLocation().getLatitude().toString());


            lat = (String) offer.getLocation().getLatitude().toString();
            lng = (String) offer.getLocation().getLongitude().toString();
        }
        else if(callerActivityID == CONSTANTS.getActivityIdMapsShowTuitions()){
            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("lng");
        }


        if(offer.isActive()){
            Log.i("activeStatus", "active True");
        }else{
            Log.i("activeStatus", "active False");
            tvAssigned.setVisibility(View.VISIBLE);
        }

        final int index = getIntent().getIntExtra("index", 0);

        getEmailFromUsersTable();

        setFieldValues();

        btnInterested.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String offerID = offer.getObjectId();
                DataQueryBuilder dataQuery = DataQueryBuilder.create();
                System.out.println(notficationemail);
                String whereClause = "email = '" + notficationemail+ "'";
                dataQuery.setWhereClause(whereClause);

                Backendless.Data.of(BackendlessUser.class).find(dataQuery,new AsyncCallback<List<BackendlessUser>>() {
                    @Override
                    public void handleResponse(List<BackendlessUser> users) {
                        deviceid = (String)users.get(0).getProperty("device_id");




                        String message = (String)Backendless.UserService.CurrentUser().getProperty("first_name") + (String)Backendless.UserService.CurrentUser().getProperty("last_name")+ " has applied for a tuition you posted. Check the Notification Page";
                        DeliveryOptions deliveryOptions = new DeliveryOptions();
                        deliveryOptions.setPushSinglecast(Arrays.asList(deviceid));
                        PublishOptions publishOptions = new PublishOptions();
                        publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
                        publishOptions.putHeader("android-content-title", "New Candidate!!!");
                        publishOptions.putHeader("android-content-text", "Push Notifications are cool");
                        Backendless.Messaging.publish(message, publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
                            @Override
                            public void handleResponse(MessageStatus response) {
                                System.out.println("Hello there you are here");
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });



                        System.out.print("this is device id " + deviceid);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });


//                Backendless.Messaging.getDeviceRegistration(new AsyncCallback<DeviceRegistration>() {
//                    @Override
//                    public void handleResponse(DeviceRegistration response) {
//
//                        System.out.println(response.getDeviceId());
//                        DeliveryOptions deliveryOptions = new DeliveryOptions();
//                        deliveryOptions.setPushSinglecast(Arrays.asList(response.getDeviceId()));
//                        PublishOptions publishOptions = new PublishOptions();
//                        publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
//                        publishOptions.putHeader("android-content-title", "This is a notification title");
//                        publishOptions.putHeader("android-content-text", "Push Notifications are cool");
//                        Backendless.Messaging.publish("this is a message!", publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
//                            @Override
//                            public void handleResponse(MessageStatus response) {
//
//                            }
//
//                            @Override
//                            public void handleFault(BackendlessFault fault) {
//
//                            }
//                        });
//
//
//
//                    }
//
//                    @Override
//                    public void handleFault(BackendlessFault fault) {
//
//                    }
//                });
                String userEmail = FileMethods.load(getApplicationContext());
                //String userEmail = Backendless.UserService.CurrentUser().getEmail();
                saveNewApplicant(offerPostedByEmail, loggedInUserEmail, offerID);
                //saveNewNotification(offerPostedByEmail, loggedInUserEmail, offerID);
            }
        });

        btnCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuitionDetails.this, CandidateList.class);
                intent.putExtra("offerID", offer.getObjectId());
                intent.putExtra("index", index);
                //startActivity(intent);
                startActivityForResult(intent, candidatesList);
            }
        });


        Log.i("location", "entering button click location");
        //Log.i("location", offer.getLocation().getLatitude().toString());

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("location", "came to location");

                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: ,0"));

                Log.i("location", "lattitude = " + lat + "longitude = " + lng);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 0,0?q=" + lat + ", " + lng));
                startActivity(intent);
            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + offer.getContact()));
                startActivity(intent);
            }
        });


    }

    private void setFieldValues(){
        salary.setText(offer.getSalary());
        //subjects = new String[]{offer.getSubject()};            // Cannot be Null

        // TODO

        //subjects = processSubjectString(offer.getSubject());                      // Returns a string of subjects processed from the single line fetched from the database
        subjects = FileMethods.processSubjectString(offer.getSubject());
        //Log.i("subjects", "After split :\t" + subjects[1]);

        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(listViewAdapter);
        tvRemarksContent.setText(offer.getRemarks());
        tvRemarksContent.setVisibility(View.VISIBLE);
        hostName.setText(offer.getName());
    }

    private void initializeFields(){
        listView = findViewById(R.id.lvDetails_Subject);
        hostName = findViewById(R.id.tvDetails_hostName);
        salary = findViewById(R.id.tvDetails_salaryNumber);
        btnInterested = findViewById(R.id.btnInterested);
        btnCandidates = findViewById(R.id.btnCandidates);
        btnMap = findViewById(R.id.btnTuitionDetails_map);
        btnCall = findViewById(R.id.btnTuitionDetails_call);
        tvRemarksContent = findViewById(R.id.tvDetails_remarksContent);
        tvAssigned = findViewById(R.id.tvAssigned);
    }

    private LoadRelationsQueryBuilder prepareLoadRelationQuery(String relationFieldName)
    {
        LoadRelationsQueryBuilder<BackendlessUser> loadRelationsQueryBuilder;
        loadRelationsQueryBuilder = LoadRelationsQueryBuilder.of( BackendlessUser.class );
        loadRelationsQueryBuilder.setRelationName( relationFieldName );

        return loadRelationsQueryBuilder;
    }


    private void getEmailFromUsersTable(){
        LoadRelationsQueryBuilder loadRelationsQueryBuilder = prepareLoadRelationQuery("email");
        Backendless.Data.of("Offer").loadRelations(offer.getObjectId(), loadRelationsQueryBuilder, new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> users) {
                String email = (String) users.get(0).getEmail().trim();
                notficationemail = email;
                offerPostedByEmail = (String) users.get(0).getEmail().trim();
                loggedInUserEmail = FileMethods.load(getApplicationContext()).trim();
                //loggedInUserEmail = Backendless.UserService.CurrentUser().getEmail();

                if(loggedInUserEmail.equals(offerPostedByEmail)) {
                    System.out.println("loaded email "+loggedInUserEmail);
                    System.out.println("offer posted by "+offerPostedByEmail);
                    btnCall.setVisibility(View.GONE);
                    btnMap.setVisibility(View.GONE);
                    if(offer.isActive()){
                        btnCandidates.setVisibility(View.VISIBLE);
                    }
                }else{
                    System.out.println("else loaded email "+loggedInUserEmail);
                    System.out.println("else offer posted by "+offerPostedByEmail);
                    if(offer.isActive()){
                        btnCall.setVisibility(View.VISIBLE);
                        btnMap.setVisibility(View.VISIBLE);
                        btnInterested.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("relation query", "relation query error " + fault.getMessage());
            }
        });
    }

    private  void setRelation(final Applicants applicants, ArrayList<BackendlessUser>userList) {

        Backendless.Data.of(Applicants.class).addRelation(applicants, "email", userList, new AsyncCallback<Integer>(){
            @Override
            public void handleResponse(Integer response) {
                Log.i("addRelation", "Relation has been set");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });


    }

    public void saveNewApplicant(final String user_email, final String teacher_email, final String offerID) {
        Applicants applicants = new Applicants();
        String ID = teacher_email+offerID;
        applicants.setOfferID(offerID);
        applicants.setEmail(teacher_email);
        applicants.setID(ID);


        final ArrayList<BackendlessUser> userlist = new ArrayList<>();
        BackendlessUser user = Backendless.UserService.CurrentUser();
        userlist.add(user);
        Backendless.Data.of(Applicants.class).save(applicants, new AsyncCallback<Applicants>() {

            @Override
            public void handleResponse(Applicants applicants1) {
                saveNewNotification(user_email, teacher_email, offerID);
                Toast.makeText(getApplicationContext(), "Your Application Submitted!", Toast.LENGTH_LONG).show();
                setRelation(applicants1, userlist);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(TuitionDetails.this, "You have already applied for this tuition!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveNewNotification(String user_email, String teacher_email, String offerID){

        BackendlessUser user = Backendless.UserService.CurrentUser();
        String firstName = (String) user.getProperty("first_name");
        String lastName = (String) user.getProperty("last_name");
        String name = firstName + " " + lastName;
        Log.i("userName!!", name);

        Notifications notifications = new Notifications();
        notifications.setUser_email(user_email);
        notifications.setTeacher_email(teacher_email);
        notifications.setMessage(name + " has applied for your tuition offer");
        notifications.setOfferID(offerID);

        Backendless.Data.of(Notifications.class).save(notifications, new AsyncCallback<Notifications>() {
            @Override
            public void handleResponse(Notifications response) {
                Log.i("notification", "notification saved in database");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("notification", "notification NOT!!! saved in database");
            }
        });
    }

}
