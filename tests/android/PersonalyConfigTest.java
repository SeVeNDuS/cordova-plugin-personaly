package name.sevendus.cordova.personaly;

import android.util.Log;

import com.google.android.gms.ads.AdSize;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class PersonalyConfigTest {
    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);
    }

    @Test
    public void canSetInterstitialAdUnitId() throws JSONException {
        PersonalyConfig config = new PersonalyConfig();
        assertEquals("ca-app-pub-3940256099942544/1033173712", config.getInterstitialAdUnitId());

        config.setOptions(new JSONObject("{\"interstitialAdId\": \"interstitial-id\"}"));
        assertEquals("interstitial-id", config.getInterstitialAdUnitId());
    }

    @Test
    public void canSetAutoShow() throws JSONException {
        PersonalyConfig config = new PersonalyConfig();
        assertEquals(true, config.autoShow);
        assertEquals(true, config.autoShowInterstitial);
        assertEquals(true, config.autoRewardedVideo);
        assertEquals(true, config.autoPopupOffer);

        config.setOptions(new JSONObject("{\"autoShow\": false}"));
        assertEquals(false, config.autoShow);
        assertEquals(false, config.autoShowInterstitial);
        assertEquals(false, config.autoRewardedVideo);
        assertEquals(false, config.autoPopupOffer);

        config.setOptions(new JSONObject("{\"autoShow\": true}"));
        assertEquals(true, config.autoShow);
        assertEquals(true, config.autoShowInterstitial);
        assertEquals(true, config.autoRewardedVideo);
        assertEquals(true, config.autoPopupOffer);

        config.setInterstitialOptions(new JSONObject("{\"autoShow\": false}"));
        assertEquals(true, config.autoShow);
        assertEquals(false, config.autoShowInterstitial);
        assertEquals(true, config.autoRewardedVideo);
        assertEquals(true, config.autoPopupOffer);

        config.setRewardedVideoOptions(new JSONObject("{\"autoShow\": false}"));
        assertEquals(true, config.autoShow);
        assertEquals(true, config.autoShowInterstitial);
        assertEquals(false, config.autoRewardedVideo);
        assertEquals(true, config.autoPopupOffer);

        config.setPopupOfferOptions(new JSONObject("{\"autoShow\": false}"));
        assertEquals(true, config.autoShow);
        assertEquals(true, config.autoShowInterstitial);
        assertEquals(true, config.autoRewardedVideo);
        assertEquals(false, config.autoPopupOffer);
    }
}
