package com.example.precticeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TTTTTTTTTT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_t_t_t_t_t_t_t_t_t);

        Intent intent = getIntent();
        String number = intent.getStringExtra("textnumber");

        TextView t1 = (TextView) findViewById(R.id.testttt);
        t1.setText(number);
    }
}