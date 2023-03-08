package com.example.assignment8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Dadjoke extends AppCompatActivity {
    private TextView dad_joke_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadjoke);
        dad_joke_textview = findViewById(R.id.dad_joke_textview);
        dad_joke_textview.setText("Why don't scientists trust atoms? Because they make up everything!");
    }

}
