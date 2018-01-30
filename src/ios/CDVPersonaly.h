#import <Cordova/CDV.h>
#import <Foundation/Foundation.h>
#import <Personaly/Personaly.h>
#import <UIKit/UIKit.h>

#pragma mark - JS requestAd options

#pragma mark Personaly Plugin

// This version of the Personaly plugin has been tested with Cordova version 2.5.0.

@interface CDVPersonaly : CDVPlugin <PersonalyDelegate> {
}

@property (nonatomic, retain) NSString* appId;
@property (nonatomic, retain) NSString* interstitialAdId;
@property (nonatomic, retain) NSString* rewardedVideoId;
@property (nonatomic, retain) NSString* popupOfferId;
@property (nonatomic, retain) NSString* appWallId;
@property (nonatomic, retain) NSString* offerWallId;

@property (assign) BOOL isTesting;

@property (assign) BOOL autoShow;
@property (assign) BOOL autoShowInterstitial;
@property (assign) BOOL autoShowRewardedVideo;
@property (assign) BOOL autoShowPopupOffer;
@property (assign) NSObject* rewardedVideoLock;
@property (assign) BOOL isRewardedVideoLoading;

@property (nonatomic, retain) NSString* gender;
@property (nonatomic, retain) NSString* dafeOfBirth;
@property (nonatomic, retain) NSString* age;
@property (nonatomic, retain) NSString* userId;
@property (nonatomic, retain) NSString* clickId;

- (void) setOptions:(CDVInvokedUrlCommand *)command;

- (void) prepareInterstitialView:(CDVInvokedUrlCommand *)command;
- (void) showInterstitialAd:(CDVInvokedUrlCommand *)command;
- (void) isInterstitialReady:(CDVInvokedUrlCommand *)command;

- (void) prepareRewardedVideo:(CDVInvokedUrlCommand *)command;
- (void) showRewardedVideo:(CDVInvokedUrlCommand *)command;
- (void) isRewardedVideoReady:(CDVInvokedUrlCommand *)command;

- (void) preparePopupOffer:(CDVInvokedUrlCommand *)command;
- (void) showPopupOffer:(CDVInvokedUrlCommand *)command;
- (void) isPopupOfferReady:(CDVInvokedUrlCommand *)command;

- (void) showOfferWall:(CDVInvokedUrlCommand *)command;

- (void) showAppWall:(CDVInvokedUrlCommand *)command;

@end
