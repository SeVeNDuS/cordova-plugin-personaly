#import <Personaly/Personaly.h>
#import <AdSupport/ASIdentifierManager.h>
#import <CommonCrypto/CommonDigest.h>

#import "CDVPersonaly.h"

#import "MainViewController.h"

@interface CDVPersonaly()

- (void) __setOptions:(NSDictionary*) options;
- (void) __showInterstitial:(BOOL)show;
- (void) __showRewardedVideo:(BOOL)show;
- (void) __showPopupOffer:(BOOL)show;
- (void) __showOfferWall:(BOOL)show;
- (void) __showAppWall:(BOOL)show;
- (GADRequest*) __buildAdRequest;
- (NSString*) __md5: (NSString*) s;
- (NSString *) __getPersonalyDeviceId;

- (void) fireEvent:(NSString *)obj event:(NSString *)eventName withData:(NSString *)jsonStr;

@end

@implementation CDVPersonaly

@synthesize interstitialView = interstitialView_;
@synthesize rewardedVideoView = rewardedVideoView_;
@synthesize popupOfferView = popupOfferView_;
@synthesize offerWallView = offerWallView_;
@synthesize appWallView = appWallView_;

@synthesize appId, interstitialAdId, rewardedVideoId, popupOfferId, offerWallId, appWallId;
@synthesize isTesting;

@synthesize autoShow, autoShowPopupOffer, autoShowInterstitial, autoShowRewardedVideo;

@synthesize gender, dateOfBirth, age, userId, clickId;

#define DEFAULT_APP_ID              @"5a5d301f6ff4230012057488"

#define DEFAULT_INTERSTITIAL_ID     @"4179e499-0bfd-4885-a179-a2f5968518ea"
#define DEFAULT_REWARDED_VIDEO_ID   @"220a0f80-114a-4201-a3f4-39226e01106f"
#define DEFAULT_POPUP_OFFER_ID      @"5f0d60e7014ce834c17c0cd7784ca7fc"
#define DEFAULT_OFFERWALL_ID        @"f73998678271d1501c64b0c9a03a2d0b"
#define DEFAULT_APPWALL_ID          @"d87a52fe-7e5a-4e1d-ab84-07984252c923"

#define OPT_APP_ID                  @"appId"
#define OPT_INTERSTITIAL_ADID       @"interstitialAdId"
#define OPT_REWARDED_VIDEO_ID       @"rewardedVideoId"
#define OPT_POPUP_OFFER_ID          @"popupOfferId"
#define OPT_OFFERWALL_ID            @"offerWallId"
#define OPT_APPWALL_ID              @"appWallId"
#define OPT_IS_TESTING              @"isTesting"
#define OPT_AUTO_SHOW               @"autoShow"

#define OPT_GENDER                  @"gender"
#define OPT_DATEOFBIRTH             @"dateOfBirth"
#define OPT_AGE                     @"age"
#define OPT_USERID                  @"userId"
#define OPT_CLICKID                 @"clickId"

#pragma mark Cordova JS bridge

- (void) pluginInitialize {
    [super pluginInitialize];
    if (self) {
        // These notifications are required for re-placing the ad on orientation
        // changes. Start listening for notifications here since we need to
        // translate the Smart Banner constants according to the orientation.
        [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
        [[NSNotificationCenter defaultCenter]
         addObserver:self
         selector:@selector(deviceOrientationChange:)
         name:UIDeviceOrientationDidChangeNotification
         object:nil];
    }

    appId = DEFAULT_APP_ID;
    interstitialAdId = DEFAULT_INTERSTITIAL_ID;
    rewardedVideoId = DEFAULT_REWARDED_VIDEO_ID;
    popupOfferId = DEFAULT_POPUP_OFFER_ID;
    offerWallId = DEFAULT_OFFERWALL_ID;
    appWallId = DEFAULT_APPWALL_ID;

    isTesting = false;

    autoShow = true;
    autoShowInterstitial = false;
    autoShowRewardedVideo = false;
    autoShowPopupOffer = false;

    gender = nil;
    dateOfBirth = nil;
    age = nil;
    userId = nil;
    clickId = nil;

    srand((unsigned int)time(NULL));
}

- (void) setOptions:(CDVInvokedUrlCommand *)command {
    NSLog(@"setOptions");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if( argc >= 1 ) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];

        [self __setOptions:options];
    }

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) prepareInterstitial:(CDVInvokedUrlCommand *)command {
    NSLog(@"prepareInterstitial");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if (argc >= 1) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self __setOptions:options];
        autoShowInterstitial = autoShow;
    }

    [self __cycleInterstitial];
    [self.interstitialView loadRequest:[self __buildAdRequest]];

    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];

}

