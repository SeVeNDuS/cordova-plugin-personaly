package name.sevendus.cordova.personaly.rewardvideo;

import android.os.Bundle;
import android.util.Log;

import ly.persona.sdk.Personaly;
import ly.persona.sdk.CampaignAd;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;
import name.sevendus.cordova.personaly.Personaly;

public class RewardVideoExecutor extends AbstractExecutor {
    /**
     * RewardVideo
     */
    private RewardedVideoAd rewardedVideoAd;
    boolean isRewardedVideoLoading = false;
    final Object rewardedVideoLock = new Object();

    public RewardVideoExecutor(Personaly plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "rewardedvideo";
    }

    public PluginResult prepareAd(JSONObject options, CallbackContext callbackContext) {
        CordovaInterface cordova = plugin.cordova;
        plugin.config.setRewardedVideoOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CordovaInterface cordova = plugin.cordova;
                destroy();

                rewardedVideoAd = CampaignAd.get(plugin.config.getRewardedVideoAdUnitId());
                rewardedVideoAd.setListener(new RewardedVideoListener(RewardedVideoExecutor.this));
                Log.w("rewardedvideo", plugin.config.getRewardedVideoAdUnitId());
                rewardedVideoAd.load();
                delayCallback.success();
            }
        });
        return null;
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        if (rewardedVideoAd == null) {
            return new PluginResult(PluginResult.Status.ERROR, "rewardedVideoAd is null, call createRewardVideo first.");
        }
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (rewardedVideoAd instanceof RewardedVideoAd) {
                    RewardedVideoAd rvad = rewardedVideoAd;
                    if (rvad.isReady()) {
                        rvad.show();
                    }
                }

                if (callbackContext != null) {
                    callbackContext.success();
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
                if (rewardedVideoAd != null && rewardedVideoAd.isReady()) {
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
        if (rewardedVideoAd == null) {
            return;
        }
        rewardedVideoAd.setRewardedVideoAdListener(null);
        rewardedVideoAd.dispose(plugin.config.getRewardedVideoAdUnitId());
        rewardedVideoAd = null;
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowRewardedVideo;
    }
}
