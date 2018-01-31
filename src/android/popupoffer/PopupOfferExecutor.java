package name.sevendus.cordova.personaly.popupoffer;

import android.util.Log;

import ly.persona.sdk.Personaly;
import ly.persona.sdk.PopupOfferAd;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;
import name.sevendus.cordova.personaly.PersonalyPlugin;
import name.sevendus.cordova.personaly.PersonalyConfig;

public class PopupOfferExecutor extends AbstractExecutor {
    /**
     * The popupoffer ad to display to the user.
     */
    private PopupOfferAd popupOfferAd;

    public PopupOfferExecutor(PersonalyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "popupoffer";
    }

    public PluginResult prepareAd(JSONObject options, CallbackContext callbackContext) {
        PersonalyConfig config = plugin.config;
        CordovaInterface cordova = plugin.cordova;
        config.setPopupOfferOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;
                CordovaInterface cordova = plugin.cordova;

                destroy();
                popupOfferAd = PopupOfferAd.get(cordova.getActivity());
                popupOfferAd.get(config.getPopupOfferAdUnitId());
                popupOfferAd.setListener(new PopupOfferListener(PopupOfferExecutor.this));
                Log.i("popup", config.getPopupOfferAdUnitId());
                popupOfferAd.load();
                delayCallback.success();
            }
        });
        return null;
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        if (offerwallAd == null) {
            return new PluginResult(PluginResult.Status.ERROR, "popupOfferAd is null, call createPopupOfferView first.");
        }
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;

                if (popupOfferAd.isReady()) {
                    popupOfferAd.show();
                    if (callbackContext != null) {
                        callbackContext.success();
                    }
                } else if (!config.autoShowPopupOffer) {
                    if (callbackContext != null) {
                        callbackContext.error("PopupOffer not ready yet");
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
                if (popupOfferAd != null && popupOfferAd.isReady()) {
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
        if (popupOfferAd != null) {
            popupOfferAd.setAdListener(null);
            popupOfferAd.dispose(config.getPopupOfferAdUnitId());
            popupOfferAd = null;
        }
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowPopupOffer;
    }
}
