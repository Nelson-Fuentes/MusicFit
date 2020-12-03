package com.idnp.musicfit.models.services.trainingService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.idnp.musicfit.presenter.trainingControllerPresenter.TrainingControllerPresenter;

import java.util.ArrayList;

public class DBManager {
    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }
    public DBManager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dbHelper.close();
    }

    public void insert (String name){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper.TABLE1_COL_NAME,name);
        database.insert(DataBaseHelper.TABLE1_NAME,null,contentValue);
    }
    public ArrayList getElements(){
        database = dbHelper.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DataBaseHelper.TABLE1_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex(DataBaseHelper.TABLE1_COL_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }
    public Cursor fetch(){
        String [] columns = new String [] { DataBaseHelper.TABLE1_COL_ID,
                DataBaseHelper.TABLE1_COL_NAME};
        Cursor cursor = database.query(DataBaseHelper.TABLE1_NAME, columns, null, null, null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public int update (long id, String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.TABLE1_COL_NAME,name);
        int i = database.update(DataBaseHelper.TABLE1_NAME, contentValues,
                DataBaseHelper.TABLE1_COL_ID + " = " + id, null );
        return i;
    }
    public void delete (long id){
        database.delete(DataBaseHelper.TABLE1_NAME, DataBaseHelper.TABLE1_COL_ID + " = " + id, null);
    }
    public void deleteDB(Context c){
        c.deleteDatabase(DataBaseHelper.DB_NAME);
    }
    public void deleteTable(String name){
        name = DataBaseHelper.TABLE1_NAME;
        database.execSQL("DROP TABLE IF EXISTS " + name);
    }
}
