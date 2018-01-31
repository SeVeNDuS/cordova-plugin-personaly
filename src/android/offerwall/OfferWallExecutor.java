package name.sevendus.cordova.personaly.offerwall;

import android.util.Log;

import ly.persona.sdk.Personaly;
import ly.persona.sdk.OfferWallAd;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;
import name.sevendus.cordova.personaly.PersonalyPlugin;
import name.sevendus.cordova.personaly.PersonalyConfig;

public class OfferWallExecutor extends AbstractExecutor {
    /**
     * The offerWall ad to display to the user.
     */
    private OfferWallAd offerWallAd;

    public OfferWallExecutor(PersonalyPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "offerwall";
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        PersonalyConfig config = plugin.config;
        CordovaInterface cordova = plugin.cordova;

        config.setOfferWallOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;
                CordovaInterface cordova = plugin.cordova;

                destroy();
                offerWallAd = OfferWall.get(config.getOfferWallAdUnitId());
                offerWallAd.setListener(new OfferWallListener(OfferWallExecutor.this));
                Log.w("offerwall", config.getOfferWallAdUnitId());
                offerWallAd.show();
                delayCallback.success();
            }
        });
        return null;
    }

    @Override
    public void destroy() {
        if (offerWallAd != null) {
            offerWallAd.setAdListener(null);
            offerWallAd.dispose(config.getOfferWallAdUnitId());
            offerWallAd = null;
        }
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowOfferWall;
    }
}
