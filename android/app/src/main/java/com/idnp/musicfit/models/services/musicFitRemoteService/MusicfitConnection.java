package com.idnp.musicfit.models.services.musicFitRemoteService;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MusicfitConnection extends AsyncTask<String,Void,MusicFitResponse> {

    public static String METHOD_POST = "POST";

    private JSONObject params;


    public void setData(JSONObject params){
        this.params = params;
    }

    @Override
    protected MusicFitResponse doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        URL url;
        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(strings[1]);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            DataOutputStream localDataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            localDataOutputStream.writeBytes(this.params.toString());
            localDataOutputStream.flush();
            localDataOutputStream.close();

            MusicFitResponse response = new MusicFitResponse("", httpURLConnection.getResponseCode(), null);

           // httpURLConnection.connect();
            InputStream stream = httpURLConnection.getErrorStream();
            if (stream == null) {
                stream = httpURLConnection.getInputStream();
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT){
                return response;
            }
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\Z");
            String response_body = scanner.next();
            response.setBody(response_body);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return new MusicFitResponse(null, 0, e);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
    }
}
