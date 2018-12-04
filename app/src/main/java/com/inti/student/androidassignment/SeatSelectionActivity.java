package com.inti.student.androidassignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<SeatDetails> seatList;
    String seating = "///";
    ViewGroup layout;
    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 150;
    int seatGaping = 10;
    private Button btnBook;
    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    int xs= 0;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String scheduleID= "";
    String routeID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        setTitle("Seat Selection Page");
        Intent i = getIntent();
        scheduleID = i.getStringExtra("scheduleID");
        btnBook = findViewById(R.id.buttonBook);
        layout = findViewById(R.id.layoutSeat);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SeatDetails");
        seatList = new ArrayList<SeatDetails>();
        Query q1 = FirebaseDatabase.getInstance().getReference("SeatDetails").orderByChild("scheduleID").equalTo(scheduleID);
        q1.addValueEventListener(listener);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("RV", "?" + seating);
                displayUI();


            }
        }, 5000);
        Log.i("SASA", "SAASASAS");



    }
    @Override
    public void onClick(View view) {
        TextView txtView = findViewById(R.id.txt_seat_selected);
        if (((String) view.getTag()).charAt(2) == 'A') {
            if (selectedIds.contains(((String) view.getTag()).substring(0,2) + " ")) {
                selectedIds = selectedIds.replace(((String) view.getTag()).substring(0,2) + " ", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
                txtView.setText("Seats Selected : " + selectedIds );
                xs--;


            } else {
                if(xs ==0)
                {
                    selectedIds = selectedIds + ((String) view.getTag()).substring(0,2) + " ";
                    view.setBackgroundResource(R.drawable.ic_seats_selected);
                    txtView.setText("Seats Selected : " + selectedIds );
                    xs++;
                }
                else
                {
                    view.setBackgroundResource(R.drawable.ic_seats_book);
                    Toast.makeText(this, "only single seat !", Toast.LENGTH_LONG).show();
                    Log.i("RASAS", selectedIds);
                }


            }
        } else if (((String) view.getTag()).charAt(2) == 'B')  {
            Toast.makeText(this, "Seat " + ((String) view.getTag()).substring(0,2) + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            txtView.setText("No seat is selected ");
        }
    }




    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren())
            {
                SeatDetails s1 = ds.getValue(SeatDetails.class);
                seatList.add(s1);
            }
            Log.i("RV", seatList.toString());
            seating += seatList.get(0).getSeatID() + seatList.get(1).getSeatID() + "___" + seatList.get(10).getSeatID() + seatList.get(11).getSeatID()
                    + "///" + seatList.get(2).getSeatID() + seatList.get(3).getSeatID()+ "___" + seatList.get(12).getSeatID() + seatList.get(13).getSeatID()
                    + "///" + seatList.get(4).getSeatID()+ seatList.get(5).getSeatID() + "___" + seatList.get(14).getSeatID()+ seatList.get(15).getSeatID()
                    + "///" + seatList.get(6).getSeatID() + seatList.get(7).getSeatID()+ "___" + seatList.get(16).getSeatID() + seatList.get(17).getSeatID()
                    + "///" + seatList.get(8).getSeatID() + seatList.get(9).getSeatID() +"___"+ seatList.get(18).getSeatID()+ seatList.get(19).getSeatID()
                    + "///";
            Log.i("RV", seating);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void displayUI()
    {
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(1 * seatGaping, 8 * seatGaping, 1 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layoutSeat.addView(layout);

        for (int index = 0; index < seating.length(); index = index +3) {
            if (seating.charAt(index+2) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            }  else if (seating.charAt(index+2) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(seating.substring(index, index+2));
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                view.setTextColor(Color.BLACK);
                view.setTag(seating.substring(index, index+3));
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seating.charAt(index+2) == 'B') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(seating.substring(index, index+2));
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                view.setTextColor(Color.WHITE);
                view.setTag(seating.substring(index, index+3));
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seating.charAt(index+2) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedIds.isEmpty())
                {
                    Toast.makeText(SeatSelectionActivity.this, "You have not selected any seats. Please select", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SeatSelectionActivity.this, "You have selected the seats of" + selectedIds, Toast.LENGTH_LONG).show();
                    String s = selectedIds;
                    String seat = selectedIds.replace(" ", "");
                    s= s.replace(" ", "");
                    s+="B";
                    SeatDetails s1 = new SeatDetails(s, "ST0001");
//                    myRef.child(s1.getScheduleID()+selectedIds.replace(" ", "A")).setValue(s1);
                    Intent g = getIntent();
                    routeID = g.getStringExtra("routeID");
                    Intent i = new Intent(SeatSelectionActivity.this, confirmActivity.class);
                    i.putExtra("scheduleID", scheduleID);
                    i.putExtra("seatNo", seat);
                    i.putExtra("seatObject", (Parcelable)s1);
                    i.putExtra("selectedID", selectedIds);
                    i.putExtra("routeID", routeID);
                    startActivity(i);

                }
            }
        });
    }


}
