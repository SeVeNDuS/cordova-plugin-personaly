package name.sevendus.cordova.personaly.interstitial;

import android.util.Log;

import ly.persona.sdk.Personaly;
import ly.persona.sdk.CampaignAd;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;
import name.sevendus.cordova.personaly.PersonalyPlugin;
import name.sevendus.cordova.personaly.PersonalyConfig;

public class InterstitialExecutor extends AbstractExecutor {
    /**
     * The interstitial ad to display to the user.
     */
    private CampaignAd interstitialAd;

    public InterstitialExecutor(PersonalyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "interstitial";
    }

    public PluginResult prepareAd(JSONObject options, CallbackContext callbackContext) {
        PersonalyConfig config = plugin.config;
        CordovaInterface cordova = plugin.cordova;
        config.setInterstitialOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;
                CordovaInterface cordova = plugin.cordova;

                destroy();
                interstitialAd = CampaignAd.get(config.getInterstitialAdUnitId());
                interstitialAd.setListener(new InterstitialListener(InterstitialExecutor.this));
                Log.i("interstitial", config.getInterstitialAdUnitId());
                interstitialAd.load();
                delayCallback.success();
            }
        });
        return null;
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        if (interstitialAd == null) {
            return new PluginResult(PluginResult.Status.ERROR, "interstitialAd is null, call createInterstitialView first.");
        }
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;

                if (interstitialAd.isShowing()) {
                    if (callbackContext != null) {
                        callbackContext.error("Interstital already shown");
                    }
                } else if (interstitialAd.isReady()) {
                    interstitialAd.show();
                    if (callbackContext != null) {
                        callbackContext.success();
                    }
                } else if (!config.autoShowInterstitial) {
                    if (callbackContext != null) {
                        callbackContext.error("Interstital not ready yet");
                    }
                }
            }
        });

        return null;
    }

    public PluginResult isReady(final CallbackContext callbackContext) {
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (interstitialAd != null && interstitialAd.isReady()) {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
                } else {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
                }
            }
        });

        return null;
    }

    @Override
    public void destroy() {
        if (interstitialAd != null) {
            interstitialAd.setAdListener(null);
            interstitialAd.dispose(config.getInterstitialAdUnitId());
            interstitialAd = null;
        }
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowInterstitial;
    }
}
