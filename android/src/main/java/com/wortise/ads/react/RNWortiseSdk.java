package com.wortise.ads.react;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.wortise.ads.WortiseSdk;

import kotlin.Unit;

public class RNWortiseSdk extends ReactContextBaseJavaModule {

  public RNWortiseSdk(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseSdk";
  }

  @ReactMethod
  public void getVersion(Promise promise) {
    promise.resolve(WortiseSdk.getVersion());
  }

  @ReactMethod
  public void initialize(String assetKey, Promise promise) {
    WortiseSdk.initialize(getReactApplicationContext(), assetKey, () -> {
      promise.resolve(null);
      return Unit.INSTANCE;
    });
  }

  @ReactMethod
  public void isInitialized(Promise promise) {
    promise.resolve(WortiseSdk.isInitialized());
  }

  @ReactMethod
  public void isReady(Promise promise) {
    promise.resolve(WortiseSdk.isReady());
  }

  @ReactMethod
  public void wait(Promise promise) {
    WortiseSdk.wait(() -> {
      promise.resolve(null);
      return Unit.INSTANCE;
    });
  }
}