- (void) showInterstitialAd:(CDVInvokedUrlCommand *)command {
    NSLog(@"showInterstitial");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if(! self.interstitialView) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"interstitialAd is null, call createInterstitialView first."];

    } else {
        BOOL showed = [self __showInterstitial:YES];
        if (showed) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"interstitial not ready yet."];
        }
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) isInterstitialReady:(CDVInvokedUrlCommand *)command {
    NSLog(@"isInterstitialReady");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if (self.interstitialView && self.interstitialView.isReady) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:true];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:false];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) prepareRewardedVideo:(CDVInvokedUrlCommand *)command {
    NSLog(@"prepareRewardedVideo");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if (argc >= 1) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self __setOptions:options];
        autoShowRewardVideo = autoShow;
    }

    [self __cycleRewardVideo];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];

}

- (void) showRewardedVideo:(CDVInvokedUrlCommand *)command {
    NSLog(@"showRewardedVideo");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if(! self.rewardedVideoView) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"rewardVideoAd is null, call createRewardVideo first."];

    } else {
        [self __showRewardedVideo:YES];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];

}

- (void) isRewardedVideoReady:(CDVInvokedUrlCommand *)command {
    NSLog(@"isRewardedVideoReady");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if (self.rewardedVideoView && self.rewardedVideoView.isReady) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:true];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:false];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) preparePopupOffer:(CDVInvokedUrlCommand *)command {
    NSLog(@"preparePopupOffer");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if (argc >= 1) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self __setOptions:options];
        autoShowPopupOffer = autoShow;
    }

    [self __cyclePopupOffer];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];

}

- (void) showPopupOffer:(CDVInvokedUrlCommand *)command {
    NSLog(@"showPopupOffer");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if(! self.rewardedVideoView) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"rewardVideoAd is null, call createRewardVideo first."];

    } else {
        [self __showPopupOffer:YES];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];

}

- (void) isPopupOfferReady:(CDVInvokedUrlCommand *)command {
    NSLog(@"isPopupOfferReady");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;

    if (self.popupOfferView && self.popupOfferView.isReady) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:true];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:false];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) showAppWall:(CDVInvokedUrlCommand *)command {
    NSLog(@"showAppWall");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if (argc >= 1) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self __setOptions:options];
    }

    [self __cycleAppWall];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (void) showOfferWall:(CDVInvokedUrlCommand *)command {
    NSLog(@"showOfferWall");

    CDVPluginResult *pluginResult;
    NSString *callbackId = command.callbackId;
    NSArray* args = command.arguments;

    NSUInteger argc = [args count];
    if (argc >= 1) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [self __setOptions:options];
    }

    [self __cycleOfferWall];
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

- (NSString *) __getPersonalyDeviceId {
    NSUUID* adid = [[ASIdentifierManager sharedManager] advertisingIdentifier];
    return [self __md5:adid.UUIDString];
}

- (NSString*) __md5:(NSString *) s {
    const char *cstr = [s UTF8String];
    unsigned char result[16];
    CC_MD5(cstr, (CC_LONG)strlen(cstr), result);

    return [NSString stringWithFormat:
            @"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
            result[0], result[1], result[2], result[3],
            result[4], result[5], result[6], result[7],
            result[8], result[9], result[10], result[11],
            result[12], result[13], result[14], result[15]
            ];
}

#pragma mark Ad Banner logic

