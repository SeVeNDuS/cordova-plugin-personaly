package name.sevendus.cordova.personaly;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonalyConfig {
    /* options */
    private static final String OPT_APP_ID = "appId";

    private static final String OPT_INTERSTITIAL_AD_ID = "interstitialAdId";
    private static final String OPT_REWARDED_VIDEO_ID = "rewardedVideoId";
    private static final String OPT_OFFERWALL_ID = "offerWallId";
    private static final String OPT_POPUP_OFFER_ID = "popupOfferId";
    private static final String OPT_APPWALL_ID = "appWallId";

    private static final String OPT_IS_TESTING = "isTesting";

    public static final String OPT_AUTO_SHOW = "autoShow";
    public static final String OPT_AUTO_SHOW_INTERSTITIAL = "autoShowInterstitial";
    public static final String OPT_AUTO_SHOW_POPUP_OFFER = "autoShowPopupOffer";
    public static final String OPT_AUTO_SHOW_REWARDED_VIDEO = "autoShowRewardedVideo";

    public static final String OPT_TEST_DEVICES = "testDevices";

    private static final String OPT_GENDER = "gender";
    private static final String OPT_DATEOFBIRTH = "dateOfBirth";
    private static final String OPT_AGE = "age";
    private static final String OPT_USERID = "userId";
    private static final String OPT_CLICKID = "clickId";

    public boolean isTesting = false;

    public boolean autoShow = true;
    public boolean autoShowBanner = true;
    public boolean autoShowInterstitial = true;
    public boolean autoShowRewardedVideo = false;
    public boolean autoShowPopupOffer = false;

    public String gender = null;
    public String dateOfBirth = null;
    public String age = null;
    public String userId = null;
    public String clickId = null;

    public List<String> testDeviceList = null;

    // General
    private static final String TEST_APP_ID = "5a5d301f6ff4230012057488";
    private String appId = "";

    // Interstial
    private static final String TEST_INTERSTITIAL_ID = "4179e499-0bfd-4885-a179-a2f5968518ea";
    private String interstitialAdUnitId = "";

    // Rewarded Video
    private static final String TEST_REWARDED_VIDEO_ID = "220a0f80-114a-4201-a3f4-39226e01106f";
    private String rewardedVideoId = "";

    // Offerwall
    private static final String TEST_OFFERWALL_ID = "f73998678271d1501c64b0c9a03a2d0b";
    private String offerWallId = "";

    // PopupOffer
    private static final String TEST_POPUP_OFFER_ID = "5f0d60e7014ce834c17c0cd7784ca7fc";
    private String popupOfferId = "";

    // AppWall
    private static final String TEST_APPWALL_ID = "d87a52fe-7e5a-4e1d-ab84-07984252c923";
    private String appWallId = "";


    public void setOptions(JSONObject options) {
        if (options == null) {
            return;
        }

        if (options.has(OPT_APP_ID)) {
            this.appId = options.optString(OPTA_APP_ID);
        }
        if (options.has(OPT_INTERSTITIAL_AD_ID)) {
            this.interstitialAdUnitId = options.optString(OPT_INTERSTITIAL_AD_ID);
        }
        if (options.has(OPT_REWARDED_VIDEO_ID)) {
            this.rewardedVideoId = options.optString(OPT_REWARDED_VIDEO_ID);
        }
        if (options.has(OPT_OFFERWALL_ID)) {
            this.offerWallId = options.optString(OPT_OFFERWALL_ID);
        }
        if (options.has(OPT_APPWALL_ID)) {
            this.appWallId = options.optString(OPT_APPWALL_ID);
        }
        if (options.has(OPT_POPUP_OFFER_ID)) {
            this.popupOfferId = options.optString(OPT_POPUP_OFFER_ID);
        }
        if (options.has(OPT_IS_TESTING)) {
            this.isTesting = options.optBoolean(OPT_IS_TESTING);
        }
        if (options.has(OPT_AUTO_SHOW)) {
            this.autoShow = options.optBoolean(OPT_AUTO_SHOW);
        }
        if (options.has(OPT_AUTO_SHOW_INTERSTITIAL)) {
            this.autoShowInterstitial = options.optBoolean(OPT_AUTO_SHOW_INTERSTITIAL);
        }
        if (options.has(OPT_AUTO_SHOW_REWARDED_VIDEO)) {
            this.autoShowRewardedVideo = options.optBoolean(OPT_AUTO_SHOW_REWARDED_VIDEO);
        }
        if (options.has(OPT_AUTO_SHOW_POPUP_OFFER)) {
            this.autoShowPopupOffer = options.optBoolean(OPT_AUTO_SHOW_POPUP_OFFER);
        }

        if (options.has(OPT_GENDER)) {
            this.gender = options.optString(OPT_GENDER);
        }
        if (options.has(OPT_DATEOFBIRTH)) {
            this.dateOfBirth = options.optString(OPT_DATEOFBIRTH);
        }
        if (options.has(OPT_AGE)) {
            this.age = options.optString(OPT_AGE);
        }
        if (options.has(OPT_USERID)) {
            this.userId = options.optString(OPT_USERID);
        }
        if (options.has(OPT_CLICKID)) {
            this.clickId = options.optString(OPT_CLICKID);
        }

        if (options.has(OPT_TEST_DEVICES)) {
            JSONArray testDevices = options.optJSONArray(OPT_TEST_DEVICES);
            if (testDevices != null) {
                testDeviceList = new ArrayList<String>();
                for (int i = 0; i < testDevices.length(); i++) {
                    testDeviceList.add(testDevices.optString(i));
                }
            }
        }
    }

    public void setInterstitialOptions(JSONObject options) {
        try {
            this.autoShowInterstitial = (Boolean) options.remove(OPT_AUTO_SHOW);
        } catch (NullPointerException ignored) {
        }
        this.setOptions(options);
    }

    public void setRewardedVideoOptions(JSONObject options) {
        try {
            this.autoShowRewardedVideo = (Boolean) options.remove(OPT_AUTO_SHOW);
        } catch (NullPointerException ignored) {
        }
        this.setOptions(options);
    }

    public void setOfferWallOptions(JSONObject options) {
        this.setOptions(options);
    }

    public void setAppWallOptions(JSONObject options) {
        this.setOptions(options);
    }

    public void setPopupOfferOptions(JSONObject options) {
        try {
            this.autoShowPopupOffer = (Boolean) options.remove(OPT_AUTO_SHOW);
        } catch (NullPointerException ignored) {
        }
        this.setOptions(options);
    }

    public String getAppId() {
        if (isEmptyAdUnitId(appId)) {
            // in case the user does not enter their own publisher id
            Log.e("app", "Please put your Personaly app id into the javascript code. Test app id is used.");
            return TEST_APP_ID;
        }
        return appId;
    }

    public String getInterstitialAdUnitId() {
        if (isEmptyAdUnitId(interstitialAdUnitId)) {
            // in case the user does not enter their own publisher id
            Log.e("interstitial", "Please put your Personaly id into the javascript code. Test ad is used.");
            return TEST_INTERSTITIAL_ID;
        }
        return interstitialAdUnitId;
    }

    public String getRewardedVideoAdUnitId() {
        if (isEmptyAdUnitId(rewardedVideoId)) {
            // in case the user does not enter their own publisher id
            Log.e("rewardedvideo", "Please put your Personaly id into the javascript code. Test ad is used.");
            return TEST_REWARDED_VIDEO_ID;
        }
        return rewardedVideoId;
    }

    public String getOfferWallAdUnitId() {
        if (isEmptyAdUnitId(offerWallId)) {
            // in case the user does not enter their own publisher id
            Log.e("offerwall", "Please put your Personaly offerwall id into the javascript code. Test ad is used.");
            return TEST_OFFERWALL_ID;
        }
        return offerWallId;
    }

    public String getPopupOfferAdUnitId() {
        if (isEmptyAdUnitId(popupOfferId)) {
            // in case the user does not enter their own publisher id
            Log.e("popupoffer", "Please put your Personaly PopupOffer id into the javascript code. Test ad is used.");
            return TEST_POPUP_OFFER_ID;
        }
        return popupOfferId;
    }

    public String getAppWallAdUnitId() {
        if (isEmptyAdUnitId(appWallId)) {
            // in case the user does not enter their own publisher id
            Log.e("appWall", "Please put your Personaly appwall id into the javascript code. Test ad is used.");
            return TEST_APPWALL_ID;
        }
        return appWallId;
    }

    private static boolean isEmptyAdUnitId(String adId) {
        return adId.length() == 0 || adId.indexOf("xxxx") > 0;
    }
}
