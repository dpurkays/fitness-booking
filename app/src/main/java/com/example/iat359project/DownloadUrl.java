package com.example.iat359project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/*
This class supports Google Places API.
Downloads the url from the web to get information of nearby searches.

Due to the cost associate to Google Places API, this class is not used.
 */
public class DownloadUrl {

    public String readUrl(String myUrl) throws IOException{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            //read each line
            String line ="";
            while((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            inputStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
}
