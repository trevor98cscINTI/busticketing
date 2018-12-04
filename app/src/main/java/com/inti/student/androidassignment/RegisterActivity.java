package com.inti.student.androidassignment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity{
    private EditText mEmail;
    private EditText mPassword;
    private Button mButtonRegister;
    private TextView mBirthDay;
    Calendar cal;
    DatePickerDialog dpd;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private RadioGroup mRG;
    private RadioButton mRB1, mRB2, mRB3;
    private EditText mPhone, mIC, mUsername, mPassword2;
    private long countUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register Page");
        mButtonRegister =  findViewById(R.id.sign_in_button);
        mEmail = findViewById(R.id.registerEmail);
        mPassword = findViewById(R.id.registerPassword);
        mRG = findViewById(R.id.registerRadioGroup);
        mPhone = findViewById(R.id.registerPhone);
        mIC= findViewById(R.id.registerIC);
        mUsername = findViewById(R.id.registerUsername);
        mPassword2 = findViewById(R.id.registerPassword2);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");



        mButtonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String userName = mUsername.getText().toString().trim();
                final String password2 = mPassword2.getText().toString().trim();
                final String IC = mIC.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();
                int selectedId = mRG.getCheckedRadioButtonId();
                mRB1 = findViewById(selectedId);
                mRB2 = findViewById(R.id.registerRadio1);
                mRB3 = findViewById(R.id.registerRadio2);





                if ((!password.equals(password2))||email.isEmpty() || password.isEmpty() || userName.isEmpty() || password2.isEmpty() || IC.isEmpty() || phone.isEmpty() ||((!mRB2.isChecked()) && (!mRB3.isChecked())))
                {


                    if (userName.isEmpty()) {
                        mUsername.setError("Username is required");
                        mUsername.requestFocus();
                        return;
                    }
                    if (email.isEmpty()) {
                        mEmail.setError("Email is required");
                        mEmail.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        mEmail.setError("Please enter a valid email");
                        mEmail.requestFocus();
                        return;
                    }

                    if (phone.isEmpty()) {
                        mPhone.setError("Phone number is required");
                        mPhone.requestFocus();
                        return;
                    }
                    if (phone.length() != 10) {
                        mPhone.setError("Phone number's length must be 10");
                        mPhone.requestFocus();
                        return;
                    }

                    if((!mRB2.isChecked()) && (!mRB3.isChecked()))
                    {
                        Toast.makeText(getApplicationContext(), "Please select any gender", Toast.LENGTH_SHORT).show();
                        Log.i("", "ERROR");
                    }
                    if (IC.isEmpty()) {
                        mIC.setError("IC number is required");
                        mIC.requestFocus();
                        return;
                    }
                    if (IC.length() != 12) {
                        mIC.setError("Length of IC number must be 12");
                        mIC.requestFocus();
                        return;
                    }
                    if (password.isEmpty()) {
                        mPassword.setError("Password is required");
                        mPassword.requestFocus();
                        return;
                    }

                    if (password.length() < 6) {
                        mPassword.setError("Minimum length of password should be 6");
                        mPassword.requestFocus();
                        return;
                    }
                    if (password2.isEmpty()) {
                        mPassword2.setError("Password is required");
                        mPassword2.requestFocus();
                        return;
                    }

                    if (password2.length() < 6) {
                        mPassword2.setError("Minimum length of password should be 6");
                        mPassword2.requestFocus();
                        return;
                    }
                    if(!password.equals(password2))
                    {
                        mPassword2.setError("The confirmed password must be same as the first password");
                        mPassword2.requestFocus();
                        return;
                    }

                }
                else
                    {


                        Log.i("rv", mRB1.getText().toString());
                Query query = FirebaseDatabase.getInstance().getReference("user")
                        .orderByChild("userEmail")
                        .equalTo(email);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.i("v", "Attempt 1");
                            User user = new User();


                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                user = snapshot.getValue(User.class);

                            }

                            if (email.equals(user.getUserEmail())) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() { //ref will be your desired path where you want to count children
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
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
                                                    String id = "UUID" + x;
                                                    User newUser = new User(email, password, phone, userName, IC, mRB1.getText().toString());
                                                    myRef.child(id).setValue(newUser);
                                                }
                                                else
                                                {
                                                    String id = "UUID000" + Long.toString(dataSnapshot.getChildrenCount() +1);
                                                    User newUser = new User(email, password, phone, userName, IC, mRB1.getText().toString());
                                                    myRef.child(id).setValue(newUser);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        SharedPreferences preferences = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    } else {

                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.i("v", "Attempt 6");

                    }
                });
            }



            }
        });




        /*mBirthDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);



                dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String YoB;
                        if((mMonth+1 < 10 ))
                        {
                            if (mDay < 10)
                            {
                                YoB = Integer.toString(mYear) + "-0" + Integer.toString(mMonth + 1) + "-0" + Integer.toString(mDay);
                            }
                            else
                            {
                                YoB = Integer.toString(mYear) + "-0" + Integer.toString(mMonth + 1) + "-" + Integer.toString(mDay);
                            }

                        }
                        else {
                            if(mDay < 10)
                            {
                                YoB = Integer.toString(mYear) + "-" + Integer.toString(mMonth + 1) + "-0" + Integer.toString(mDay);
                            }
                            else {
                                YoB = Integer.toString(mYear) + "-" + Integer.toString(mMonth + 1) + "-" + Integer.toString(mDay);
                            }
                        }
                        mBirthDay.setText(YoB);
                    }
                }, day, month, year);
                dpd.show();
            }

        });
        mButtonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                Log.i("rv", mBirthDay.getText().toString());

            }

        });*/

    }
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
      //  DatabaseReference myRef = database.getReference("user");

       /* mEmail = findViewById(R.id.editEmail);
        mPassword =  findViewById(R.id.editPassword);*/
      //  mButtonRegister =  findViewById(R.id.sign_in_button);
       // mButtonRegister.setOnClickListener(this);
      /*  mButtonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                Log.i("rv", mEmail.getText().toString().trim());

            }

        });*/



        //mButtonRegister.setOnClickListener(this);
        //myRef.push().setValue("dfd.com", "Hello World");
       // User user1 = new User("UUID0001", "banzaishu@gmail.com", "91122702ab", "0164043688",
         //       "Trevor AXA", "980425025665", "Male", 20 );
//
      //  String id = myRef.push().getKey();
      //  myRef.child(id).setValue(user1);
  //  }

  /*  private void registerButton(){
        String email = mEmail.getText().toString().trim();
        String password= mPassword.getText().toString().trim();
        Log.i("rv",  email);

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();

            Log.i("rv", "unable to email");
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            Log.i("rv", "unable to password");
            return;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == mButtonRegister)
        {
            registerButton();

        }

    }*/


}
