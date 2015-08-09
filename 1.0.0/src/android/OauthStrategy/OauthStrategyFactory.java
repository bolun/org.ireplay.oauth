package org.apache.cordova.oauth;

import org.apache.cordova.CordovaPlugin;

public class OauthStrategyFactory {
    public static OauthStrategy create(String authType, CordovaPlugin cordovaPlugin) throws OauthException {
        if(authType.equals("facebook")) {
            //return new FacebookOauthStrategy(callbackContext);
        }

        if(authType.equals("twitter")) {
            //return new TwitterOauthStrategy(callbackContext);
        }

        if(authType.equals("qzone")) {
            return new QzoneOauthStrategy(cordovaPlugin);
        }

        throw new OauthException("Unknown oauth type");
    }
}