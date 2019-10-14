package com.example.philonoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
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
    Button btnlocation;
    Button btnPost;

    String name;
    String _class;
    String salary;
    String subject1;
    //String subject2;
    //String subject3;
    String location;

    final int SELECT_LOCATION_INTENT_ID = 99;

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
        btnlocation = findViewById(R.id.btnPostoffer_location);
        btnPost = findViewById(R.id.btnpostoffer_Post);

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Select_Tuition_Location.class);
                startActivityForResult(intent, SELECT_LOCATION_INTENT_ID);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncOfferWithDatabase();
            }
        });



    }

    protected void syncOfferWithDatabase()
    {
        ArrayList<String> subjectString = new ArrayList<String>();


        _class = etClass.getText().toString().trim();
        salary = etsalary.getText().toString().trim();
        subject1 = etsubject1.getText().toString().trim();
        name = etName.getText().toString().trim();

//                if(!etsubject2.getText().toString().isEmpty()){
//                    subject2 = etsubject2.getText().toString().trim();
//                    subjectString.add(subject2);
//                }
//                if(!etsubject3.getText().toString().isEmpty()) {
//                    subject3 = etsubject3.getText().toString().trim();
//                    subjectString.add(subject3);
//                }
        saveNewOffer(_class, salary, subject1);




        postOffer.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == SELECT_LOCATION_INTENT_ID){
            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                syncOfferWithDatabase();

                }
            });
        }
    }


    public void saveNewOffer(String _class, String salary, String subject) {
        Offer newoffer = new Offer();
        newoffer.set_class(_class);
        newoffer.setSalary(salary);
        newoffer.setSubject(subject);
        final ArrayList<BackendlessUser> userlist = new ArrayList<>();
        BackendlessUser user = Backendless.UserService.CurrentUser();
        userlist.add(user);


        Backendless.Data.of(Offer.class).save(newoffer, new AsyncCallback<Offer>() {

            @Override
            public void handleResponse(Offer NewOffer) {
                Toast.makeText(getApplicationContext(), "STRING MESSAGE", Toast.LENGTH_LONG).show();
                // Log.i(TAG, "Order has been saved");
                 setRelation(NewOffer, userlist);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //Log.e(TAG, fault.getMessage());
            }
        });
    }

    private  void setRelation(Offer Newoffer, ArrayList<BackendlessUser>userList) {

        Backendless.Data.of(Offer.class).addRelation(Newoffer, "email", userList, new AsyncCallback<Integer>(){
            @Override
            public void handleResponse(Integer response) {
                Log.i("setRelation", "Relation has been set");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("SetRelation", "Relation wasn't set");
            }
        });


    }
}
