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
import com.wortise.ads.appopen.AppOpenAd
import com.wortise.ads.react.extensions.toWritableMap

class RNWortiseAppOpen(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), AppOpenAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
  }

  private var appOpenAd: AppOpenAd? = null


  override fun getName(): String = "RNWortiseAppOpen"

  @ReactMethod
  fun addListener(eventName: String) {}

  @ReactMethod
  fun destroy() {
    appOpenAd?.destroy()
    appOpenAd = null
  }

  @ReactMethod
  fun isAvailable(promise: Promise) {
    promise.resolve(appOpenAd?.isAvailable == true)
  }

  @ReactMethod
  fun isShowing(promise: Promise) {
    promise.resolve(appOpenAd?.isShowing == true)
  }

  @ReactMethod
  fun loadAd() {
    appOpenAd?.loadAd()
  }

  @ReactMethod
  fun removeListeners(count: Int) {}

  @ReactMethod
  fun setAdUnitId(adUnitId: String) {
    val currentActivity = currentActivity ?: return

    destroy()

    appOpenAd = AppOpenAd(currentActivity, adUnitId).also {
      it.listener = this
    }
  }

  @ReactMethod
  fun setAutoReload(autoReload: Boolean) {
    appOpenAd?.autoReload = autoReload
  }

  @ReactMethod
  fun showAd(promise: Promise) {
    val appOpenAd = appOpenAd

    if (appOpenAd == null) {
      promise.resolve(false)
      return
    }

    currentActivity?.let(appOpenAd::showAd) ?: appOpenAd.showAd()

    promise.resolve(true)
  }

  @ReactMethod
  fun tryToShowAd(promise: Promise) {
    val appOpenAd = appOpenAd

    if (appOpenAd == null) {
        promise.resolve(false)
        return
    }

    appOpenAd.tryToShowAd(currentActivity)

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

  override fun onAppOpenClicked(ad: AppOpenAd) {
    sendEvent(EVENT_CLICKED)
  }

  override fun onAppOpenDismissed(ad: AppOpenAd) {
    sendEvent(EVENT_DISMISSED)
  }

  override fun onAppOpenFailedToLoad(ad: AppOpenAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_LOAD, error.toWritableMap())
  }

  override fun onAppOpenFailedToShow(ad: AppOpenAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_SHOW, error.toWritableMap())
  }

  override fun onAppOpenImpression(ad: AppOpenAd) {
    sendEvent(EVENT_IMPRESSION)
  }

  override fun onAppOpenLoaded(ad: AppOpenAd) {
    sendEvent(EVENT_LOADED)
  }

  override fun onAppOpenRevenuePaid(ad: AppOpenAd, data: RevenueData) {
    sendEvent(EVENT_REVENUE_PAID, data.toWritableMap())
  }

  override fun onAppOpenShown(ad: AppOpenAd) {
    sendEvent(EVENT_SHOWN)
  }


  companion object {
    const val EVENT_CLICKED        = "onAppOpenClicked"
    const val EVENT_DISMISSED      = "onAppOpenDismissed"
    const val EVENT_FAILED_TO_LOAD = "onAppOpenFailedToLoad"
    const val EVENT_FAILED_TO_SHOW = "onAppOpenFailedToShow"
    const val EVENT_IMPRESSION     = "onAppOpenImpression"
    const val EVENT_LOADED         = "onAppOpenLoaded"
    const val EVENT_REVENUE_PAID   = "onAppOpenRevenuePaid"
    const val EVENT_SHOWN          = "onAppOpenShown"
  }
}
