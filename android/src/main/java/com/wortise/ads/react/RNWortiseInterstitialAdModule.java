package com.wortise.ads.react;

import android.app.Activity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.wortise.ads.AdError;
import com.wortise.ads.interstitial.InterstitialAd;

public class RNWortiseInterstitialAdModule extends ReactContextBaseJavaModule implements InterstitialAd.Listener, LifecycleEventListener {

  public static final String EVENT_CLICKED   = "onClicked";
  public static final String EVENT_DISMISSED = "onDismissed";
  public static final String EVENT_FAILED    = "onFailed";
  public static final String EVENT_LOADED    = "onLoaded";
  public static final String EVENT_SHOWN     = "onShown";


  private InterstitialAd mInterstitialAd;


  public RNWortiseInterstitialAdModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseInterstitial";
  }

  @ReactMethod
  public void destroy() {
    if (mInterstitialAd == null) {
      return;
    }

    mInterstitialAd.destroy();
    mInterstitialAd = null;
  }

  @ReactMethod
  public boolean isAvailable() {
    return (mInterstitialAd != null) && mInterstitialAd.isAvailable();
  }

  @ReactMethod
  public void loadAd() {
    if (mInterstitialAd == null) {
      return;
    }

    mInterstitialAd.loadAd();
  }

  @ReactMethod
  public void setAdUnitId(String adUnitId) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
        return;
    }

    destroy();

    mInterstitialAd = new InterstitialAd(currentActivity, adUnitId);
    mInterstitialAd.setListener(this);
  }

  @ReactMethod
  public boolean showAd() {
    if (mInterstitialAd == null) {
      return false;
    }

    return mInterstitialAd.showAd();
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
  public void onInterstitialClicked(InterstitialAd ad) {
    sendEvent(EVENT_CLICKED, null);
  }

  @Override
  public void onInterstitialDismissed(InterstitialAd ad) {
    sendEvent(EVENT_DISMISSED, null);
  }

  @Override
  public void onInterstitialFailed(InterstitialAd ad, AdError error) {
    WritableMap event = Arguments.createMap();

    event.putString("message", error.toString());
    event.putString("name",    error.name());

    sendEvent(EVENT_FAILED, event);
  }

  @Override
  public void onInterstitialLoaded(InterstitialAd ad) {
    sendEvent(EVENT_LOADED, null);
  }

  @Override
  public void onInterstitialShown(InterstitialAd ad) {
    sendEvent(EVENT_SHOWN, null);
  }
}
