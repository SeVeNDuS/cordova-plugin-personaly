package name.sevendus.cordova.personaly.popup;

import android.util.Log;

import ly.persona.sdk.listener;

import org.json.JSONException;
import org.json.JSONObject;

import name.sevendus.cordova.personaly.AbstractExecutor;

class PopupOfferListener extends AdListener {
    private final PopupOfferxecutor executor;

    PopupOfferListener(PopupOfferExecutor executor) {
        this.executor = executor;
    }

    @Override void onAdStartLoading() {
        Log.w("Personaly", "onAdStartLoading");
        executor.fireAdEvent("personaly.popupoffer.events.LOAD_START");
        executor.fireAdEvent("onAdStartLoading");
    }

    @Override
    public void onAdLoaded() {
        Log.w("Personaly", "PopupOfferAdLoaded");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.LOAD", data);
        executor.fireAdEvent("onAdLoaded", data);

        if (executor.shouldAutoShow()) {
            executor.showAd(true, null);
        }
    }

    @Override
    public void onAdShowed() {
        Log.w("Personaly", "PopupOfferAdShown");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.SHOWED", data);
        executor.fireAdEvent("onAdShowed", data);
    }
    
    @Override
    public void onAdClicked() {
        Log.w("Personaly", "PopupOfferAdClicked");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.CLICKED", data);
        executor.fireAdEvent("onAdClicked", data);
    }

    @Override
    public void onAdRewarded(Impression impression) {
        Log.w("Personaly", "PopupOfferAdRewarded");
        int amount = impression.getRewardedAmount();
        JSONObject data = new JSONObject();
        try {
            data.put("amount", amount);
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.REWARDED", data);
        executor.fireAdEvent("onAdRewarded", data);
    }

    @Override
    public void onAdFailed(Throwable throwable) {
        Log.w("Personaly", "PopupOfferAdFailed");
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
            executor.fireAdEvent("personaly.popupoffer.events.LOAD_FAIL", data);
            executor.fireAdEvent("onFailedToReceiveAd", data);
        }
    }
    
    @Override
    public void onAdLeftApplication() {
        Log.w("Personaly", "PopupOfferAdLeftApplication");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.EXIT_APP", data);
        executor.fireAdEvent("onLeaveToAd", data);
    }

    @Override
    public void onAdClosed() {
        Log.w("Personaly", "PopupOfferAdClose");
        JSONObject data = new JSONObject();
        try {
            data.put("adType", executor.getAdType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        executor.fireAdEvent("personaly.popupoffer.events.CLOSE", data);
        executor.fireAdEvent("onDismissPopupOfferAd", data);
        executor.destroy();
    }
}
