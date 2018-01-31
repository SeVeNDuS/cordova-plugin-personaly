package name.sevendus.cordova.personaly;

import ly.persona.sdk.model.PersonaError;

import org.json.JSONObject;

/**
 * This class implements the Personaly ad listener events.  It forwards the events
 * to the JavaScript layer.  To listen for these events, use:
 * <p>
 * document.addEventListener('onAdStartLoading', function());
 * document.addEventListener('onAdRewarded', function(data){});
 * document.addEventListener('onAdFailed', function(data){});
 * document.addEventListener('onAdLoaded', function());
 * document.addEventListener('onAdShowed', function());
 * document.addEventListener('onAdClicked', function());
 * document.addEventListener('onAdClosed', function());
 */
public abstract class AbstractExecutor {
    protected PersonalyPlugin plugin;

    public AbstractExecutor(PersonalyPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets a string error reason from an error code.
     */
    public static String getErrorReason(int errorCode) {
        String errorReason = "";
        switch (errorCode) {
            case PersonaError.ERROR_INIT:
                errorReason = "Incorrect inizialization";
                break;
            case PersonaError.ERROR_INTERNAL:
                errorReason = "Internal SDK error, not related to the publisher";
                break;
            case PersonaError.ERROR_NETWORK:
                errorReason = "The ad request was unsuccessful due to network connectivity issues or a resource was not found";
                break;
            case PersonaError.ERROR_NO_AD:
                errorReason = "The ad will not load";
                break;
            case PersonaError.ERROR_BUSY_AD:
                errorReason = "The ad is loading or currently being displayed, please hold";
                break;
        }
        return errorReason;
    }

    public abstract String getAdType();

    public abstract void destroy();

    public void fireAdEvent(String eventName) {
        String js = new CordovaEventBuilder(eventName).build();
        loadJS(js);
    }

    public void fireAdEvent(String eventName, JSONObject data) {
        String js = new CordovaEventBuilder(eventName).withData(data).build();
        loadJS(js);
    }

    private void loadJS(String js) {
        plugin.webView.loadUrl(js);
    }
}
