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
import com.wortise.ads.appopen.AppOpenAd;
import com.wortise.ads.appopen.AppOpenAd.Orientation;

public class RNWortiseAppOpenModule extends ReactContextBaseJavaModule implements AppOpenAd.Listener, LifecycleEventListener {

  public static final String EVENT_CLICKED   = "onAppOpenClicked";
  public static final String EVENT_DISMISSED = "onAppOpenDismissed";
  public static final String EVENT_FAILED    = "onAppOpenFailed";
  public static final String EVENT_LOADED    = "onAppOpenLoaded";
  public static final String EVENT_SHOWN     = "onAppOpenShown";


  private AppOpenAd mAppOpenAd;


  public RNWortiseAppOpenModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseAppOpen";
  }

  @ReactMethod
  public void addListener(String eventName) {
  }

  @ReactMethod
  public void destroy() {
    if (mAppOpenAd == null) {
      return;
    }

    mAppOpenAd.destroy();
    mAppOpenAd = null;
  }

  @ReactMethod
  public void isAvailable(Promise promise) {
    promise.resolve((mAppOpenAd != null) && mAppOpenAd.isAvailable());
  }

  @ReactMethod
  public void isShowing(Promise promise) {
    promise.resolve((mAppOpenAd != null) && mAppOpenAd.isShowing());
  }

  @ReactMethod
  public void loadAd() {
    if (mAppOpenAd == null) {
      return;
    }

    mAppOpenAd.loadAd();
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

    mAppOpenAd = new AppOpenAd(currentActivity, adUnitId);
    mAppOpenAd.setListener(this);
  }

  @ReactMethod
  public void setAutoReload(boolean autoReload) {
    if (mAppOpenAd == null) {
      return;
    }

    mAppOpenAd.setAutoReload(autoReload);
  }

  @ReactMethod
  public void setOrientation(String orientation) {
    if (mAppOpenAd == null) {
      return;
    }

    Orientation value;
    
    try {
      value = Orientation.valueOf(orientation);
    } catch (Throwable t) {
      return;
    }

    mAppOpenAd.setOrientation(value);
  }

  @ReactMethod
  public void showAd(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null || mAppOpenAd == null) {
        promise.resolve(false);
        return;
    }

    promise.resolve(mAppOpenAd.showAd(currentActivity));
  }

  @ReactMethod
  public void tryToShowAd(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null || mAppOpenAd == null) {
        promise.resolve(false);
        return;
    }

    promise.resolve(mAppOpenAd.tryToShowAd(currentActivity));
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
  public void onAppOpenClicked(AppOpenAd ad) {
    sendEvent(EVENT_CLICKED, null);
  }

  @Override
  public void onAppOpenDismissed(AppOpenAd ad) {
    sendEvent(EVENT_DISMISSED, null);
  }

  @Override
  public void onAppOpenFailed(AppOpenAd ad, AdError error) {
    WritableMap event = Arguments.createMap();

    event.putString("message", error.toString());
    event.putString("name",    error.name());

    sendEvent(EVENT_FAILED, event);
  }

  @Override
  public void onAppOpenLoaded(AppOpenAd ad) {
    sendEvent(EVENT_LOADED, null);
  }

  @Override
  public void onAppOpenShown(AppOpenAd ad) {
    sendEvent(EVENT_SHOWN, null);
  }
}
