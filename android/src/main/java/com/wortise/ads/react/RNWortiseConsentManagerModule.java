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
  public void canCollectData(Promise promise) {
    promise.resolve(ConsentManager.canCollectData(getReactApplicationContext()));
  }

  @ReactMethod
  public void canRequestPersonalizedAds(Promise promise) {
    promise.resolve(ConsentManager.canRequestPersonalizedAds(getReactApplicationContext()));
  }

  @ReactMethod
  public void exists(Promise promise) {
    promise.resolve(ConsentManager.exists(getReactApplicationContext()));
  }

  @ReactMethod
  public void request(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
      promise.resolve(false);
      return;
    }

    ConsentManager.request(currentActivity, shown -> {
      promise.resolve(shown);
      return Unit.INSTANCE;
    });
  }

  @ReactMethod
  public void requestIfRequired(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    if (currentActivity == null) {
      promise.resolve(false);
      return;
    }

    ConsentManager.requestIfRequired(currentActivity, shown -> {
      promise.resolve(shown);
      return Unit.INSTANCE;
    });
  }
}
