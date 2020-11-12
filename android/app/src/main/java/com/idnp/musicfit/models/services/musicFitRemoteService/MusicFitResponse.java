package com.idnp.musicfit.models.services.musicFitRemoteService;

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
}
