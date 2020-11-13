package com.idnp.musicfit.models.services.musicFitRemoteService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MusicFitResponse {

    private String body;
    private int requestCode;
    private Exception exception;

    public MusicFitResponse(String body, int requestCode, Exception exception) {
        this.body = body;
        this.requestCode = requestCode;
        this.exception = exception;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(MusicFitException exception) {
        this.exception = exception;
    }

    public void throwException() throws Exception {
        if (this.exception != null)
            throw this.exception;
    }

    public String getErrorBody() throws JSONException, MusicFitException {
        JSONObject jsonError = new JSONObject(this.getBody());
        for (Iterator<String> it = jsonError.keys(); it.hasNext(); ) {
            String key = it.next();
            return  key.toUpperCase() + ": " + (new JSONArray(jsonError.getString(key)).get(0));
        }
        return null;
    }
}
