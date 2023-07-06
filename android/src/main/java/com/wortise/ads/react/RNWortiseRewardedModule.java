package com.wortise.ads.react;

import android.app.Activity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.wortise.ads.AdError;
import com.wortise.ads.rewarded.RewardedAd;
import com.wortise.ads.rewarded.models.Reward;

public class RNWortiseRewardedModule extends ReactContextBaseJavaModule implements RewardedAd.Listener, LifecycleEventListener {

  public static final String EVENT_CLICKED   = "onRewardedClicked";
  public static final String EVENT_COMPLETED = "onRewardedCompleted";
  public static final String EVENT_DISMISSED = "onRewardedDismissed";
  public static final String EVENT_FAILED    = "onRewardedFailed";
  public static final String EVENT_LOADED    = "onRewardedLoaded";
  public static final String EVENT_SHOWN     = "onRewardedShown";


  private RewardedAd mRewardedAd;


  public RNWortiseRewardedModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseRewarded";
  }

  @ReactMethod
  public void addListener(String eventName) {
  }

  @ReactMethod
  public void destroy() {
    if (mRewardedAd == null) {
      return;
    }

    mRewardedAd.destroy();
    mRewardedAd = null;
  }

  @ReactMethod
  public void isAvailable(Promise promise) {
    promise.resolve((mRewardedAd != null) && mRewardedAd.isAvailable());
  }

  @ReactMethod
  public void isShowing(Promise promise) {
    promise.resolve((mRewardedAd != null) && mRewardedAd.isShowing());
  }

  @ReactMethod
  public void loadAd() {
    if (mRewardedAd == null) {
      return;
    }

    mRewardedAd.loadAd();
  }

  @ReactMethod
  public void removeListeners(Integer count) {
  }

  @ReactMethod
  public void setAdUnitId(String adUnitId) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
        return;
    }

    destroy();

    mRewardedAd = new RewardedAd(currentActivity, adUnitId);
    mRewardedAd.setListener(this);
  }

  @ReactMethod
  public void showAd(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (mRewardedAd == null) {
        promise.resolve(false);
        return;
    }

    boolean result = (currentActivity != null)
      ? mRewardedAd.showAd(currentActivity)
      : mRewardedAd.showAd();

    promise.resolve(result);
  }


  private void sendEvent(String eventName, WritableMap params) {
    getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }


  @Override
  public void onHostDestroy() {
    destroy();
  }

  @Override
  public void onHostPause() {
  }

  @Override
  public void onHostResume() {
  }

  @Override
  public void onRewardedClicked(RewardedAd ad) {
    sendEvent(EVENT_CLICKED, null);
  }

  @Override
  public void onRewardedCompleted(RewardedAd ad, Reward reward) {
    WritableMap event = Arguments.createMap();

    event.putInt    ("amount",  reward.getAmount());
    event.putString ("label",   reward.getLabel());
    event.putBoolean("success", reward.getSuccess());

    sendEvent(EVENT_COMPLETED, event);
  }

  @Override
  public void onRewardedDismissed(RewardedAd ad) {
    sendEvent(EVENT_DISMISSED, null);
  }

  @Override
  public void onRewardedFailed(RewardedAd ad, AdError error) {
    WritableMap event = Arguments.createMap();

    event.putString("message", error.toString());
    event.putString("name",    error.name());

    sendEvent(EVENT_FAILED, event);
  }

  @Override
  public void onRewardedLoaded(RewardedAd ad) {
    sendEvent(EVENT_LOADED, null);
  }

  @Override
  public void onRewardedShown(RewardedAd ad) {
    sendEvent(EVENT_SHOWN, null);
  }
}
