package com.inti.student.androidassignment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BookingFragment extends Fragment {
    DatabaseReference reference;
    ArrayList<BookingDetails> details;
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    ArrayList<String> bookIDs;
    String user1ID = "";
    TextView txtTemplate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("The Booking Details's Page");
        View RootView = inflater.inflate(R.layout.fragment_bookings, container, false);
        return RootView;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences pref = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String  emailtest= pref.getString("userEmail", "");
                user1ID = pref.getString("customerUSERID", "");
                Log.i("RV", "USER ID " + user1ID);

       Query query = FirebaseDatabase.getInstance().getReference("BookingDetails").orderByChild("userID").equalTo(user1ID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                details = new ArrayList<BookingDetails>();
                bookIDs = new ArrayList<String>();
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        // Profile p = dataSnapshot1.getValue(Profile.class);
                        BookingDetails p = dataSnapshot1.getValue(BookingDetails.class);
                        // list.add(p);
                        details.add(p);
                        bookIDs.add(dataSnapshot1.getKey());
                        Log.i("RV", dataSnapshot1.getKey());

                    }
                    Log.i("RV", details.get(0).toString());

                    //listAdapter.notifyDataSetChanged();
                    listAdapter = new ListAdapter(BookingFragment.this.getContext(), details, bookIDs);
                    listAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(listAdapter);



                }
                else
                {
                    Toast.makeText(getActivity(), "This account does not have any booking details. Thank you ", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}