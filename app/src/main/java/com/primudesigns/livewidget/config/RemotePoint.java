package com.primudesigns.livewidget.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RemotePoint {

    public RemotePoint() {
    }

    //TODO 7 : Switch to OkHtttp or Retrofit

    public static String getJsonString(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int statusCode = urlConnection.getResponseCode();

        int HTTP_OK = 200;
        if (statusCode == HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }

            return response.toString();

        } else {

            return null;

        }

    }

}
