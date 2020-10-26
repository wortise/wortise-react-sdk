package com.wortise.ads.react;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.wortise.ads.WortiseSdk;

import kotlin.Unit;

public class RNWortiseSdkModule extends ReactContextBaseJavaModule {

  public RNWortiseSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseSdk";
  }

  @ReactMethod
  public void initialize(String assetKey, Promise promise) {
    initialize(assetKey, true, promise);
  }

  @ReactMethod
  public void initialize(String assetKey, boolean start, Promise promise) {
    WortiseSdk.initialize(getReactApplicationContext(), assetKey, start, () -> {
      promise.resolve(null);
      return Unit.INSTANCE;
    });
  }

  @ReactMethod
  public void getVersion(Promise promise) {
    promise.resolve(WortiseSdk.getVersion());
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
  public void start() {
    WortiseSdk.start(getReactApplicationContext());
  }

  @ReactMethod
  public void stop() {
    WortiseSdk.stop(getReactApplicationContext());
  }

  @ReactMethod
  public void wait(Promise promise) {
    WortiseSdk.wait(() -> {
      promise.resolve(null);
      return Unit.INSTANCE;
    });
  }
}
