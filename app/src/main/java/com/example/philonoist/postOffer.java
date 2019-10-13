package com.example.philonoist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

public class postOffer extends AppCompatActivity {

    EditText etName;
    EditText etClass;
    EditText etsalary;
    EditText etsubject1;
    EditText etsubject2;
    EditText etsubject3;
    EditText etlocation;
    Button btnPost;

    String name;
    String _class;
    String salary;
    String subject1;
    String subject2;
    String subject3;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        setTitle("Post Offer");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_PostOffer);
        setSupportActionBar(toolbar);

        etName = findViewById(R.id.etpostoffer_name);
        etClass = findViewById(R.id.etpostoffer_class);
        etsalary = findViewById(R.id.etpostoffer_salary);
        etsubject1 = findViewById(R.id.etpostoffer_Subject1);
        etsubject2 = findViewById(R.id.etpostoffer_Subject2);
        etsubject3 = findViewById(R.id.etpostoffer_Subject3);
        etlocation = findViewById(R.id.etpostoffer_location);
        btnPost = findViewById(R.id.btnpostoffer_Post);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ArrayList<String> subjectString = new ArrayList<String>();


                _class = etClass.getText().toString().trim();
                salary = etsalary.getText().toString().trim();
                subject1 = etsubject1.getText().toString().trim();
                location = etlocation.getText().toString().trim();
                name = etName.getText().toString().trim();

                if(!etsubject2.getText().toString().isEmpty()){
                    subject2 = etsubject2.getText().toString().trim();
                    subjectString.add(subject2);
                }
                if(!etsubject3.getText().toString().isEmpty()) {
                    subject3 = etsubject3.getText().toString().trim();
                    subjectString.add(subject3);
                }
                saveNewOffer(_class,salary,subject1);

                Tuition tuition = new Tuition(name, salary, subjectString, location);

                intent.putExtra("newTuition", tuition);
                setResult(Activity.RESULT_OK, intent);

                postOffer.this.finish();


            }
        });

    }

    public void saveNewOffer(String _class, String salary, String subject)
    {
        Offer newoffer = new Offer();
        newoffer.set_class(_class);
        newoffer.setSalary( salary );
        newoffer.setSubject( subject );

        Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
        // save object synchronously
        Offer savedContact = Backendless.Persistence.save( newoffer );

        // save object asynchronously
        Backendless.Persistence.save( newoffer, new AsyncCallback<Offer>() {
            public void handleResponse( Offer response )
            {
                Toast.makeText(getApplicationContext(),"handle response",Toast.LENGTH_SHORT).show();
                // new Contact instance has been saved
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(getApplicationContext(),"fault response",Toast.LENGTH_SHORT).show();
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }

}
