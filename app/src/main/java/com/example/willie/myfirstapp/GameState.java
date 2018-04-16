package com.example.willie.myfirstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameState extends AppCompatActivity {

    //Views + Buttons
    TextView textView_Time, textView_Clicks, textView_HighScore, textView_Start;
    ImageView imageView_Back;
    ImageButton imageButton_Clicks, imageButton_Back, imageButton_Start;

    //TIMER
    CountDownTimer cd_timer;
    int timer = 30;

    //CLICKS
    int clicks = 0;
    int highScore;

    //DATABASE
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //define views
        textView_Time = (TextView) findViewById(R.id.textView_Time);
        textView_Clicks = (TextView) findViewById(R.id.textView_Clicks);
        textView_HighScore = (TextView) findViewById(R.id.textView_HighScore);
        textView_Start = (TextView) findViewById(R.id.textView_Start);
        imageView_Back = (ImageView) findViewById(R.id.imageView_back);
        imageButton_Clicks = (ImageButton) findViewById(R.id.imageButton_Clicks);
        imageButton_Back = (ImageButton) findViewById(R.id.imageButton_Back);
        imageButton_Start = (ImageButton) findViewById(R.id.imageButton_Start);

        //Enable Start; Disable Clicks
        imageButton_Start.setEnabled(true);
        imageButton_Clicks.setEnabled(false);

        //Set text of start button
        textView_Start.setText("START");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        @SuppressLint("RestrictedApi") final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        final DatabaseReference highscoreReference = databaseReference.child("highscore");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Countdown timer
        cd_timer = new CountDownTimer(30000, 1000){
            public void onTick(long millis){
                    timer--;
                    textView_Time.setText("Time: " + timer);
            }

            public void onFinish(){
                imageButton_Clicks.setEnabled(false);
                //if clicks is > firebase highscore stored
                //update the highscore
                if(clicks > highScore){
                    highscoreReference.setValue(clicks);

                }

            }
        };

        //On click, start button
        imageButton_Start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Start the timer
                if(((String) textView_Start.getText()) == "START") {
                    cd_timer.start();
                    textView_Start.setText("RESET");
                    imageButton_Clicks.setEnabled(true);
                }
                //Reset Timer
                else{
                    cd_timer.cancel();
                    timer = 30;
                    clicks = 0;
                    textView_Clicks.setText("Clicks: " + clicks);
                    imageButton_Clicks.setEnabled(false);
                    textView_Time.setText("Time: " + timer);
                    textView_Start.setText("START");
                }
            }
        });
        imageButton_Back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                startActivity(new Intent(GameState.this, HomeScreen.class));
            }
        });

        //On click, click button
        imageButton_Clicks.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                //On Press make image larger
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = (float) 1.15;
                    float y = (float) 1.15;

                    imageButton_Clicks.setScaleX(x);
                    imageButton_Clicks.setScaleY(y);
                    //When button is pressed, it is considered a click
                    clicks++;
                    textView_Clicks.setText("Clicks: " + clicks);

                //On Release return image to normal
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = 1;
                    float y = 1;

                    imageButton_Clicks.setScaleX(x);
                    imageButton_Clicks.setScaleY(y);
                }
                return false;
            }
        });
    }
    private void showData(DataSnapshot dataSnapshot){
        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
        textView_HighScore.setText("Highscore: " + Integer.toString(userProfile.getHighscore()));
    }
}
