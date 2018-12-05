package com.inti.student.androidassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class tryActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SeatDetails");
        SeatDetails sd = null;
        sd = new SeatDetails();
        sd.setScheduleID("ST0006");
        for(int i =0;i<20;i++)
        {
            if(i ==0)
            {
                sd.setSeatID("MAA");

            }
            if(i ==1)
            {
                sd.setSeatID("MBA");

            }
            if(i ==2)
            {
                sd.setSeatID("MCA");
            }
            if(i ==3)
            {
                sd.setSeatID("MDA");
            }
            if(i ==4)
            {
                sd.setSeatID("MEA");
            }
            if(i ==5)
            {
                sd.setSeatID("MFA");
            }
            if(i ==6)
            {
                sd.setSeatID("MGA");
            }
            if(i ==7)
            {
                sd.setSeatID("MHA");
            }
            if(i ==8)
            {
                sd.setSeatID("MIA");
            }
            if(i ==9)
            {
                sd.setSeatID("MJA");
            }
            if(i ==10)
            {
                sd.setSeatID("NAA");
            }
            if(i ==11)
            {
                sd.setSeatID("NBA");
            }
            if(i ==12)
            {
                sd.setSeatID("NCA");
            }
            if(i ==13)
            {
                sd.setSeatID("NDA");
            }
            if(i ==14)
            {
                sd.setSeatID("NEA");
            }
            if(i ==15)
            {
                sd.setSeatID("NFA");
            }
            if(i ==16)
            {
                sd.setSeatID("NGA");
            }
            if(i ==17)
            {
                sd.setSeatID("NHA");
            }
            if(i ==18)
            { sd.setSeatID("NIA");

            }
            if(i ==19)
            {
                sd.setSeatID("NJA");
            }

            myRef.child(sd.getScheduleID()+sd.getSeatID()).setValue(sd);
        }

    }
}
