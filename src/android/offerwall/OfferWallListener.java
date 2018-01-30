package name.sevendus.cordova.personaly.offerwall;

import android.util.Log;

import ly.persona.sdk.listener;

import org.json.JSONException;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;

class OfferWallListener extends AdListener {
    private final OfferWallExecutor executor;

    OfferWallListener(OfferWallExecutor executor) {
        this.executor = executor;
    }

    @Override void onAdStartLoading() {
        Log.w("Personaly", "onAdStartLoading");
        executor.fireAdEvent("personaly.offerwall.events.START_LOAD");
        executor.fireAdEvent("onAdStartLoading");
    }

    @Override
    public void onAdLoaded() {
        Log.w("Personaly", "OfferWallAdLoaded");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.LOAD", data);
        executor.fireAdEvent("onAdLoaded", data);
    }

    @Override
    public void onAdShowed() {
        Log.w("Personaly", "OfferWallAdShown");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.SHOWED", data);
        executor.fireAdEvent("onAdShowed", data);
    }
    
    @Override
    public void onAdClicked() {
        Log.w("Personaly", "OfferWallAdClicked");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.CLICKED", data);
        executor.fireAdEvent("onAdClicked", data);
    }

    @Override
    public void onAdRewarded(Impression impression) {
        Log.w("Personaly", "OfferWallAdRewarded");
        int amount = impression.getRewardedAmount();
        JSONObject data = new JSONObject();
        try {
            data.put("amount", amount);
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.REWARDED", data);
        executor.fireAdEvent("onAdRewarded", data)
    }

    @Override
    public void onAdFailed(Throwable throwable) {
        Log.w("Personaly", "OfferWallAdFailed");
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
            executor.fireAdEvent("personaly.offerwall.events.LOAD_FAIL", data);
            executor.fireAdEvent("onFailedToReceiveAd", data);
        }
    }
    
    @Override
    public void onAdLeftApplication() {
        Log.w("Personaly", "OfferWallAdLeftApplication");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.EXIT_APP", data);
        executor.fireAdEvent("onLeaveToAd", data);
    }

    @Override
    public void onAdClosed() {
        Log.w("Personaly", "OfferWallAdClose");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.offerwall.events.CLOSE", data);
        executor.fireAdEvent("onDismissOfferWallAd", data);
        executor.destroy();
    }
}
