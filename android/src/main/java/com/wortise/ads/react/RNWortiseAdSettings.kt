package com.wortise.ads.react

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.wortise.ads.AdContentRating
import com.wortise.ads.AdSettings
import com.wortise.ads.extensions.tryOrNull

class RNWortiseAdSettings(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RNWortiseAdSettings"

  @ReactMethod
  fun getAssetKey(promise: Promise) {
    promise.resolve(AdSettings.getAssetKey(reactApplicationContext))
  }

  @ReactMethod
  fun getMaxAdContentRating(promise: Promise) {
    val rating = AdSettings.getMaxAdContentRating(reactApplicationContext)

    if (rating == null) {
      promise.resolve(null)
      return
    }

    promise.resolve(rating.name)
  }

  @ReactMethod
  fun isTestEnabled(): Boolean {
    return AdSettings.testEnabled
  }

  @ReactMethod
  fun getUserId(promise: Promise) {
    promise.resolve(AdSettings.getUserId(reactApplicationContext))
  }

  @ReactMethod
  fun isChildDirected(promise: Promise) {
    promise.resolve(AdSettings.isChildDirected(reactApplicationContext))
  }

  @ReactMethod
  fun setChildDirected(enabled: Boolean) {
    AdSettings.setChildDirected(reactApplicationContext, enabled)
  }

  @ReactMethod
  fun setMaxAdContentRating(rating: String?) {
    val value = rating?.let {
      tryOrNull { AdContentRating.valueOf(it) }
    }

    AdSettings.setMaxAdContentRating(reactApplicationContext, value)
  }

  @ReactMethod
  fun setTestEnabled(enabled: Boolean) {
    AdSettings.testEnabled = enabled
  }

  @ReactMethod
  fun setUserId(userId: String?) {
    AdSettings.setUserId(reactApplicationContext, userId)
  }
}
