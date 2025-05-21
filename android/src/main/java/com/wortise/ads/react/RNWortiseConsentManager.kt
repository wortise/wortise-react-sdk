package com.wortise.ads.react

import android.app.Activity
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.wortise.ads.consent.ConsentManager
import kotlin.Unit

class RNWortiseConsentManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RNWortiseConsentManager"

  @ReactMethod
  fun canCollectData(promise: Promise) {
    promise.resolve(ConsentManager.canCollectData(reactApplicationContext))
  }

  @ReactMethod
  fun canRequestPersonalizedAds(promise: Promise) {
    promise.resolve(ConsentManager.canRequestPersonalizedAds(reactApplicationContext))
  }

  @ReactMethod
  fun exists(promise: Promise) {
    promise.resolve(ConsentManager.exists(reactApplicationContext))
  }

  @ReactMethod
  fun request(promise: Promise) {
    val currentActivity = currentActivity

    if (currentActivity == null) {
      promise.resolve(false)
      return
    }

    ConsentManager.request(currentActivity) {
      promise.resolve(it)
    }
  }

  @ReactMethod
  fun requestIfRequired(promise: Promise) {
    val currentActivity = currentActivity

    if (currentActivity == null) {
      promise.resolve(false)
      return
    }

    ConsentManager.requestIfRequired(currentActivity) {
      promise.resolve(it)
    }
  }
}
