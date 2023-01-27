package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
//import android.content.res.Resources;
import android.os.Bundle;
//import android.text.TextWatcher;
//import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    EditText editText = findViewById(R.id.firstEditText);
    TextView result =  findViewById(R.id.textview);
    CheckBox checkBox =  findViewById(R.id.checkbox);
    Button btn =  findViewById(R.id.button);

    btn.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
        String text = editText.getText().toString();
        result.setText(text);
        Toast.makeText(getApplicationContext(), getString(R.string.toast_message), Toast.LENGTH_LONG).show();
    }
    });
    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

    {
        @Override
        public void onCheckedChanged (CompoundButton cb,boolean b){
        if (b) {
            Snackbar.make(cb, getString(R.string.snack_on), Snackbar.LENGTH_LONG)
                    .setAction("undo", click -> cb.setChecked(!b))
                    .show();
        } else {
            Snackbar.make(cb, getString(R.string.snack_off), Snackbar.LENGTH_LONG)
                    .setAction("undo", click -> cb.setChecked(!b))
                    .show();
        }
     }
    });
   }
}