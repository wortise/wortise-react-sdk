package com.wortise.ads.react

import android.app.Activity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.wortise.ads.AdError
import com.wortise.ads.RevenueData
import com.wortise.ads.interstitial.InterstitialAd
import com.wortise.ads.react.extensions.toWritableMap

class RNWortiseInterstitial(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), InterstitialAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
  }

  private var interstitialAd: InterstitialAd? = null


  override fun getName(): String = "RNWortiseInterstitial"

  @ReactMethod
  fun addListener(eventName: String) {}

  @ReactMethod
  fun destroy() {
    interstitialAd?.destroy()
    interstitialAd = null
  }

  @ReactMethod
  fun isAvailable(promise: Promise) {
    promise.resolve(interstitialAd?.isAvailable == true)
  }

  @ReactMethod
  fun isShowing(promise: Promise) {
    promise.resolve(interstitialAd?.isShowing == true)
  }

  @ReactMethod
  fun loadAd() {
    interstitialAd?.loadAd()
  }

  @ReactMethod
  fun removeListeners(count: Int) {}

  @ReactMethod
  fun setAdUnitId(adUnitId: String) {
    val currentActivity = reactApplicationContext.currentActivity ?: return

    destroy()

    interstitialAd = InterstitialAd(currentActivity, adUnitId).also {
      it.listener = this
    }
  }

  @ReactMethod
  fun showAd(promise: Promise) {
    val interstitialAd = interstitialAd

    if (interstitialAd == null) {
      promise.resolve(false)
      return
    }

    val currentActivity = reactApplicationContext.currentActivity

    currentActivity?.let(interstitialAd::showAd) ?: interstitialAd.showAd()

    promise.resolve(true)
  }


  private fun sendEvent(eventName: String, params: WritableMap? = null) {
    eventEmitter.emit(eventName, params)
  }


  override fun onHostDestroy() {
    destroy()
  }

  override fun onHostPause() {}

  override fun onHostResume() {}

  override fun onInterstitialClicked(ad: InterstitialAd) {
    sendEvent(EVENT_CLICKED)
  }

  override fun onInterstitialDismissed(ad: InterstitialAd) {
    sendEvent(EVENT_DISMISSED)
  }

  override fun onInterstitialFailedToLoad(ad: InterstitialAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_LOAD, error.toWritableMap())
  }

  override fun onInterstitialFailedToShow(ad: InterstitialAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_SHOW, error.toWritableMap())
  }

  override fun onInterstitialImpression(ad: InterstitialAd) {
    sendEvent(EVENT_IMPRESSION)
  }

  override fun onInterstitialLoaded(ad: InterstitialAd) {
    sendEvent(EVENT_LOADED)
  }

  override fun onInterstitialRevenuePaid(ad: InterstitialAd, data: RevenueData) {
    sendEvent(EVENT_REVENUE_PAID, data.toWritableMap())
  }

  override fun onInterstitialShown(ad: InterstitialAd) {
    sendEvent(EVENT_SHOWN)
  }


  companion object {
    const val EVENT_CLICKED        = "onInterstitialClicked"
    const val EVENT_DISMISSED      = "onInterstitialDismissed"
    const val EVENT_FAILED_TO_LOAD = "onInterstitialFailedToLoad"
    const val EVENT_FAILED_TO_SHOW = "onInterstitialFailedToShow"
    const val EVENT_IMPRESSION     = "onInterstitialImpression"
    const val EVENT_LOADED         = "onInterstitialLoaded"
    const val EVENT_REVENUE_PAID   = "onInterstitialRevenuePaid"
    const val EVENT_SHOWN          = "onInterstitialShown"
  }
}
