package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.push.DeviceRegistrationResult;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

        BackendlessUser user = new BackendlessUser();

//        setTitle("Login");
//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));


        tvSignup = findViewById(R.id.tvLogin_createaccount);
        etEmail = findViewById(R.id.etLogin_email);
        etPassword = findViewById(R.id.etLogin_password);
        btLogin = findViewById(R.id.btnLogin_Login);
        tvForgetpassword = findViewById(R.id.tvLogin_ForgetPassword);
        cbStayLoggedIn = findViewById(R.id.cbLogin_StayLoggedIn);

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
                final Boolean stayLoggedIn = cbStayLoggedIn.isChecked();

                registerDeviceForNotification();
                Login(email, password, stayLoggedIn);
//                BackendlessAPIMethods.Login(getApplicationContext(), email, password, stayLoggedIn, getApplicationContext().getAct);


            }
        });


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
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText( Login.this, "Error registering " + fault.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Login(final String email, final String password, Boolean stayLoggedIn)
    {
        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                CONSTANTS.setCurrentUserEmail(email);

                FileMethods.writes(getApplicationContext(), email);
                System.out.println("logged in "+email);

                Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                btLogin.setText("Login");
            }
        }, stayLoggedIn);
    }
}
