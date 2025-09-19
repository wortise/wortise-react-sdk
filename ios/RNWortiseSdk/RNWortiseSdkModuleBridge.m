#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(RNWortiseAdSettings, NSObject)

RCT_EXTERN_METHOD(getAssetKey:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getMaxAdContentRating:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserId:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isChildDirected:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isTestEnabled:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setChildDirected:(BOOL)enabled)

RCT_EXTERN_METHOD(setMaxAdContentRating:(NSString *)rating)

RCT_EXTERN_METHOD(setTestEnabled:(BOOL)enabled)

RCT_EXTERN_METHOD(setUserId:(NSString *)userId)

@end

@interface RCT_EXTERN_MODULE(RNWortiseAppOpen, RCTEventEmitter)

RCT_EXTERN_METHOD(destroy)

RCT_EXTERN_METHOD(loadAd)

RCT_EXTERN_METHOD(isAvailable:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isShowing:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setAdUnitId:(NSString *)adUnitId)

RCT_EXTERN_METHOD(setAutoReload:(BOOL)autoReload)

RCT_EXTERN_METHOD(showAd:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(tryToShowAd:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end

@interface RCT_EXTERN_MODULE(RNWortiseBanner, RCTViewManager)

RCT_EXTERN_METHOD(loadAd:(nonnull NSNumber)reactTag)

RCT_EXPORT_VIEW_PROPERTY(adSize, NSDictionary)

RCT_EXPORT_VIEW_PROPERTY(adUnitId, NSString)

RCT_EXPORT_VIEW_PROPERTY(autoRefreshTime, NSNumber)

RCT_EXPORT_VIEW_PROPERTY(onClicked, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(onFailedToLoad, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(onImpression, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(onLoaded, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(onRevenuePaid, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(onSizeChange, RCTBubblingEventBlock)

@end

@interface RCT_EXTERN_MODULE(RNWortiseConsentManager, NSObject)

RCT_EXTERN_METHOD(canCollectData:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(canRequestPersonalizedAds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(exists:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(request:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(requestIfRequired:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end

@interface RCT_EXTERN_MODULE(RNWortiseDataManager, NSObject)

RCT_EXTERN_METHOD(addEmail:(NSString *)email)

RCT_EXTERN_METHOD(getAge:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getEmails:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getGender:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setAge:(NSInteger)age)

RCT_EXTERN_METHOD(setEmails:(NSArray<NSString *> *)list)

RCT_EXTERN_METHOD(setGender:(NSString *)gender)

@end

@interface RCT_EXTERN_MODULE(RNWortiseInterstitial, RCTEventEmitter)

RCT_EXTERN_METHOD(destroy)

RCT_EXTERN_METHOD(loadAd)

RCT_EXTERN_METHOD(isAvailable:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isShowing:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setAdUnitId:(NSString *)adUnitId)

RCT_EXTERN_METHOD(showAd:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end

@interface RCT_EXTERN_MODULE(RNWortiseRewarded, RCTEventEmitter)

RCT_EXTERN_METHOD(destroy)

RCT_EXTERN_METHOD(loadAd)

RCT_EXTERN_METHOD(isAvailable:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isShowing:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setAdUnitId:(NSString *)adUnitId)

RCT_EXTERN_METHOD(showAd:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end

@interface RCT_EXTERN_MODULE(RNWortiseSdk, NSObject)

RCT_EXTERN_METHOD(getVersion:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(initialize:(NSString *)assetKey
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isInitialized:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isReady:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(wait:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end
