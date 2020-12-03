package com.idnp.musicfit.models.services.trainingService;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Database name
    public static final String DB_NAME = "TRAINING";
    public static final int DB_VERSION = 1;

    //Database tables
    public static final String TABLE1_NAME = "REPORT";

    //Table columns
    public static final String TABLE1_COL_ID = "id";
    public static final String TABLE1_COL_NAME = "name";

    //CREATING TABLES QUERY
    private static final String CREATE_TABLE1 = "create table " + TABLE1_NAME + " (" + TABLE1_COL_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE1_COL_NAME + " TEXT NOT NULL);";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        onCreate(db);
    }
}
