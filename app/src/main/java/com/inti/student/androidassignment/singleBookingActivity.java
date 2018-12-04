package com.inti.student.androidassignment;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class singleBookingActivity extends AppCompatActivity {
    TextView mDate;
    TextView mBoardingT;
    TextView mBoardingL;
    TextView mDestinationT;
    TextView mDestinationL;
    String scheduleID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_booking);
        setTitle("Booking Detail");
        mDate = findViewById(R.id.sDate);
        mBoardingL = findViewById(R.id.sBoardL);
        mBoardingT = findViewById(R.id.sBoardingT);
        mDestinationL = findViewById(R.id.sDestinationL);
        mDestinationT = findViewById(R.id.sDestinationT);
        Intent i = getIntent();
        scheduleID = i.getStringExtra("scheduleID");


        Query q1 = FirebaseDatabase.getInstance().getReference("schedule").orderByChild("scheduleID").equalTo(scheduleID);
        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Schedule s1 = null;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    s1 = ds.getValue(Schedule.class);



                }
                mDate.setText("Schedule Date :  " + s1.getScheduleDate());

                Log.i("RAV", s1.getRouteID() + s1.getScheduleID());

                Query q2 = FirebaseDatabase.getInstance().getReference("route").orderByChild("routeID").equalTo(s1.getRouteID());
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Route r1= null;
                        for(DataSnapshot ds2 :dataSnapshot.getChildren())
                        {
                            r1 = ds2.getValue(Route.class);
                        }
                        Log.i("RASD", r1.getBoardingTime() + r1.getDestinationTime() + r1.getDestinationLocation() + r1.getBoardingLocation());
                        mBoardingL.setText("From " +r1.getBoardingLocation());
                        mBoardingT.setText(r1.getBoardingTime());
                        mDestinationL.setText( "To  " + r1.getDestinationLocation());
                        mDestinationT.setText(r1.getDestinationTime());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
