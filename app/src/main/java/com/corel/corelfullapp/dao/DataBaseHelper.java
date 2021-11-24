package com.corel.corelfullapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

import com.corel.corelfullapp.entites.Product;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "project.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "DataBaseHelper";

    private static DataBaseHelper dataBaseHelper;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DataBaseHelper getInstance(Context context){
        if (dataBaseHelper == null){
            dataBaseHelper = new DataBaseHelper(context);
        }
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate; avant la creation");
        sqLiteDatabase.execSQL(Product.CREATE_TABLE);
        Log.d(TAG, "onCreate; après la creation");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
