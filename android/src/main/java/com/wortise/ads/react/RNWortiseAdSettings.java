package com.wortise.ads.react;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.wortise.ads.AdContentRating;
import com.wortise.ads.AdSettings;

public class RNWortiseAdSettings extends ReactContextBaseJavaModule {

  public RNWortiseAdSettings(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseAdSettings";
  }

  @ReactMethod
  public void getAssetKey(Promise promise) {
    promise.resolve(AdSettings.getAssetKey(getReactApplicationContext()));
  }

  @ReactMethod
  public void getMaxAdContentRating(Promise promise) {
    AdContentRating rating = AdSettings.getMaxAdContentRating(getReactApplicationContext());

    if (rating == null) {
      promise.resolve(null);
      return;
    }

    promise.resolve(rating.name());
  }

  @ReactMethod
  public boolean isTestEnabled() {
    return AdSettings.isTestEnabled();
  }

  @ReactMethod
  public void getUserId(Promise promise) {
    promise.resolve(AdSettings.getUserId(getReactApplicationContext()));
  }

  @ReactMethod
  public void isChildDirected(Promise promise) {
    promise.resolve(AdSettings.isChildDirected(getReactApplicationContext()));
  }

  @ReactMethod
  public void setChildDirected(boolean enabled) {
    AdSettings.setChildDirected(getReactApplicationContext(), enabled);
  }

  @ReactMethod
  public void setMaxAdContentRating(String rating) {
    AdContentRating value;

    try {
      value = AdContentRating.valueOf(rating);
    } catch (Throwable t) {
      value = null;
    }

    AdSettings.setMaxAdContentRating(getReactApplicationContext(), value);
  }

  @ReactMethod
  public void setTestEnabled(boolean enabled) {
    AdSettings.setTestEnabled(enabled);
  }

  @ReactMethod
  public void setUserId(String userId) {
    AdSettings.setUserId(getReactApplicationContext(), userId);
  }
}
