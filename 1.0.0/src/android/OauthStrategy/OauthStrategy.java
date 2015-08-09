package org.apache.cordova.oauth;

import org.apache.cordova.CallbackContext;

public abstract class OauthStrategy {
    public abstract void login(CallbackContext callbackContext);
}