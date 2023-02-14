package com.example.androidlabs;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TODO> todoList;
    private MyListAdapter myAdapter;
    Cdbtodo db;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.editText);
        Switch swUrgernt = findViewById(R.id.switch1);
        Button addButton = findViewById(R.id.button1);

        db = new Cdbtodo(this);
        todoList = db.getAllTodo();
        db.printCursor();

        addButton.setOnClickListener(click -> {

            String listItem = editText.getText().toString();
            boolean isUrgent= swUrgernt.isChecked();

            TODO todo = new TODO(listItem,isUrgent);
            db.addTodo(todo);

            todoList.add(todo);
            myAdapter.notifyDataSetChanged();

            editText.setText("");
            swUrgernt.setChecked(false);
        });

        ListView myList = findViewById(R.id.myList);
        myList.setAdapter(myAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            View newView = getLayoutInflater().inflate(R.layout.todo, null);
            TextView tView = newView.findViewById(R.id.textGoesHere);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is: " + pos +
                            "\n " + todoList.get(pos).todoText)
                    .setPositiveButton("Yes", (click, arg) -> {
                        todoList.remove(todoList.get(pos));
                        db.deleteTodo(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .setView(newView)
                    .create().show();
            return true;
        });
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return todoList.size();
        }

        public TODO getItem(int position) {
            return todoList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if (newView == null) {
                newView = inflater.inflate(R.layout.todo, parent, false);
            }

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText(getItem(position).todoText);

            if (getItem(position).isUrgent) {
                tView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {
                tView.setBackgroundColor(Color.WHITE);
                tView.setTextColor(Color.GRAY);
            }

            //return it to be put in the table
            return newView;
        }
    }
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}

class TODO {

    String todoText;
    Boolean isUrgent;

    public TODO(String todoText, boolean isUrgent) {
        this.todoText=todoText;
        this.isUrgent=isUrgent;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
}