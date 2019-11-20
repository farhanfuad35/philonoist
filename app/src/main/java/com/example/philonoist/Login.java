package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.DeviceRegistration;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.DeviceRegistrationResult;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText etPassword;
    EditText etEmail;
    Button btLogin;
    TextView tvSignup;
    TextView tvForgetpassword;
    CheckBox cbStayLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        setTitle("Login");
//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initializeGUIElements();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));



        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSignup.setTextColor(Color.BLUE);
                Intent intent = new Intent(view.getContext(),com.example.philonoist.SignUp.class);
                startActivity(intent);
            }
        });

        tvForgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvForgetpassword.setTextColor(Color.BLUE);
                String useremail = etEmail.getText().toString().toLowerCase().trim();
                Backendless.UserService.restorePassword( useremail, new AsyncCallback<Void>()
                {
                    public void handleResponse( Void response )
                    {
                        Toast.makeText(getApplicationContext(), "Password has been sent to your email.", Toast.LENGTH_SHORT).show();
                        // Backendless has completed the operation - an email has been sent to the user
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // password revovery failed, to get the error code call fault.getCode()
                    }
                });

            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btLogin.setText("Logging in...");
                final String email = etEmail.getText().toString().toLowerCase();
                final String password = etPassword.getText().toString();
                //final Boolean stayLoggedIn = cbStayLoggedIn.isChecked();
                final Boolean stayLoggedIn = true;

                Login(email, password, stayLoggedIn);



                Log.i("deviceid","hewweoewhi");
//                BackendlessAPIMethods.Login(getApplicationContext(), email, password, stayLoggedIn, getApplicationContext().getAct);

            }
        });


    }




    private void Login(final String email, final String password, Boolean stayLoggedIn)
    {
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                CONSTANTS.setCurrentUserEmail(email);
                CONSTANTS.setCurrentSavedUser(response);

                FileMethods.writes(getApplicationContext(), email);
                System.out.println("logged in "+email);

                Log.i("deviceid", "Login Successful");

                // If login successful, register user to the database with deviceID

                registerDeviceForNotification();

                Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                btLogin.setText("Login");

                Log.i("deviceid", fault.getMessage());
            }
        }, stayLoggedIn);
    }





    private void registerDeviceForNotification() {

        // Testing Backendless and FCM Service

        List<String> channels = new ArrayList<String>();
        channels.add( "default" );
        Backendless.Messaging.registerDevice(channels, new AsyncCallback<DeviceRegistrationResult>() {
            @Override
            public void handleResponse(DeviceRegistrationResult response) {
                Toast.makeText( Login.this, "Device registered!",
                        Toast.LENGTH_LONG).show();
                Log.i("deviceid", "Device registered successfully on backendless");

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText( Login.this, "Error registering " + fault.getMessage(),
                        Toast.LENGTH_LONG).show();

                Log.i("deviceid", fault.getMessage());
            }
        });

        Backendless.Messaging.getDeviceRegistration(new AsyncCallback<DeviceRegistration>() {
            @Override
            public void handleResponse(DeviceRegistration response) {

                BackendlessUser user = Backendless.UserService.CurrentUser();


                BackendlessAPIMethods.updateDeviceId(Login.this, user, response.getDeviceId());

//                BackendlessUser user = Backendless.UserService.CurrentUser();
//
//                Log.i("deviceid", "before get email");
//                Log.i("deviceid", "email of CurrentUser : " + user.getEmail());
//                Log.i("deviceid", "after get email");
//
//                user.setProperty( "device_id",response.getDeviceId() );
//
//                Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
//                {
//                    @Override
//                    public void handleResponse( BackendlessUser backendlessUser )
//                    {
//                        Log.i("deviceid", "User has been updated" );
//                        Log.i("deviceid", "Device ID - " + backendlessUser.getProperty( "device_id" ) );
//                    }
//                    @Override
//                    public void handleFault( BackendlessFault backendlessFault )
//                    {
//                        Log.i("deviceid", backendlessFault.getMessage());
//                    }
//                });

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }




    private void initializeGUIElements()
    {

        tvSignup = findViewById(R.id.tvLogin_createaccount);
        etEmail = findViewById(R.id.etLogin_email);
        etPassword = findViewById(R.id.etLogin_password);
        btLogin = findViewById(R.id.btnLogin_Login);
        tvForgetpassword = findViewById(R.id.tvLogin_ForgetPassword);
        cbStayLoggedIn = findViewById(R.id.cbLogin_StayLoggedIn);
    }

}