- (void) __setOptions:(NSDictionary*) options {
    if ((NSNull *)options == [NSNull null]) return;

    NSString* str = nil;

    str = [options objectForKey:OPT_APP_ID];
    if (str && [str length] > 0) {
        appId = str;
    }

    str = [options objectForKey:OPT_INTERSTITIAL_ADID];
    if (str && [str length] > 0) {
        interstitialAdId = str;
    }

    str = [options objectForKey:OPT_REWARDED_VIDEO_ID];
    if (str && [str length] > 0) {
        rewardedVideoId = str;
    }

    str = [options objectForKey:OPT_POPUP_OFFER_ID];
    if (str && [str length] > 0) {
        popupOfferId = str;
    }

    str = [options objectForKey:OPT_APPWALL_ID];
    if (str && [str length] > 0) {
        appWallId = str;
    }

    str = [options objectForKey:OPT_OFFERWALL_ID];
    if (str && [str length] > 0) {
        offerWallId = str;
    }

    str = [options objectForKey:OPT_IS_TESTING];
    if (str) {
        isTesting = [str boolValue];
    }

    NSDictionary* dict = [options objectForKey:OPT_AD_EXTRAS];
    if (dict) {
        adExtras = dict;
    }

    str = [options objectForKey:OPT_AUTO_SHOW];
    if (str) {
        autoShow = [str boolValue];
    }

    str = [options objectForKey:OPT_GENDER];
    if (str && [str length] > 0) {
        gender = str;
    }
    str = [options objectForKey:OPT_DATEOFBIRTH];
    if (str && [str length] > 0) {
        dateOfBirth = str;
    }
    str = [options objectForKey:OPT_AGE];
    if (str && [str length] > 0) {
        age = str;
    }
    str = [options objectForKey:OPT_USERID];
    if (str && [str length] > 0) {
        userId = str;
    }
    str = [options objectForKey:OPT_CLICKID];
    if (str && [str length] > 0) {
        clickId = str;
    }

    NSDictionary *params = @{ [Personaly userIDKey]: userId,
                          [Personaly ageKey]: age,
                          [Personaly genderKey]: gender,
                          [Personaly dateOfBirthdayKey]: [dateOfBirth] };

    [Personaly configureWithAppID:appId parameters:params queue:dispatch_get_main_queue() completion:^(BOOL success, NSError * _Nullable error) {
        [Personaly setDelegate:self];
    }];
}

- (void) __cycleInterstitial {
    NSLog(@"__cycleInterstitial");

    // Clean up the old interstitial...
    if (self.interstitialView) {
        self.interstitialView.delegate = nil;
        self.interstitialView = nil;
    }

    // and create a new interstitial. We set the delegate so that we can be notified of when
    if (!self.interstitialView){
        self.interstitialView = [[GADInterstitial alloc] initWithAdUnitID:self.interstitialAdId];
        self.interstitialView.delegate = self;

        [self.interstitialView loadRequest:[self __buildAdRequest]];
    }
}

- (BOOL) __showInterstitial:(BOOL)show {
    NSLog(@"__showInterstitial");

    if (!self.interstitialView){
        [self __cycleInterstitial];
    }

    if (self.interstitialView && self.interstitialView.isReady) {
        [self.interstitialView presentFromRootViewController:self.viewController];
        return true;
    } else {
        NSLog(@"Ad wasn't ready");
        return false;
    }
}

- (void) __cycleRewardedVideo {
    NSLog(@"__cycleRewardedVideo");

    @synchronized(self.rewardededVideoLock) {
        if (!self.isRewardedVideoLoading) {
            self.isRewardedVideoLoading = true;

            // Clean up the old video...
            if (self.rewardedVideoView) {
                self.rewardedVideoView.delegate = nil;
                self.rewardedVideoView = nil;
            }

            // and create a new video. We set the delegate so that we can be notified of when
            if (!self.rewardedVideoView){
                self.rewardedVideoView = [GADRewardBasedVideoAd sharedInstance];
                self.rewardedVideoView.delegate = self;

                [self.rewardedVideoView loadRequest:[GADRequest request] withAdUnitID:self.rewardedVideoId];
            }
        }
    }
}

