package com.example.willie.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView profileName;

    Button button_Play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        profileName = findViewById(R.id.tvProfileName);
        button_Play = (Button) findViewById(R.id.button_Play);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        @SuppressLint("RestrictedApi")DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("HI " + userProfile.getUserFirst_Name() + "!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeScreen.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        button_Play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                startActivity(new Intent(HomeScreen.this, GameState.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(HomeScreen.this, Login.class));
            }
            case R.id.updateMenu: {
                //startActivity(new Intent(HomeScreen.this, UpdateInfo.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
