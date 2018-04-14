package com.example.willie.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateInfo extends AppCompatActivity {

    private TextView instructions;
    private EditText updatedFirstName;
    private EditText updatedLastName;
    private EditText updatedAge;
    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        instructions = findViewById(R.id.tvInstructions);
        updatedFirstName = findViewById(R.id.etFirstNameUpdate);
        updatedLastName = findViewById(R.id.etLastNameUpdate);
        updatedAge = findViewById(R.id.etAgeUpdate);
        updateBtn = findViewById(R.id.btnUpdate);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }


    public Boolean validate() {
        return true;
    }
}
