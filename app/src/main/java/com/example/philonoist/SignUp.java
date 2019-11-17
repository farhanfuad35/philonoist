package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import static com.backendless.rt.RTTypes.log;

public class SignUp extends AppCompatActivity {

    EditText etContactNo;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassWord;
    EditText etConfirmPassword;
    EditText etDepartment;
    EditText etYear;
    Button btSignup;

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_SignUp);
        setSupportActionBar(toolbar);

        etContactNo = findViewById(R.id.etSignup_contactNumber);
        etFirstName = findViewById(R.id.etSignup_firstname);
        etLastName = findViewById(R.id.etSignup_lastname);
        etEmail = findViewById(R.id.etSignup_email);
        etPassWord = findViewById(R.id.etSignup_password);
        etConfirmPassword = findViewById(R.id.etSignup_confirmpassword);
        etDepartment = findViewById(R.id.etSignup_department);
        etYear = findViewById(R.id.etSignup_year);
        btSignup = findViewById(R.id.btnSignup_createAccount);

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactNo = etContactNo.getText().toString().trim();
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().toLowerCase().trim();
                String password = etPassWord.getText().toString();
                String confirmPass = etConfirmPassword.getText().toString();
                String department = etDepartment.getText().toString();
                String year = etYear.getText().toString();

                boolean hasError = false;

                if(contactNo.length() != 11){
                    hasError = true;
                    etContactNo.setError("Please type your valid Contact Number");
                }
                if(!isEmailValid(email)) {
                    hasError = true;
                    etEmail.setError("Please enter a valid e-mail address");
                }
                if(password.length() < 3) {
                    hasError = true;
                    etPassWord.setError("Password should be at least 3 characters");
                }
                if(!password.equals(confirmPass)){
                    hasError = true;
                    etConfirmPassword.setError("The passwords don't match");
                }
                if(!hasError){
                    BackendlessUser user = new BackendlessUser();
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setProperty("first_name", firstName);
                    user.setProperty("last_name", lastName);
                    user.setProperty("contact_no", contactNo);
                    user.setProperty("department", department);
                    user.setProperty("year",year);


                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(getApplicationContext(), "SignUp Successfull!", Toast.LENGTH_SHORT).show();
                            SignUp.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getApplicationContext(), "SignUp Failed " , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
