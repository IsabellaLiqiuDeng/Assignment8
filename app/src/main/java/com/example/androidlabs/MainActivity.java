package com.example.androidlabs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText editPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editPersonName = (EditText) findViewById(R.id.editPersonName);
        Button button1 = (Button) findViewById(R.id.button1);

        sharedPreferences = getSharedPreferences("Lab_3", Context.MODE_PRIVATE);
        editPersonName.setText(sharedPreferences.getString("name", ""));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editPersonName.getText().toString();
                Intent intern = new Intent(MainActivity.this, NameActivity.class);
                intern.putExtra("n", name);
                startActivityForResult(intern, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_CANCELED)
        {
            finish();
        } else if(requestCode == RESULT_FIRST_USER){
            editPersonName.setText("");

        }
    }

    @Override
    protected void onPause () {
        super.onPause();
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("name",editPersonName.getText().toString());
        myEdit.commit();
    }
}
