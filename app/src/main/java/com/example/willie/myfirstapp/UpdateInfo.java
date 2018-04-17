package com.example.willie.myfirstapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateInfo extends AppCompatActivity {

    private TextView instructions;
    private EditText updatedFirstName;
    private EditText updatedLastName;
    private EditText updatedAge;
    private ImageButton saveBtn;

    private int highscore;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        instructions = findViewById(R.id.tvInstructions);
        updatedFirstName = findViewById(R.id.etFirstNameUpdate);
        updatedLastName = findViewById(R.id.etLastNameUpdate);
        updatedAge = findViewById(R.id.etAgeUpdate);
        saveBtn = findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        @SuppressLint("RestrictedApi") final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                updatedFirstName.setText(userProfile.getFirst_Name());
                updatedLastName.setText(userProfile.getLast_Name());
                updatedAge.setText(userProfile.getAge());
                highscore = userProfile.getHighscore();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateInfo.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String firstName = updatedFirstName.getText().toString();
            String lastName = updatedLastName.getText().toString();
            String age = updatedAge.getText().toString();
            UserProfile userProfile = new UserProfile(firstName, lastName, age, highscore);

            databaseReference.setValue(userProfile);

            finish();
            }
        });
    }
}
