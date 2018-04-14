package com.example.willie.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameState extends AppCompatActivity {

    TextView textView_Time, textView_Clicks, textView_HighScore;
    Button button_Clicks, button_Back, button_Start;

    CountDownTimer cd_timer;
    int timer = 30;
    int clicks = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //define views
        textView_Time = (TextView) findViewById(R.id.textView_Time);
        textView_Clicks = (TextView) findViewById(R.id.textView_Clicks);
        textView_HighScore = (TextView) findViewById(R.id.textView_HighScore);
        button_Clicks = (Button) findViewById(R.id.button_Clicks);
        button_Back = (Button) findViewById(R.id.button_Back);
        button_Start = (Button) findViewById(R.id.button_Start);

        //Enable Start; Disable Clicks
        button_Start.setEnabled(true);
        button_Clicks.setEnabled(false);

        //Set text of start button
        button_Start.setText("START");

        //Countdown timer
        cd_timer = new CountDownTimer(30000, 1000){
            public void onTick(long millis){
                    timer--;
                    textView_Time.setText("Time: " + timer);
            }

            public void onFinish(){
                button_Clicks.setEnabled(false);
            }
        };

        //On click, start button
        button_Start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Start the timer
                if(((String) button_Start.getText()) == "START") {
                    cd_timer.start();
                    button_Start.setText("RESET");
                    button_Clicks.setEnabled(true);
                }
                //Reset Timer
                else{
                    cd_timer.cancel();
                    timer = 30;
                    clicks = 0;
                    button_Clicks.setEnabled(false);
                    textView_Time.setText("Time: " + timer);
                    button_Start.setText("START");
                }
            }
        });

        button_Back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
                startActivity(new Intent(GameState.this, HomeScreen.class));
            }
        });

        //On click, click button
        button_Clicks.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                clicks++;
                textView_Clicks.setText("Clicks: " + clicks);
            }
        });
    }
}
