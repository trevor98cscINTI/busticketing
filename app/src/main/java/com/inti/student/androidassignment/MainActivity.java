package com.inti.student.androidassignment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private TextView mSignUp;
    //private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView template;
    private String templateString ="";
    String  emailtest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.this.setTitle("Login Page");
        mEmail = findViewById(R.id.loginEmail);
        mPassword = findViewById(R.id.loginPassword);
        mLogin = findViewById(R.id.buttonLogin);
        mSignUp = findViewById(R.id.viewRegister);
        //progressDialog = new ProgressDialog(this);
          database = FirebaseDatabase.getInstance();
           myRef = database.getReference("user");
           mAuth = FirebaseAuth.getInstance();
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        emailtest= pref.getString("userEmail", "");
        Log.i("EMAIL", emailtest);




          mAuthListener = new FirebaseAuth.AuthStateListener() {
               @Override
               public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                   if(firebaseAuth.getCurrentUser() != null && !(emailtest.isEmpty()))
                   {
                       Log.i("EMAIL", emailtest);
                       startActivity(new Intent(MainActivity.this, TestActivity.class));

                   }

               }
           };



        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("rv", "Attempt 1");
               //signIn(mEmail.getText().toString(), mPassword.getText().toString());
               startSignIn();
            }
        });




        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
              //  startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn()
    {
       final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        finish();
                        template = findViewById(R.id.templateofText2);
                        mPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        mPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        mEditor = mPreferences.edit();
                        Query q1 = FirebaseDatabase.getInstance().getReference("user").orderByChild("userEmail").equalTo(email);
                        q1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                {
                                    // Profile p = dataSnapshot1.getValue(Profile.class);
                                    template.setText(dataSnapshot1.getKey());
                                }
                                mEditor.putString("customerUSERID", template.getText().toString());
                                mEditor.commit();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        mEditor.putString("userEmail", email);
                        mEditor.commit();
                        Intent i = new Intent(MainActivity.this, TestActivity.class);
                        startActivity(i);
                    }
                }
            });
        }


    }



   /* private void signIn(final String email, final String password)
    {
        Query query = FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("userEmail")
                .equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists()) {
                    Log.i("v", "Attempt 1");
                    User user = new User();


                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        user = snapshot.getValue(User.class);

                    }
                    Log.i("v", user.getUserEmail());


                    if(user.getUserPassword().equals(password))
                    {
                        Toast.makeText(MainActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        i.putExtra("UserDetails", user);
                        startActivity(i);
                        Log.i("v", "Attempt 3");
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "The password is wrong", Toast.LENGTH_SHORT).show();
                        Log.i("v", "Attempt 4");
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "The account does not exist", Toast.LENGTH_SHORT).show();
                    Log.i("v", "Attempt 5");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("v", "Attempt 6");

            }
        });
    }*/



}
