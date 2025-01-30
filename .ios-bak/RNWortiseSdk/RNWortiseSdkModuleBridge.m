#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNWortiseAdSettings, NSObject)

RCT_EXTERN_METHOD(getAssetKey)
RCT_EXTERN_METHOD(getMaxAdContentRating)
RCT_EXTERN_METHOD(getUserId)
RCT_EXTERN_METHOD(isChildDirected)
RCT_EXTERN_METHOD(isTestEnabled)
RCT_EXTERN_METHOD(setChildDirected: (BOOL)enabled)
RCT_EXTERN_METHOD(setMaxAdContentRating: (NSString *)rating)
RCT_EXTERN_METHOD(setTestEnabled: (BOOL)enabled)
RCT_EXTERN_METHOD(setUserId: (NSString *)userId)

@end

@interface RCT_EXTERN_MODULE(RNWortiseConsentManager, NSObject)

RCT_EXTERN_METHOD(canCollectData)
RCT_EXTERN_METHOD(canRequestPersonalizedAds)
RCT_EXTERN_METHOD(exists)
RCT_EXTERN_METHOD(request: (RCTPromiseResolveBlock *)resolve, (RCTPromiseRejectBlock *)reject)
RCT_EXTERN_METHOD(requestIfRequired: (RCTPromiseResolveBlock *)resolve, (RCTPromiseRejectBlock *)reject)

@end

@interface RCT_EXTERN_MODULE(RNWortiseDataManager, NSObject)

RCT_EXTERN_METHOD(addEmail: (NSString *)email)
RCT_EXTERN_METHOD(getAge)
RCT_EXTERN_METHOD(getEmails)
RCT_EXTERN_METHOD(getGender)
RCT_EXTERN_METHOD(setAge: (NSInteger *)age)
RCT_EXTERN_METHOD(setEmails: (NSArray *)list)
RCT_EXTERN_METHOD(setGender: (NSString *)age)

@end

@interface RCT_EXTERN_MODULE(RNWortiseInterstitial, NSObject)

RCT_EXTERN_METHOD(destroy)
RCT_EXTERN_METHOD(isAvailable)
RCT_EXTERN_METHOD(isShowing)
RCT_EXTERN_METHOD(loadAd)
RCT_EXTERN_METHOD(setAdUnitId: (NSString *)adUnitId)
RCT_EXTERN_METHOD(showAd)

@end

@interface RCT_EXTERN_MODULE(RNWortiseSdk, NSObject)

RCT_EXTERN_METHOD(getVersion)
RCT_EXTERN_METHOD(initialize: (NSString *)assetKey, (RCTPromiseResolveBlock *)resolve)
RCT_EXTERN_METHOD(isInitialized)
RCT_EXTERN_METHOD(isReady)
RCT_EXTERN_METHOD(wait: (RCTPromiseResolveBlock *)resolve)

@end
