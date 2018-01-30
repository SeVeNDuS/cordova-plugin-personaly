package name.sevendus.cordova.personaly;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import ly.persona.sdk.Personaly;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import name.sevendus.cordova.personaly.interstitial.InterstitialExecutor;
import name.sevendus.cordova.personaly.rewardedvideo.RewardedVideoExecutor;
import name.sevendus.cordova.personaly.offerwall.OfferWallExecutor;
import name.sevendus.cordova.personaly.appwall.AppWallExecutor;
import name.sevendus.cordova.personaly.popupoffer.PopupOfferExecutor;

/**
 * This class represents the native implementation for the Personaly Cordova plugin.
 * This plugin can be used to request Personaly ads natively via the Personaly SDK.
 * The Personaly SDK is a dependency for this plugin.
 */
public class PersonalyPlugin extends CordovaPlugin {
    /**
     * Common tag used for logging statements.
     */
    private static final String TAG = "Personaly";

    public final PersonalyConfig config = new PersonalyConfig();

    private InterstitialExecutor interstitialExecutor = null;
    private RewardedVideoExecutor rewardedVideoExecutor = null;
    private OfferWallExecutor offerWallExecutor = null;
    private AppWallExecutor appWallExecutor = null;
    private PopupOfferExecutor popupOfferExecutor = null;

    private boolean isGpsAvailable = false;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        isGpsAvailable = (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(cordova.getActivity()) == ConnectionResult.SUCCESS);
        Log.w(TAG, String.format("isGooglePlayServicesAvailable: %s", isGpsAvailable ? "true" : "false"));
    }

    /**
     * This is the main method for the Personaly plugin.  All API calls go through here.
     * This method determines the action, and executes the appropriate call.
     *
     * @param action          The action that the plugin should execute.
     * @param inputs          The input parameters for the action.
     * @param callbackContext The callback context.
     * @return A PluginResult representing the result of the provided action.  A
     * status of INVALID_ACTION is returned if the action is not recognized.
     */
    @Override
    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
        if (interstitialExecutor == null) {
            interstitialExecutor = new InterstitialExecutor(this);
        }
        if (rewardedVideoExecutor == null) {
            rewardedVideoExecutor = new RewardedVideoExecutor(this);
        }
        if (offerWallExecutor == null) {
            offerWallExecutor = new OfferWallExecutor(this);
        }
        if (appWallExecutor == null) {
            appWallExecutor = new AppWallExecutor(this);
        }
        if (popupOfferExecutor == null) {
            popupOfferExecutor = new PopUpOfferExecutor(this);
        }

        PluginResult result = null;

        if (Actions.SET_OPTIONS.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            result = executeSetOptions(options, callbackContext);

        } else if (Actions.PREPARE_INTERSTITIAL.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            result = interstitialExecutor.prepareAd(options, callbackContext);

        } else if (Actions.SHOW_INTERSTITIAL.equals(action)) {
            boolean show = inputs.optBoolean(0);
            result = interstitialExecutor.showAd(show, callbackContext);

        } else if(Actions.IS_INTERSTITIAL_READY.equals(action)) {
            result = interstitialExecutor.isReady(callbackContext);

        } else if (Actions.SHOW_OFFERWALL.equals(action)) {
            boolean show = inputs.optBoolean(0);
            result = offerWallExecutor.showAd(show, callbackContext);

        } else if (Actions.SHOW_APPWALL.equals(action)) {
            boolean show = inputs.optBoolean(0);
            result = appWallExecutor.showAd(show, callbackContext);

        } else if (Actions.CREATE_REWARDED_VIDEO.equals(action)) {
            JSONObject options = inputs.optJSONObject(0);
            result = rewardedVideoExecutor.prepareAd(options, callbackContext);

        } else if (Actions.SHOW_REWARDED_VIDEO.equals(action)) {
            boolean show = inputs.optBoolean(0);
            result = rewardedVideoExecutor.showAd(show, callbackContext);

        } else if(Actions.IS_REWARDED_VIDEO_READY.equals(action)) {
            result = rewardedVideoExecutor.isReady(callbackContext);

        } else {
            Log.d(TAG, String.format("Invalid action passed: %s", action));
            result = new PluginResult(Status.INVALID_ACTION);
        }

        if (result != null) {
            callbackContext.sendPluginResult(result);
        }

        return true;
    }

    private PluginResult executeSetOptions(JSONObject options, CallbackContext callbackContext) {
        Log.w(TAG, "executeSetOptions");

        config.setOptions(options);

        Personaly.InitCallback initCallback = new Personaly.InitCallback() {
            @Override
            public void onSuccess() {
                if (plugin.config.getGender()) {
                    Personaly.CONFIG.setGender(plugin.config.getGender());
                }
                if (plugin.config.getDateOfBirth()) {
                    Personaly.CONFIG.setDateOfBirth(plugin.config.getDateOfBirth());
                }
                if (plugin.config.getAge()) {
                    Personaly.CONFIG.setAge(plugin.config.getAge());
                }
                if (plugin.config.getUserId()) {
                    Personaly.CONFIG.setUserId(plugin.config.getUserId());
                }
                callbackContext.success();
            }

            @Override
            public void onFailure(Throwable throwable) {
                // An error occurred while initializing

                if (throwable instanceof PersonaError) {
                    PersonaError error = (PersonaError) throwable;
                    int errorCode = error.getErrorCode();
                    String message = error.getMessage();
                    // Do something with errorCode if it needed
                }
            }
        };
        Personaly.init(this, plugin.config.getAppId(), initCallback);

        return null;
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        isGpsAvailable = (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(cordova.getActivity()) == ConnectionResult.SUCCESS);
    }

    @Override
    public void onDestroy() {
        if (interstitialExecutor != null) {
            interstitialExecutor.destroy();
            interstitialExecutor = null;
        }
        if (rewardedVideoExecutor != null) {
            rewardedVideoExecutor.destroy();
            rewardedVideoExecutor = null;
        }
        if (offerWallExecutor != null) {
            offerWallExecutor.destroy();
            offerWallExecutor = null;
        }
        if (appWallExecutor != null) {
            appWallExecutor.destroy();
            appWallExecutor = null;
        }
        if (popupOfferExecutor != null) {
            popupOfferExecutor.destroy();
            popupOfferExecutor = null;
        }
        super.onDestroy();
    }

    @NonNull
    private String getDeviceId() {
        // This will request test ads on the emulator and deviceby passing this hashed device ID.
        String ANDROID_ID = Settings.Secure.getString(cordova.getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        return md5(ANDROID_ID).toUpperCase();
    }

    private static String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}
