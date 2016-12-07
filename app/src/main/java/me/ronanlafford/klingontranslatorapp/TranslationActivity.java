package me.ronanlafford.klingontranslatorapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static me.ronanlafford.klingontranslatorapp.R.id.editText;


public class TranslationActivity extends AppCompatActivity {

    //Declare variables
    EditText transmission;
    TextView responseView;
    Spinner spinnerNames;
    String[] languages = {"klingon", "vulcan", "yoda", "sith", "gungan", "huttese", "mandalorian", "aurebesh", "cheunh", "cockney", "jive", "morse"};
    String choice;
    FloatingActionButton myFabSave;
    FloatingActionButton myFabView;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialise variables
        transmission = (EditText) findViewById(editText);

        responseView = (TextView) findViewById(R.id.textView3);

        spinnerNames = (Spinner) findViewById(R.id.spinner);

        myFabSave = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        myFabView = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

        //Create adapter for attaching the list of languages to the spinner
        ArrayAdapter<String> textAdapterForSpinner = new ArrayAdapter<>(this, R.layout.spinner_item, languages);
        //attach the adapter to the spinner
        textAdapterForSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerNames.setAdapter((textAdapterForSpinner));

        //Show toast saying which language is selected bu the user and set variable to the string value
        spinnerNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Language selected: \n" + choice, Toast.LENGTH_SHORT).show();

            }

            //Show toast saying "You did not select a language!" if nothing is selected
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "You did not select a language!", Toast.LENGTH_SHORT).show();
            }
        });

        //This is the button to call the async task to begin the translation
        Button sendButton = (Button) findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrieveTranslationTask().execute();

            }
        });

        //Initialise an instance of the database handler
        final DatabaseHandler db = new DatabaseHandler(this);
        //Floating action button to set the set instantiate an set the values of a Translation to add to the translation list
        myFabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int idTest = id;
                String choiceTest = choice;
                String messageTest = transmission.getText().toString();
                String translatedTest = responseView.getText().toString();

                //db.deleteTranslation(); used for testing only - will not be deleting translations in app

                // Instantiate Translation and set values
                Translation translation = new Translation();
                translation.setId(idTest);
                translation.setLanguage(choiceTest);
                translation.setMessageRequest(messageTest);
                translation.setTranslatedResponse(translatedTest);

                // Ad the translation to the table
                db.addTranslation(translation);

                // Toast to state translation was added
                Toast.makeText(getApplicationContext(), "Translation added!", Toast.LENGTH_SHORT).show();
                //Create an arraylist of translations from the records in the table
                List<Translation> translations = db.getAllTranslations();

                //for testing and checking variables are correct in the logcat
                for (Translation tn : translations) {
                    String log = "Id:   " + tn.getId() + ",\nLanguage: " + tn.getLanguage() + " ,\nMessage:    " + tn.getMessageRequest() + " ,\nTranslation:    " + tn.getTranslatedResponse();

                    // Writing Translations to log
                    Log.i("INFO", log);

                }
            }
        });

        //Floating action button to move t the database activity to view the records saved to date
        myFabView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TranslationActivity.this, DatabaseActivity.class);
                startActivity(intent);
            }
        });


    }


    // Sub classing the AsyncTask which takes the 3 parameters <Void, Void, String>
    class RetrieveTranslationTask extends AsyncTask<Void, Void, String> {
        String message = transmission.getText().toString();
        String endpoint = choice + ".json?text=";

        //create the API url from adding the url, language choice and message to be sent for translation
        String API_URL = "http://api.funtranslations.com/translate/" + endpoint + message;

        private Exception exception;

        //Show a toast to tell the user the message is being translated  (Parameter 1 - void)
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Translating...", Toast.LENGTH_LONG).show();
        }

        //  (Parameter 2 - void)
        protected String doInBackground(Void... urls) {

            try {
                //create a instanc of URL class using the API variable
                URL url = new URL(API_URL);
                //encode the url to ensure all spaces are remove and it is recognised by the browser
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                url = uri.toURL();
                //Create and open the HTTP Connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    //Use buffered reader and string builder to retrieve the returned data line by line.
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    // keep reading until it is all received
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    //close the reader
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    //close the connection
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        //(Parameter 3 - String)
        protected void onPostExecute(String response) {

            try {
                //Create a json object from the response data
                JSONObject jObject = new JSONObject(response);
                //move through the object down the hierarchy to get to the required string
                String translated = jObject.getJSONObject("contents").getString("translated");
                //Display the translated text in the TextView
                responseView.setText(translated);
                // check to confirm what was returned in the logcat
                Log.i("INFO", translated);

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "There was an issue with the selected language...Please choose another!", Toast.LENGTH_LONG).show();
                throw new RuntimeException(e);
            }
        }


    }
}

