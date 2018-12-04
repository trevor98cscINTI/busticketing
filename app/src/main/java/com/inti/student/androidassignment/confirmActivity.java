package com.inti.student.androidassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class confirmActivity extends AppCompatActivity implements View.OnClickListener {
    String scheduleID ="";
    String seatNo ="";
    SeatDetails sd;
    String selectedID ="";
    EditText mName;
    EditText mContact;
    EditText mIC;
    Button btnConfirm;
    String user1ID;
    String routeID = "";
    FirebaseDatabase database;
    DatabaseReference myRef;
    long countUsers;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        setTitle("Confirmation to Book's Page");
        mName = findViewById(R.id.cName);
        mContact = findViewById(R.id.cContact);
        mIC = findViewById(R.id.cIC);
        btnConfirm = findViewById(R.id.cConfirmBtn);
        Intent i = getIntent();
        scheduleID = i.getStringExtra("scheduleID");
        seatNo = i.getStringExtra("seatNo");
        selectedID  = i.getStringExtra("selectedID");
        sd = i.getExtras().getParcelable("seatObject");
        routeID = i.getStringExtra("routeID");
        database = FirebaseDatabase.getInstance();
       // myRef = database.getReference("schedule");
        SharedPreferences pref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user1ID = pref.getString("customerUSERID", "");
        Log.i("RV", "user ID" + user1ID);
        Log.i("RV", scheduleID);
        Log.i("RV", sd.getScheduleID() + sd.getSeatID());
        Log.i("RV", selectedID);
        Log.i("RV", seatNo);
        Log.i("RV", routeID);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mName.getText().toString().isEmpty() || mContact.getText().toString().isEmpty() || mIC.getText().toString().isEmpty())
        {


            if (mName.getText().toString().isEmpty()) {
                mName.setError("Passenger's name is required");
                mName.requestFocus();
                return;
            }
            if (mIC.getText().toString().isEmpty()) {
                mIC.setError("Passenger's IC is required");
                mIC.requestFocus();
                return;
            }
            if (mIC.getText().length() != 12) {
                mIC.setError("Length of IC number must be 12");
                mIC.requestFocus();
                return;
            }


            if (mContact.getText().toString().isEmpty()) {
                mContact.setError("Phone number is required");
                mContact.requestFocus();
                return;
            }
            if (mContact.getText().length() != 10) {
                mContact.setError("Phone number's length must be 10");
                mContact.requestFocus();
                return;
            }


        }
        else
        {
            Query q1 = FirebaseDatabase.getInstance().getReference("schedule").orderByChild("scheduleID").equalTo(scheduleID);
            q1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Schedule s1 = null;
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        s1 = ds.getValue(Schedule.class);

                    }
                    s1.setTotalSeats(s1.getTotalSeats()-1);
                    myRef = database.getReference("schedule");
                    myRef.child(s1.getScheduleID()).setValue(s1);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myRef = database.getReference("SeatDetails");
            myRef.child(sd.getScheduleID()+seatNo+"A").setValue(sd);

            Query q3 = FirebaseDatabase.getInstance().getReference("BookingDetails");
            q3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myRef = database.getReference("BookingDetails");
                    date =new Date();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                    if (dataSnapshot.getValue() != null) {   // Check for data snapshot has some value
                        // check for counts of data snapshot children
                        countUsers = dataSnapshot.getChildrenCount() +1;
                        String x;
                        if(countUsers < 10)
                        {
                            x = "000" + Long.toString(countUsers);
                        }
                        else if (countUsers < 100)
                        {
                            x = "00" + Long.toString(countUsers);
                        }
                        else if(countUsers < 1000)
                        {
                            x = "0" + Long.toString(countUsers);
                        }
                        else
                        {
                            x =  Long.toString(countUsers);
                        }
                        String id = "BD" + x;
                        Log.i("ID", id);

                        BookingDetails bd = new BookingDetails(seatNo, 1, 14.5,mName.getText().toString(), mIC.getText().toString(), mContact.getText().toString(), user1ID, dateFormatter.format(date), "Paid", scheduleID);
                        myRef.child(id).setValue(bd);
                    }
                    else
                    {
                        String id = "BD000" + Long.toString(dataSnapshot.getChildrenCount() +1);
                        Log.i("ID", id);
                        BookingDetails bd = new BookingDetails(seatNo, 1, 14.5,mName.getText().toString(), mIC.getText().toString(), mContact.getText().toString(), user1ID, dateFormatter.format(date), "Paid", scheduleID);
                        myRef.child(id).setValue(bd);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Toast.makeText(this, "You have successfully make a payment", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, TestActivity.class);
            startActivity(in);

        }

    }
}
