package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Cdbtodo extends SQLiteOpenHelper {
    private static final String DB_NAME ="mytodo_db";
    private static final int DB_VERSION =1;
    private static final String KEY_ID ="id";
    private static final String KEY_TODOTEXT ="tototext";
    private static final String KEY_ISARGENT ="isUrgent";
    private static final String TABLE_TODO ="tbtodo";
    private static final String TAG = MainActivity.class.getSimpleName();

    public Cdbtodo(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        String create_table = "create TABLE "+TABLE_TODO+"("+KEY_ID+" integer primary key, "+KEY_TODOTEXT+" varchar(100),"+KEY_ISARGENT+" varchar(10))";

        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String delete_query = "DROP Table "+TABLE_TODO;
        sqLiteDatabase.execSQL(delete_query);
        onCreate(sqLiteDatabase);
    }

    public void addTodo(@NonNull TODO todo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODOTEXT,todo.getTodoText());
        values.put(KEY_ISARGENT,String.valueOf(todo.isUrgent));
        sqLiteDatabase.insert(TABLE_TODO,null,values);
    }
    public ArrayList<TODO> getAllTodo(){
        ArrayList<TODO> todos = new ArrayList<>();
        String select_query = "select * from "+TABLE_TODO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(select_query,null);

        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false){
                int indexTdoText = cursor.getColumnIndex(KEY_TODOTEXT);
                String todoText = cursor.getString(indexTdoText);
                int indexIsUrgent = cursor.getColumnIndex(KEY_ISARGENT);
                String isUrgent = cursor.getString(indexIsUrgent);
                boolean bIsUrgent=Boolean.parseBoolean(isUrgent);
                TODO todoItem = new TODO(todoText,bIsUrgent);
                todos.add(todoItem);
                cursor.moveToNext();
            }
        }
        return todos;
    }
    public Integer deleteTodo (Integer id) {
        id=id+1;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TODO,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public boolean deleteTodo(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_NAME, KEY_TODOTEXT + "=?", new String[]{name}) > 0;
    }
    public Cursor getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_TODO);
        return numRows;
    }
    public void printCursor(){

        SQLiteDatabase db = this.getReadableDatabase();
        String select_query = "select * from "+TABLE_TODO;
        Cursor c = db.rawQuery(select_query,null);
        if(c.moveToFirst()){
            Log.d(TAG,"Database version number is: "+String.valueOf(db.getVersion()));
            Log.d(TAG,"Number of columns in the cursor:"+String.valueOf(c.getColumnCount()));
            Log.d(TAG,"Number of results in the cursor:"+String.valueOf(c.getCount()));
            Log.d(TAG,"Each row of results in the cursor:");
            while(c.isAfterLast() == false){
                int indexTdoText = c.getColumnIndex(KEY_TODOTEXT);
                String todoText = c.getString(indexTdoText);
                int indexIsUrgent = c.getColumnIndex(KEY_ISARGENT);
                String isUrgent = c.getString(indexIsUrgent);
                boolean bIsUrgent=Boolean.parseBoolean(isUrgent);
                TODO todoItem = new TODO(todoText,bIsUrgent);
                Log.d(TAG,String.valueOf("Todo: "+todoText+ ", IsUrgent: "+isUrgent));
                c.moveToNext();
            }

        }
    }
}