package com.example.willie.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userAge;
    private EditText userEmail;
    private EditText userPassword;
    private TextView signIn;
    private Button register;
    private String firstName, lastName, age, email, password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUiViews();
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    //Upload data to the database
                    String user_email = userEmail.getText().toString();
                    final String user_password = userPassword.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(Registration.this, "Registration Successful. Upload Complete", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Registration.this, HomeScreen.class));
                            } else {
                                if(user_password.length() < 6) {
                                    Toast.makeText(Registration.this, "Password must be at least 6 characters.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                finish();
                startActivity(intent);
            }
        });
    }


    private void setUpUiViews() {
        userFirstName = (EditText)findViewById(R.id.etUserFirstName);
        userLastName = (EditText)findViewById(R.id.etUserLastName);
        userAge = (EditText)findViewById(R.id.etUserAge);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        signIn = (TextView)findViewById(R.id.tvSignIn);
        register = (Button)findViewById(R.id.btnRegister);
    }

    private Boolean validate() {
        Boolean result = false;

        firstName = userFirstName.getText().toString();
        lastName = userLastName.getText().toString();
        age = userAge.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();

        if(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        @SuppressLint("RestrictedApi") DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile profile = new UserProfile(firstName, lastName, email, age);
        myRef.setValue(profile);
    }
}
