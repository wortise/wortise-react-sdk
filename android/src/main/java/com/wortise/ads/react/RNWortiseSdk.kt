package com.wortise.ads.react

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.wortise.ads.WortiseSdk
import kotlin.Unit

class RNWortiseSdk(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RNWortiseSdk"

  @ReactMethod
  fun getVersion(promise: Promise) {
    promise.resolve(WortiseSdk.version)
  }

  @ReactMethod
  fun initialize(assetKey: String, promise: Promise) {
    WortiseSdk.initialize(reactApplicationContext, assetKey) {
      promise.resolve(null)
    }
  }

  @ReactMethod
  fun isInitialized(promise: Promise) {
    promise.resolve(WortiseSdk.isInitialized)
  }

  @ReactMethod
  fun isReady(promise: Promise) {
    promise.resolve(WortiseSdk.isReady)
  }

  @ReactMethod
  fun wait(promise: Promise) {
    WortiseSdk.wait {
      promise.resolve(null)
    }
  }
}
