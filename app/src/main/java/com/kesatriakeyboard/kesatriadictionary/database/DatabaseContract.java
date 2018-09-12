package com.kesatriakeyboard.kesatriadictionary.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_ENGLISH = "table_english";

    static String TABLE_INDONESIA = "table_indonesia";

    static final class TableColumns implements BaseColumns {
        static String WORD = "word";
        static String TRANSLATION = "translation";
    }
}
