package org.apache.cordova.oauth;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

public class Oauth extends CordovaPlugin {
    OauthStrategy _oauthStrategy;

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if(action.equals("login")) {
            login(args.getString(0), callbackContext);
        }

        return true;
    }

    public void login(String oauthType, CallbackContext callbackContext) {
        try {
            _oauthStrategy = OauthStrategyFactory.create(oauthType, this);
            _oauthStrategy.login(callbackContext);
        } catch (OauthException ex) {
            callbackContext.error(ex.getMessage());
        }
    }
}