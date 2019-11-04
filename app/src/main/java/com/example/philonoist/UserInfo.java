package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Script;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        TextView tvName = findViewById(R.id.tvUserinfo_name);
        TextView tvRegistrationNumber = findViewById(R.id.tvUserinfo_registrationnNumber);
        TextView tvEmail = findViewById(R.id.tvUserinfo_email);
        Button btnAccept = findViewById(R.id.btn_accept);
        Button btnCancel = findViewById(R.id.btn_cancel);



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
