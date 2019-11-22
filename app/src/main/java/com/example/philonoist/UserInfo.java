package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Arrays;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setTitle("Candidate Details");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_Userinfo);
        setSupportActionBar(toolbar);

        TextView tvChar = findViewById(R.id.tvUserinfo_char);
        TextView tvName = findViewById(R.id.tvUserinfo_name);
        TextView tvYear = findViewById(R.id.tvUserinfo_year);
        TextView tvDepartment = findViewById(R.id.tvUserinfo_department);
        Button btnAccept = findViewById(R.id.btn_accept);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnCall = findViewById(R.id.btnUserInfo_call);
        Button btnMail = findViewById(R.id.btnUserInfo_mail);


        final BackendlessUser user = (BackendlessUser) getIntent().getSerializableExtra("user");
        final String offerID = getIntent().getStringExtra("offerID");
        //Log.i("offerIDinUserInfo", offerID);

        final String firstName = (String) user.getProperty("first_name");
        final String lastName = (String) user.getProperty("last_name");
        final String name = firstName + " " + lastName;
        final String email = user.getEmail();
        Log.i("userEmailCheck", email);




        tvChar.setText(firstName.toUpperCase().charAt(0) + "");

        tvName.setText(name);

        tvDepartment.setText((String)user.getProperty("department"));

        tvYear.setText((String)user.getProperty("year") + " year");


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + "01521526360";//user.getContact();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] TO = {email};
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                startActivity(Intent.createChooser(intent, "Send mail to "+firstName + " " + lastName));
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Offer offer = new Offer();
                int index = -1;
                for(int i=0; i<CONSTANTS.offers.size(); i++){
                    if(CONSTANTS.offers.get(i).getObjectId().equals(offerID)){
                        Log.i("offerFound", "Offer found in index:" +i);
                        offer = CONSTANTS.offers.get(i);
                        index = i;
                        break;
                    }
                }

                if(index == -1){
                    Toast.makeText(getApplicationContext(), "Server Error!...Please try again", Toast.LENGTH_SHORT).show();
                    Log.i("OfferIdNotFound", "Offer ID not found Error!!!");
                    System.out.println(CONSTANTS.offers.size());
                }else{
                    CONSTANTS.offers.get(index).setActive(false);
                    Backendless.Persistence.save(CONSTANTS.offers.get(index), new AsyncCallback<Offer>() {
                        @Override
                        public void handleResponse(Offer response) {
                            Toast.makeText(getApplicationContext(), "Teacher Accepted!",Toast.LENGTH_SHORT ).show();



                                String message = (String)Backendless.UserService.CurrentUser().getProperty("first_name") + " " + (String)Backendless.UserService.CurrentUser().getProperty("last_name")+ " has accepted you as teacher. Check the Notification Page";
                                DeliveryOptions deliveryOptions = new DeliveryOptions();
                                deliveryOptions.setPushSinglecast(Arrays.asList((String) user.getProperty("device_id")));
                                PublishOptions publishOptions = new PublishOptions();
                                publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
                                publishOptions.putHeader("android-content-title", "You have been accepted as a teacher!");
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



                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getApplicationContext(), "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    saveNewNotification(email, offerID, offer.getName());
                }

                setResult(RESULT_OK);
                //UserInfo.this.finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo.this.finish();
            }
        });
    }

    public void saveNewNotification(String user_email, String offerID, String studentName){
        Notifications notifications = new Notifications();
        notifications.setUser_email(user_email);
        notifications.setMessage("Your request to teach " + studentName +" has been accepted!");
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
