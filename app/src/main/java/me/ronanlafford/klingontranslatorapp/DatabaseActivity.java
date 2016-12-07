package me.ronanlafford.klingontranslatorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Create a listview for the activity
        listView = (ListView) findViewById(R.id.listView);
        //initialise a new handler
        db = new DatabaseHandler(DatabaseActivity.this);
        //Create a List Array of Translations
        List<Translation> translations = db.getAllTranslations();
        //Create custom arrayAdapter for attaching the list of translations                                                           android.R.layout.simple_list_item_1,
        TranslationArrayAdapter translationArrayAdapter = new TranslationArrayAdapter(getApplicationContext(), translations);
        //set the adapter to the list view for displaying the list items
        listView.setAdapter(translationArrayAdapter);

    }

}

