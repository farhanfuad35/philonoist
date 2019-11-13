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

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        TextView tvChar = findViewById(R.id.tvUserinfo_char);
        TextView tvName = findViewById(R.id.tvUserinfo_name);
        TextView tvRegistrationNumber = findViewById(R.id.tvUserinfo_registrationNumber);
        TextView tvEmail = findViewById(R.id.tvUserinfo_email);
        Button btnAccept = findViewById(R.id.btn_accept);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnCall = findViewById(R.id.btnUserInfo_call);
        Button btnMail = findViewById(R.id.btnUserInfo_mail);


        final BackendlessUser user = (BackendlessUser) getIntent().getSerializableExtra("user");
        final String offerID = getIntent().getStringExtra("offerID");
        //Log.i("offerIDinUserInfo", offerID);

        final String firstName = (String) user.getProperty("first_name");
        final String lastName = (String) user.getProperty("last_name");
        final String email = user.getEmail();
        Log.i("userEmailCheck", email);
        tvChar.setText(firstName.toUpperCase().charAt(0) + "");
        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(user.getEmail());
        tvRegistrationNumber.setText((String)user.getProperty("registration_no"));


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
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, email);
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
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getApplicationContext(), "Error" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo.this.finish();
            }
        });

//        BackendlessUser user = Backendless.UserService.CurrentUser();
//        String reg_no = (String)user.getProperty("registration_no");
//        EditText registration = (EditText) findViewById(R.id.etUserinfo_registrationnNumber);
//        registration.setText(reg_no);
//
//        String first_name = (String)user.getProperty("first_name");
//        EditText firstname = (EditText)findViewById(R.id.etUserinfo_firstname);
//        firstname.setText(first_name);
//
//        String last_name = (String)user.getProperty("last_name");
//        EditText lastname = (EditText)findViewById(R.id.etUserinfo_lastname);
//        lastname.setText(last_name);
//
//
//        String email_ = (String)user.getProperty("email");
//        EditText email = (EditText)findViewById(R.id.etUserinfo_email);
//        email.setText(email_);





    }
}
