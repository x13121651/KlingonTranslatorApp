package me.ronanlafford.klingontranslatorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronan on 29/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Set the Database Version
    private static final int DATABASE_VERSION = 1;

    // Set the Database Name
    private static final String DATABASE_NAME = "translationManager";

    // Set the Translations table name
    private static final String TABLE_TRANSLATIONS = "translations";

    // Set the Translations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_MESSAGE_REQUEST = "message_request";
    private static final String KEY_TRANSLATED_RESPONSE = "translated_response";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSLATIONS_TABLE = "CREATE TABLE " + TABLE_TRANSLATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LANGUAGE + " TEXT,"
                + KEY_MESSAGE_REQUEST + " TEXT," + KEY_TRANSLATED_RESPONSE + " TEXT" + ")";
        db.execSQL(CREATE_TRANSLATIONS_TABLE);
    }

    // Upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSLATIONS);

        // Create tables again
        onCreate(db);
    }


    // Adding new translation
    public void addTranslation(Translation translation) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create new content values for translation record
        ContentValues values = new ContentValues();
        values.put(KEY_LANGUAGE, translation.getLanguage());
        values.put(KEY_MESSAGE_REQUEST, translation.getMessageRequest());
        values.put(KEY_TRANSLATED_RESPONSE, translation.getTranslatedResponse());

        // Inserting the record with values to the translation table
        db.insert(TABLE_TRANSLATIONS, null, values);
        db.close(); // Closing database connection
    }


    // To get all the Translations
    public List<Translation> getAllTranslations() {
        List<Translation> translationList = new ArrayList<>();

        // Select All From Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSLATIONS;

        //initialise the SQLite database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows and add each one to the list
        if (cursor.moveToFirst()) {
            do {
                //Instantiate the Translation class and pass in the values
                Translation translation = new Translation();
                translation.setId(Integer.parseInt(cursor.getString(0)));
                translation.setLanguage(cursor.getString(1));
                translation.setMessageRequest(cursor.getString(2));
                translation.setTranslatedResponse(cursor.getString(3));

                // Add the Translation to list
                translationList.add(translation);
            } while (cursor.moveToNext());
        }

        // return translation list
        return translationList;
    }


    // Delete the translation table
    public void deleteTranslation() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSLATIONS, null, null);
        db.close();
    }

}