package name.sevendus.cordova.personaly.interstitial;

import android.util.Log;

import ly.persona.sdk.listener;

import org.json.JSONException;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;

class InterstitialListener extends AdListener {
    private final InterstitialExecutor executor;

    InterstitialListener(InterstitialExecutor executor) {
        this.executor = executor;
    }

    @Override void onAdStartLoading() {
        Log.w("Personaly", "onAdStartLoading");
        executor.fireAdEvent("personaly.interstitial.events.LOAD_START");
        executor.fireAdEvent("onAdStartLoading");
    }

    @Override
    public void onAdLoaded() {
        Log.w("Personaly", "InterstitialAdLoaded");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.LOAD", data);
        executor.fireAdEvent("onAdLoaded", data);

        if (executor.shouldAutoShow()) {
            executor.showAd(true, null);
        }
    }

    @Override
    public void onAdShowed() {
        Log.w("Personaly", "InterstitialAdShown");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.SHOWED", data);
        executor.fireAdEvent("onAdShowed", data);
    }
    
    @Override
    public void onAdClicked() {
        Log.w("Personaly", "InterstitialAdClicked");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.CLICKED", data);
        executor.fireAdEvent("onAdClicked", data);
    }

    @Override
    public void onAdRewarded(Impression impression) {
        Log.w("Personaly", "InterstitialAdRewarded");
        int amount = impression.getRewardedAmount();
        JSONObject data = new JSONObject();
        try {
            data.put("amount", amount);
            data.put("adType", executor.getAdType());
            data.put("hasRewardedAmount", impression.hasRewardedAmount());
            data.put("isRewardedVideo", impression.isRewardedVideo());
            data.put("isRewardedPlayable", impression.isRewardedPlayable());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.REWARDED", data);
        executor.fireAdEvent("onAdRewarded", data)
    }

    @Override
    public void onAdFailed(Throwable throwable) {
        Log.w("Personaly", "InterstitialAdFailed");
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
            executor.fireAdEvent("personaly.interstitial.events.LOAD_FAIL", data);
            executor.fireAdEvent("onFailedToReceiveAd", data);
        }
    }
    
    @Override
    public void onAdLeftApplication() {
        Log.w("Personaly", "InterstitialAdLeftApplication");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.EXIT_APP", data);
        executor.fireAdEvent("onLeaveToAd", data);
    }

    @Override
    public void onAdClosed() {
        Log.w("Personaly", "InterstitialAdClose");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.interstitial.events.CLOSE", data);
        executor.fireAdEvent("onDismissAppWallAd", data);
        executor.destroy();
    }
}
