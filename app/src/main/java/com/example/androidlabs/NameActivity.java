package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_name);
        TextView welcome = findViewById(R.id.textView);
        Button button_2 = findViewById(R.id.button2);
        Button button_3 = findViewById(R.id.button3);
        String welcome_text = NameActivity.this.getResources().getString(R.string.welcome);
        Intent intent = getIntent();
        String name = intent.getStringExtra("n");
        welcome.setText(welcome + " " + name + " !");

        button_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}