package com.inti.student.androidassignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinner;
    Spinner spinner2;
    Button btnSearch;
    TextView btntripDate;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int day;
    Calendar calendar;
    ArrayList<Route> routeList;
    ArrayList<Schedule> scheduleList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    Intent s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Home Page");
        final View rootView =inflater.inflate(R.layout.fragment_home, container, false);
        spinner = rootView.findViewById(R.id.spinner);
        spinner2 = rootView.findViewById(R.id.spinner2);
        btnSearch = rootView.findViewById(R.id.buttonSearch);
        btntripDate = rootView.findViewById(R.id.tripDate);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.locationList, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner2.setAdapter(spinnerAdapter);
        spinner2.setOnItemSelectedListener(this);
        btntripDate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        return rootView;
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)view).setTextSize(20);
            String text = parent.getItemAtPosition(position).toString();
        Log.i("SPIINERER 1",spinner.getSelectedItem().toString());
        Log.i("SPIINERER 2",spinner2.getSelectedItem().toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSearch:
                if(spinner.getSelectedItem().toString().equals("Select City ") || spinner2.getSelectedItem().toString().equals("Select City"))
                {
                    Toast.makeText(view.getContext(), "Please do not leave blank on the fields", Toast.LENGTH_LONG).show();

                    spinner2.requestFocus();
                }
                else if(spinner.getSelectedItem().toString().equals(spinner2.getSelectedItem().toString()))
                {
                    Toast.makeText(view.getContext(), "The Destination's location should not be same as the Boarding's", Toast.LENGTH_SHORT).show();
                    spinner2.requestFocus();
                }
                else if(btntripDate.getText().toString().equals("Select Date"))
                {
                    btntripDate.setError("Please select Date!!!");
                    btntripDate.requestFocus();
                    Toast.makeText(view.getContext(), "Please select date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    s = new Intent(getContext(), tripSelectionActivity.class);
                    Query q1 = FirebaseDatabase.getInstance().getReference("route").orderByChild("boardingLocation").equalTo(spinner.getSelectedItem().toString());
                    q1.addValueEventListener(valueEventListener);
                }
                Log.e("RV", spinner.getSelectedItem().toString());
                Log.e("RV", spinner2.getSelectedItem().toString());

                break;

            case R.id.tripDate:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++;
                        btntripDate.setText(year + "-"+month+"-"+day);

                    }
                }, year, month, day);
                datePickerDialog.show();
                break;
        }
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            routeList = new ArrayList<Route>();
            scheduleList = new ArrayList<Schedule>();

            if(!dataSnapshot.exists())
            {
                Toast.makeText(getContext(), "There is no trip to the destinations", Toast.LENGTH_LONG).show();
            }
            else {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Route r = ds.getValue(Route.class);
                    if (r.getDestinationLocation().equals(spinner2.getSelectedItem().toString())) {

                        routeList.add(r);
                        // Log.i("RV", ds.getKey());
                        Query q2 = FirebaseDatabase.getInstance().getReference("schedule").orderByChild("routeID").equalTo(ds.getKey());
                        q2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                                for (DataSnapshot ds2 : dataSnapshot2.getChildren()) {
                                    Schedule s1 = ds2.getValue(Schedule.class);


                                    if (s1.getScheduleDate().equals(btntripDate.getText().toString())) {
                                        if (s1.getTotalSeats() != 0) {
                                            scheduleList.add(s1);


                                        }
                                        Log.e("RVAa", Integer.toString(scheduleList.size()));
                                    }
                                }



                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Log.e("RV", ds.getKey());
                    }

                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        s.putParcelableArrayListExtra("routeList", routeList);
                        s.putParcelableArrayListExtra("scheduleList", scheduleList);
                        startActivity(s);

                    }
                }, 2000);



            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}