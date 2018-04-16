package com.example.willie.myfirstapp;

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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private TextView attemps;
    private TextView userRegistration;
    private Button login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        attemps = (TextView)findViewById(R.id.tvAttempts);
        login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);

        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser(); //This line checks if the user is already logged in.
        if(user != null) {//Automatically logs the user in if he has already signed in.
            finish();
            startActivity(new Intent(Login.this, HomeScreen.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                password = userPassword.getText().toString();
                if(name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    validate(userName.getText().toString(), userPassword.getText().toString());
                }
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this, Registration.class));
            }
        });
    }

    private void validate(String username, String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(username, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(Login.this, HomeScreen.class));
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    counter--;
                    attemps.setText("Number of attempts remaining " + String.valueOf(counter));
                    if(counter == 0) {
                        login.setActivated(false);
                    }
                }
            }
        });
    }
}