- (void) __showRewardedVideo:(BOOL)show {
    NSLog(@"__showRewardedVideo");

    if (!self.rewardVideoView){
        [self __cycleRewardedVideo];
    }

    if (self.rewardedVideoView && self.rewardVideoView.isReady) {
        [self.rewardedVideoView presentFromRootViewController:self.viewController];
    } else {
        NSLog(@"RewardedVideo wasn't ready");
    }
}

- (void) fireEvent:(NSString *)obj event:(NSString *)eventName withData:(NSString *)jsonStr {
    NSString* js;
    if(obj && [obj isEqualToString:@"window"]) {
        js = [NSString stringWithFormat:@"var evt=document.createEvent(\"UIEvents\");evt.initUIEvent(\"%@\",true,false,window,0);window.dispatchEvent(evt);", eventName];
    } else if(jsonStr && [jsonStr length]>0) {
        js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%@',%@);", eventName, jsonStr];
    } else {
        js = [NSString stringWithFormat:@"javascript:cordova.fireDocumentEvent('%@');", eventName];
    }
    [self.commandDelegate evalJs:js];
}

#pragma mark PersonalyDelegate implementation

- (void) didConfigure {
    [self fireEvent:@"" event:@"personaly.banner.events.LOAD" withData:nil];
    [self fireEvent:@"" event:@"onReceiveAd" withData:nil];
}

- (void) didFailConfigure:(NSError *)error {
    NSString* jsonData = [NSString stringWithFormat:@"{ 'error': '%@', 'adType':'banner' }", [error localizedFailureReason]];
    [self fireEvent:@"" event:@"personaly.banner.events.LOAD_FAIL" withData:jsonData];
    [self fireEvent:@"" event:@"onFailedToReceiveAd" withData:jsonData];
}

- (void) didPrecacheForPlacement:(NSString *)placementID {
    [self fireEvent:@"" event:@"personaly.banner.events.LOAD" withData:nil];
    [self fireEvent:@"" event:@"onReceiveAd" withData:nil];
}

- (void) didFailPrecacheForPlacement:(NSString *)placementID error:(NSError *)error {
    NSString* jsonData = [NSString stringWithFormat:@"{ 'error': '%@', 'adType':'banner' }", [error localizedFailureReason]];
    [self fireEvent:@"" event:@"personaly.banner.events.LOAD_FAIL" withData:jsonData];
    [self fireEvent:@"" event:@"onFailedToReceiveAd" withData:jsonData];
}

- (void) didReceiveRewardForPlacement:(NSString *)placementID amount:(NSInteger)amount {
    NSString* jsonData = [NSString stringWithFormat:@"{'adType':'rewardedvideo','adEvent':'onRewardedVideo','rewardAmount':%lf}", [amount doubleValue]];
    [self fireEvent:@"" event:@"personaly.banner.events.REWARDED" withData:jsonData];
    [self fireEvent:@"" event:@"onReceiveAd" withData:jsonData];
}

- (void) didReceiveClickForPlacement:(NSString *)placementID {
    [self fireEvent:@"" event:@"personaly.banner.events.CLICKED" withData:nil];
    [self fireEvent:@"" event:@"onReceiveAd" withData:nil];
}

#pragma mark Cleanup

- (void)dealloc {
    [[UIDevice currentDevice] endGeneratingDeviceOrientationNotifications];
    [[NSNotificationCenter defaultCenter]
     removeObserver:self
     name:UIDeviceOrientationDidChangeNotification
     object:nil];

    interstitialView_.delegate = nil;
    interstitialView_ = nil;
    rewardedVideoView_.delegate = nil;
    rewardedVideoView_ =  nil;
    popupOfferView_.delegate = nil;
    popupOfferView_ =  nil;
    offerWallView_.delegate = nil;
    offerWallView_ =  nil;
    appWallView_.delegate = nil;
    appWallView_ =  nil;

    self.interstitialView = nil;
    self.rewardedVideoView = nil;
    self.popupOfferView = nil;
    self.offerWallView = nil;
    self.appWallView = nil;
}

@end
