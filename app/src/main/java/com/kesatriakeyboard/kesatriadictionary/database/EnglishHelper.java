package com.kesatriakeyboard.kesatriadictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.kesatriakeyboard.kesatriadictionary.model.WordModel;

import java.util.ArrayList;

import static com.kesatriakeyboard.kesatriadictionary.database.DatabaseContract.TABLE_ENGLISH;
import static com.kesatriakeyboard.kesatriadictionary.database.DatabaseContract.TableColumns._ID;
import static com.kesatriakeyboard.kesatriadictionary.database.DatabaseContract.TableColumns.WORD;
import static com.kesatriakeyboard.kesatriadictionary.database.DatabaseContract.TableColumns.TRANSLATION;

public class EnglishHelper {

    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public EnglishHelper(Context context) {
        this.context = context;
    }

    public EnglishHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<WordModel> getAllData() {
        Cursor cursor = db.query(TABLE_ENGLISH, null, null, null, null, null, _ID + "ASC", null);
        cursor.moveToFirst();
        ArrayList<WordModel> arrayList = new ArrayList<>();
        WordModel model;
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                String word = cursor.getString(cursor.getColumnIndexOrThrow(WORD));
                String translation = cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION));

                model = new WordModel(id, word, translation);
                arrayList.add(model);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<WordModel> getDataByName(String query) {
        Cursor cursor = db.query(TABLE_ENGLISH, null, WORD + " LIKE ?", new String[]{query + "%"}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<WordModel> arrayList = new ArrayList<>();
        WordModel model;
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                String word = cursor.getString(cursor.getColumnIndexOrThrow(WORD));
                String translation = cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION));

                model = new WordModel(id, word, translation);
                arrayList.add(model);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public long insert(WordModel model) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORD, model.getWord());
        initialValues.put(TRANSLATION, model.getTranslation());
        return db.insert(TABLE_ENGLISH, null, initialValues);
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccess() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void insertTransaction(WordModel model) {
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES(?, ?);",
                TABLE_ENGLISH,
                WORD,
                TRANSLATION);

        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, model.getWord());
        stmt.bindString(2, model.getTranslation());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(WordModel model) {
        ContentValues args = new ContentValues();
        args.put(WORD, model.getWord());
        args.put(TRANSLATION, model.getTranslation());
        return db.update(TABLE_ENGLISH, args, _ID + " = '" + model.getId() + "'", null);
    }

    public int delete(int id) {
        return db.delete(TABLE_ENGLISH, _ID + " = '" + id + "'", null);
    }
}
