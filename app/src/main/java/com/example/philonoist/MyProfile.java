package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import org.w3c.dom.Text;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setTitle("My Profile");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_MyProfile);
        setSupportActionBar(toolbar);


        String contact_no;
        String first_name;
        String last_name;
        String name;
        String email;
        String character;
        String department;
        String year;



        TextView tvName = findViewById(R.id.tvMyProfile_name);
        TextView tvEmail = findViewById(R.id.tvMyProfile_email);
        TextView tvContact = findViewById(R.id.tvMyProfile_Contact);
        TextView tvChar = findViewById(R.id.tvMyProfile_char);
        TextView tvDepartment = findViewById(R.id.tvMyProfile_department);
        TextView tvYear = findViewById(R.id.tvMyProfile_year);

        BackendlessUser user = Backendless.UserService.CurrentUser();
        contact_no = (String)user.getProperty("contact_no");
        tvContact.setText(contact_no);

        first_name = (String)user.getProperty("first_name");
        last_name = (String)user.getProperty("last_name");

        name = first_name + " " + last_name;
        tvName.setText(name);

        email = (String)user.getProperty("email");
        tvEmail.setText(email);


        character = ""+name.charAt(0)+"";
        tvChar.setText(character);

        department = (String)user.getProperty("department");
        tvDepartment.setText(department);

        year = (String)user.getProperty("year");
        tvYear.setText(year);
    }
}
