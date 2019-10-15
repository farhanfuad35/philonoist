package com.example.philonoist;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.backendless.Backendless;
        import com.backendless.BackendlessUser;
        import com.backendless.async.callback.AsyncCallback;
        import com.backendless.exceptions.BackendlessFault;
        import com.backendless.persistence.DataQueryBuilder;
        import com.backendless.persistence.LoadRelationsQueryBuilder;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.List;

public class MyOffers extends AppCompatActivity {

    public boolean flag = true;

    final int POSTOFFER = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offers);

        FloatingActionButton fab = findViewById(R.id.fab_add);

        setTitle("My Offers");
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_MyOffer);
        setSupportActionBar(toolbar);


        final List<String> myOffers = new ArrayList<>();

        final List<Offer>  returnlist = new ArrayList<>();

//        final AsyncCallback<List<Offer>> callback = new AsyncCallback<List<Offer>>() {
//            @Override
//            public void handleResponse(List <Offer> offerList) {
//                for (Offer offer : offerList) {
//                    Toast.makeText(getApplicationContext(),"Hello Javatpoint1",Toast.LENGTH_SHORT).show();
//                    System.out.println(offer.getSalary());
//                    returnlist.add(offer);
//                    // System.out.println(returnlist.size());
//                }
//
//            }
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                // if there is any fault
//            }
//        };
//
//        final DataQueryBuilder dataQuery = DataQueryBuilder.create();
//        dataQuery.addRelated("email");
//        dataQuery.addProperty("subject");
//
//        Backendless.Data.of(Offer.class).getObjectCount(new AsyncCallback<Integer>() {
//
//            @Override
//            public void handleResponse(Integer count) {
//                Backendless.Data.of(Offer.class).find(dataQuery,callback);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                //Log.e(TAG, fault.getMessage());
//            }
//
//        });

        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.addRelated("email");
        dataQuery.addProperty("subject");
        final ListView listView = findViewById(R.id.lvOffers_MyOffers);

        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myOffers);

        Backendless.Data.of(Offer.class).find(dataQuery, new AsyncCallback<List<Offer>>() {
            @Override
            public void handleResponse(List<Offer> offerList) {
                for (Offer offer : offerList) {
//                    Toast.makeText(getApplicationContext(),"Hello Javatpoint1",Toast.LENGTH_SHORT).show();
//                    System.out.println(offer.getSalary());
//                    returnlist.add(offer);
                    // System.out.println(returnlist.size());

                    myOffers.add(offer.getSubject());
                    Log.i("subject", "in loop " + Integer.toString(myOffers.size()));



                    listView.setAdapter(listViewAdapter);


                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        Log.i("subject", Integer.toString(myOffers.size()));

//        for(Offer offer:returnlist){
//            myOffers.add(offer.getSubject());
//            Log.i("subject", offer.getSubject());
//        }





        if(myOffers.size() == 0){
            myOffers.add("bangla");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listView.getContext(), com.example.philonoist.postOffer.class);
                startActivityForResult(intent, POSTOFFER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == POSTOFFER && resultCode == Activity.RESULT_OK){
            Tuition tuition = (Tuition) data.getSerializableExtra("newTuition");
            Intent intent = new Intent();
            intent.putExtra("newTuition", tuition);
            setResult(Activity.RESULT_OK, intent);
            MyOffers.this.finish();
        }
    }


    /* private List<Offer> loadRelationsAsync() {
        final List<Offer>  returnlist = new ArrayList<>();
         final AsyncCallback<List<Offer>> callback = new AsyncCallback<List<Offer>>() {
            List <Offer> offerList = new ArrayList<>();
            @Override
            public void handleResponse(List <Offer> offerList) {
                for (Offer offer : offerList) {
                    Toast.makeText(getApplicationContext(),"Hello Javatpoint1",Toast.LENGTH_SHORT).show();
                     //System.out.println(offer.getSalary());
                     returnlist.add(offer);
                    // System.out.println(returnlist.size());
                }

            }
            @Override
            public void handleFault(BackendlessFault fault) {
               // if there is any fault
            }
        };

         System.out.println(returnlist.size());
        final DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.addRelated("email");

        Backendless.Data.of(Offer.class).getObjectCount(new AsyncCallback<Integer>() {

            @Override
            public void handleResponse(Integer count) {
                Backendless.Data.of(Offer.class).find(dataQuery,callback);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //Log.e(TAG, fault.getMessage());
            }

        });
        return returnlist;
    }*/





}
