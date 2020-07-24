package com.wortise.ads.react;

import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.wortise.ads.consent.ConsentManager;

import kotlin.Unit;

public class RNWortiseConsentManagerModule extends ReactContextBaseJavaModule {

  public RNWortiseConsentManagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseConsentManager";
  }

  @ReactMethod
  public boolean canCollectData() {
    return ConsentManager.canCollectData(getReactApplicationContext());
  }

  @ReactMethod
  public Boolean isGranted() {
    return ConsentManager.isGranted(getReactApplicationContext());
  }

  @ReactMethod
  public Boolean isReplied() {
    return ConsentManager.isReplied(getReactApplicationContext());
  }

  @ReactMethod
  public void request(boolean withOptOut, Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
      promise.resolve(isGranted());
      return;
    }

    ConsentManager.request(currentActivity, withOptOut, consent -> {
      promise.resolve(consent);
    });
  }

  @ReactMethod
  public void requestOnce(boolean withOptOut, Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
      promise.resolve(isGranted());
      return;
    }

    ConsentManager.requestOnce(currentActivity, withOptOut, consent -> {
      promise.resolve(consent);
    });
  }

  @ReactMethod
  public void set(boolean granted) {
    ConsentManager.set(getReactApplicationContext(), granted);
  }
}
