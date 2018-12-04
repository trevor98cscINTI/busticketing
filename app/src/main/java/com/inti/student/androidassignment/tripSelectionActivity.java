package com.inti.student.androidassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class tripSelectionActivity extends AppCompatActivity {
    ArrayList<Schedule> scheduleList;
    ArrayList<Route> routeList;
    TextView date;
    TextView location;
    TextView noTrip;
    RecyclerView recyclerView;
    TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_selection);
        setTitle("Trip Selection Details");

        date = findViewById(R.id.IDTripDate);
        location = findViewById(R.id.IDTripLocation);
        noTrip = findViewById(R.id.noTripID);

        Bundle bdl = getIntent().getExtras();
        scheduleList =  bdl.getParcelableArrayList("scheduleList");
        routeList = bdl.getParcelableArrayList("routeList");
        Log.i("RSD", routeList.toString());
        for(Route r1 : routeList)
        {
            Log.i("PRINTING", r1.toString());
        }
        if(scheduleList.isEmpty())
        {
            Log.i("DS", "EMPTYY");
            Toast.makeText(this, "There is no such trip on the date", Toast.LENGTH_LONG).show();
            noTrip.setVisibility(View.VISIBLE);
            date.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
           // Intent i = new Intent(this, TestActivity.class);
          //  startActivity(i);
        }
        else
            {
                Log.i("RFS", Integer.toString(scheduleList.size()));
            Log.i("DS", scheduleList.get(0).toString());
            date.setText("Journey Date : " + scheduleList.get(0).getScheduleDate());
            location.setText("Boarding Location : " + routeList.get(0).getBoardingLocation() + " To Destination : " + routeList.get(0).getDestinationLocation() );
        }


        recyclerView = findViewById(R.id.rvOfTripList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tripAdapter = new TripAdapter(this, scheduleList, routeList);
        tripAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tripAdapter);



    }
}
