package org.apache.cordova.oauth;

import android.app.Activity;
import android.content.Context;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public class QzoneOauthStrategy extends OauthStrategy {
    CordovaPlugin _cordovaPlugin;
    Tencent _tencent;

    public QzoneOauthStrategy(CordovaPlugin cordovaPlugin) {
        String appid = getPreference("oauth-qq-appid", cordovaPlugin);
        Context context = cordovaPlugin.cordova.getActivity().getApplicationContext();

        _cordovaPlugin = cordovaPlugin;
        _tencent = Tencent.createInstance(appid, context);
    }

    public void login(final CallbackContext callbackContext) {
        final IUiListener listerer = new IUiListener() {
            @Override
            public void onComplete(Object response) {
                _cordovaPlugin.webView.sendPluginResult(new PluginResult(PluginResult.Status.OK, getLoginResult(response)), callbackContext.getCallbackId());
            }

            @Override
            public void onError(UiError error) {
                _cordovaPlugin.webView.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Login failed (Code: " + error.errorCode + ")"), callbackContext.getCallbackId());
            }

            @Override
            public void onCancel() {
                _cordovaPlugin.webView.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Login has been canceled"), callbackContext.getCallbackId());
            }
        };

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                _tencent.login(_cordovaPlugin.cordova.getActivity(), "all", listerer);
            }
        };

        _cordovaPlugin.cordova.getActivity().runOnUiThread(runnable);
        _cordovaPlugin.cordova.setActivityResultCallback(_cordovaPlugin);
    }

    protected JSONObject getLoginResult(Object object) {
        try {
            JSONObject input = (JSONObject)object;
            JSONObject output = new JSONObject();

            output.put("oauth", "qzone");
            output.put("openid", input.getString("openid"));
            output.put("accessToken", input.getString("access_token"));
            output.put("expiresIn", input.getString("expires_in"));

            return output;
        } catch (JSONException ex) {
            return null;
        }
    }

    private String getPreference(String key, CordovaPlugin cordovaPlugin) {
        return cordovaPlugin.webView.getPreferences().getString(key, "");
    }
}