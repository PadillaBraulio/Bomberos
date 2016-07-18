package project.bomberos;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/07/16.
 */
public class RequestEmergency {

    private final static String DOMAIN = "https://webserversosapp-brauliojuancarlos.c9users.io/webservices/getEmergencyByAmbulance?" ;
    private final String ambulanceNumber;

    public RequestEmergency(String ambulanceNumber) {
        this.ambulanceNumber = ambulanceNumber;
    }

    public  String requestEmergencies(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;
        try {
            URL url = new URL( DOMAIN  +
                    "Ambulanceid=" + ambulanceNumber
                    );

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return "";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                return "";
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("request", "Error of IO", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("request", "Error closing stream", e);
                }
            }
        }
        return forecastJsonStr;
    }
    public  List<MainActivity.DataEmergency> readJson(String jsonStr){

        try {
            Log.i("info",jsonStr);
            JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(jsonStr.getBytes("UTF-8"))));
            return  readMessagesArray(reader);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<MainActivity.DataEmergency> readMessagesArray(JsonReader reader) throws IOException {
        List<MainActivity.DataEmergency> data = new ArrayList<MainActivity.DataEmergency>();

        reader.beginArray();
        while (reader.hasNext()) {
            data.add(readMessage(reader));
        }
        reader.endArray();
        reader.close();
        return data;
    }
    private MainActivity.DataEmergency readMessage(JsonReader reader) throws IOException {
        String id = "";
        String latitude = "";
        String longitude = "";
        String phone = "";
        String date = "";
        String ambulance = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("_id")) {
                id = reader.nextString();
            } else if (name.equals("latitude")) {
                latitude = reader.nextString();
            } else if (name.equals("longitude")) {
                longitude = reader.nextString();
            } else if (name.equals("telefone")) {
                phone = reader.nextString();
            } else if (name.equals("date")) {
                date = reader.nextString();
            } else if (name.equals("ambulance")) {
                ambulance = reader.nextString();
            }else{
                reader.nextString();
            }
        }
        reader.endObject();
        return new MainActivity.DataEmergency(id, latitude, longitude, phone, date, ambulance);
    }

}

