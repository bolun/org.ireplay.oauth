package org.apache.cordova.oauth;

public class OauthException extends Exception {
    String message;

    public OauthException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}