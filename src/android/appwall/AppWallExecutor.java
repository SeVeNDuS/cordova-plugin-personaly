package name.sevendus.cordova.personaly.appwall;

import android.util.Log;

import ly.persona.sdk.Personaly;
import ly.persona.sdk.AppWallAd;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;
import name.sevendus.cordova.personaly.Personaly;
import name.sevendus.cordova.personaly.PersonalyConfig;

public class AppWallExecutor extends AbstractExecutor {
    /**
     * The AppWall ad to display to the user.
     */
    private AppWallAd appWallAd;

    public AppWallExecutor(Personaly plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "appwall";
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        PersonalyConfig config = plugin.config;
        CordovaInterface cordova = plugin.cordova;

        config.setAppWallOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PersonalyConfig config = plugin.config;
                CordovaInterface cordova = plugin.cordova;

                destroy();
                appWallAd = AppWall.get(config.getAppWallAdUnitId());
                appWallAd.setListener(new AppWallListener(AppWallExecutor.this));
                Log.w("appwall", config.getAppWallAdUnitId());
                appWallAd.show();
                delayCallback.success();
            }
        });
        return null;
    }

    @Override
    public void destroy() {
        if (appWallAd != null) {
            appWallAd.setAdListener(null);
            appWallAd.dispose(config.getAppWallAdUnitId());
            appWallAd = null;
        }
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowAppWall;
    }
}
