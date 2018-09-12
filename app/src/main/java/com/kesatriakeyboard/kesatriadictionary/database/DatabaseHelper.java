package com.kesatriakeyboard.kesatriadictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_dictionary";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_ENGLISH = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            DatabaseContract.TABLE_ENGLISH,
            DatabaseContract.TableColumns._ID,
            DatabaseContract.TableColumns.WORD,
            DatabaseContract.TableColumns.TRANSLATION
    );

    private static String CREATE_TABLE_INDONESIA = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            DatabaseContract.TABLE_INDONESIA,
            DatabaseContract.TableColumns._ID,
            DatabaseContract.TableColumns.WORD,
            DatabaseContract.TableColumns.TRANSLATION
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_INDONESIA);
        onCreate(db);
    }
}
