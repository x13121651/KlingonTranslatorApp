package me.ronanlafford.klingontranslatorapp;

/**
 * Created by Ronan on 29/11/2016.
 */

public class Translation {
    private int id;
    private String language;
    private String message_request;
    private String translated_response;

    //Create constructor for the 4 fields to be used in database
    public Translation(int id, String language, String message_request, String translated_response) {
        this.id = id;
        this.language = language;
        this.message_request = message_request;
        this.translated_response = translated_response;

    }

    //Empty constructor
    public Translation() {

    }


    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMessageRequest() {
        return message_request;
    }

    public void setMessageRequest(String message_request) {
        this.message_request = message_request;
    }

    public String getTranslatedResponse() {
        return translated_response;
    }

    public void setTranslatedResponse(String translated_response) {
        this.translated_response = translated_response;
    }

    //Override the String method to get the variables into the listview as proper strings
    @Override
    public String toString() {
        return language + message_request + translated_response;
    }

}

