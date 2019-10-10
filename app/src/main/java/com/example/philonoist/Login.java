package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText etPassword;
    EditText etEmail;
    Button btLogin;
    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BackendlessUser user = new BackendlessUser();

        setTitle("Login");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvSignup = findViewById(R.id.tvLogin_createaccount);
        etEmail = findViewById(R.id.etLogin_email);
        etPassword = findViewById(R.id.etLogin_password);
        btLogin = findViewById(R.id.btnLogin_Login);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSignup.setTextColor(Color.BLUE);
                Intent intent = new Intent(view.getContext(),com.example.philonoist.SignUp.class);
                startActivity(intent);
            }
        });



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Intent intent = new Intent(getApplicationContext(), com.example.philonoist.TuitionList.class);
                        startActivity(intent);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }, true);


            }
        });

    }
}
