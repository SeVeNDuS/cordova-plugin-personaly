package name.sevendus.cordova.personaly.appwall;

import android.util.Log;

import ly.persona.sdk.listener;

import org.json.JSONException;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;

class AppWallListener extends AdListener {
    private final AppWallExecutor executor;

    AppWallListener(AppWallExecutor executor) {
        this.executor = executor;
    }

    @Override void onAdStartLoading() {
        Log.w("Personaly", "onAdStartLoading");
        executor.fireAdEvent("personaly.appwall.events.LOAD");
        executor.fireAdEvent("onAdStartLoading");
    }

    @Override
    public void onAdLoaded() {
        Log.w("Personaly", "AppWallAdLoaded");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.LOAD", data);
        executor.fireAdEvent("onAdLoaded", data);
    }

    @Override
    public void onAdShowed() {
        Log.w("Personaly", "AppWallAdShown");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.SHOWED", data);
        executor.fireAdEvent("onAdShowed", data);
    }
    
    @Override
    public void onAdClicked() {
        Log.w("Personaly", "AppWlAdClicked");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.CLICKED", data);
        executor.fireAdEvent("onAdClicked", data);
    }

    @Override
    public void onAdRewarded(Impression impression) {
        Log.w("Personaly", "AppWAdRewarded");
        int amount = impression.getRewardedAmount();
        JSONObject data = new JSONObject();
        try {
            data.put("amount", amount);
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.REWARDED", data);
        executor.fireAdEvent("onAdRewarded", data)
    }

    @Override
    public void onAdFailed(Throwable throwable) {
        Log.w("Personaly", "AppWallAdFailed");
        if (throwable instanceof PersonaError) {
            PersonaError error = (PersonaError) throwable;
            int errorCode = error.getErrorCode();
            JSONObject data = new JSONObject();
            try {
                data.put("error", errorCode);
                data.put("reason", AbstractExecutor.getErrorReason(errorCode));
                data.put("message", error.getMessage());
                data.put("adType", executor.getAdType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            executor.fireAdEvent("personaly.appwall.events.LOAD_FAIL", data);
            executor.fireAdEvent("onFailedToReceiveAd", data);
        }
    }
    
    @Override
    public void onAdLeftApplication() {
        Log.w("Personaly", "AppWallAdLeftApplication");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.EXIT_APP", data);
        executor.fireAdEvent("onLeaveToAd", data);
    }

    @Override
    public void onAdClosed() {
        Log.w("Personaly", "AppWallAdClose");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.appwall.events.CLOSE", data);
        executor.fireAdEvent("onDismissAppWallAd", data);
        executor.destroy();
    }
}
