package com.inti.student.androidassignment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private Button mLogOut;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    TextView  mProfileName;
    TextView  mProfileIC;
    TextView  mProfileGender;
    TextView  mProfileContact;
    TextView  mProfileEmail;
    String userEmail = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Account Profile");
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLogOut = view.findViewById(R.id.logoutButton);
        mProfileName = view.findViewById(R.id.mProfileName);
        mProfileContact = view.findViewById(R.id.mProfileContact);
        mProfileEmail = view.findViewById(R.id.mProfileEmail);
        mProfileGender = view.findViewById(R.id.mProfileGender);
        mProfileIC = view.findViewById(R.id.mProfileIC);
        SharedPreferences pref = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userEmail = pref.getString("userEmail", "");

        Query q1 = FirebaseDatabase.getInstance().getReference("user").orderByChild("userEmail").equalTo(userEmail);
        q1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = null;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    u = ds.getValue(User.class);
                }
                mProfileName.setText("Name : " + u.getUserName());
                mProfileContact.setText( "Contact No : " +u.getUserContact());
                mProfileEmail.setText("Email : " +u.getUserEmail());
                mProfileGender.setText("Gender : " +u.getUserGender());
                mProfileIC.setText("IC Number : " + u.getUserIC());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(ProfileFragment.this.getActivity(), MainActivity.class));


            }

        });

    }
}