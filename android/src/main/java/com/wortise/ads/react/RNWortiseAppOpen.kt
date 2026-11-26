package com.wortise.ads.react

import android.app.Activity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.wortise.ads.AdError
import com.wortise.ads.RevenueData
import com.wortise.ads.appopen.AppOpenAd
import com.wortise.ads.appopen.AppOpenAdPreloader
import com.wortise.ads.preload.PreloadListener
import com.wortise.ads.react.extensions.toPreloadConfiguration
import com.wortise.ads.react.extensions.toRequestParameters
import com.wortise.ads.react.extensions.toWritableMap

class RNWortiseAppOpen(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), AppOpenAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
  }

  private var appOpenAd: AppOpenAd? = null

  private val preloadListener = object : PreloadListener {

    override fun onAdFailedToPreload(adUnitId: String, error: AdError) {
      val params = error.toWritableMap().also {
        it.putString("adUnitId", adUnitId)
      }

      sendEvent(EVENT_FAILED_TO_PRELOAD, params)
    }

    override fun onAdPreloaded(adUnitId: String) {
      sendEvent(EVENT_AD_PRELOADED, adUnitIdMap(adUnitId))
    }

    override fun onAdsExhausted(adUnitId: String) {
      sendEvent(EVENT_ADS_EXHAUSTED, adUnitIdMap(adUnitId))
    }
  }


  init {
    reactContext.addLifecycleEventListener(this)
  }


  override fun getName(): String = "RNWortiseAppOpen"

  @ReactMethod
  fun addListener(eventName: String) {}

  @ReactMethod
  fun cooldownRemainingMs(promise: Promise) {
    promise.resolve(appOpenAd?.cooldownRemainingMs?.toDouble() ?: 0.0)
  }

  @ReactMethod
  fun destroy() {
    appOpenAd?.destroy()
    appOpenAd = null
  }

  @ReactMethod
  fun destroyAllPreloads() {
    AppOpenAdPreloader.destroyAll()
  }

  @ReactMethod
  fun destroyPreload(adUnitId: String, promise: Promise) {
    promise.resolve(AppOpenAdPreloader.destroy(adUnitId))
  }

  @ReactMethod
  fun getNumAdsAvailable(adUnitId: String, promise: Promise) {
    promise.resolve(AppOpenAdPreloader.getNumAdsAvailable(adUnitId))
  }

  @ReactMethod
  fun isAvailable(promise: Promise) {
    promise.resolve(appOpenAd?.isAvailable == true)
  }

  @ReactMethod
  fun isInCooldown(promise: Promise) {
    promise.resolve(appOpenAd?.isInCooldown == true)
  }

  @ReactMethod
  fun isShowing(promise: Promise) {
    promise.resolve(appOpenAd?.isShowing == true)
  }

  @ReactMethod
  fun loadAd(requestParameters: ReadableMap?) {
    val parameters = requestParameters.toRequestParameters()

    appOpenAd?.loadAd(parameters)
  }

  @ReactMethod
  fun pollAd(adUnitId: String, promise: Promise) {
    val ad = AppOpenAdPreloader.pollAd(adUnitId) ?: run {
      promise.resolve(false)
      return
    }

    destroy()

    appOpenAd = ad.also {
      it.listener = this
    }

    promise.resolve(true)
  }

  @ReactMethod
  fun removeListeners(count: Int) {}

  @ReactMethod
  fun setAdUnitId(adUnitId: String) {
    destroy()

    appOpenAd = AppOpenAd(reactApplicationContext.currentActivity ?: reactApplicationContext, adUnitId).also {
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

    val currentActivity = reactApplicationContext.currentActivity

    currentActivity?.let(appOpenAd::showAd) ?: appOpenAd.showAd()

    promise.resolve(true)
  }

  @ReactMethod
  fun startPreload(adUnitId: String, options: ReadableMap?, promise: Promise) {
    val started = AppOpenAdPreloader.start(
      context       = reactApplicationContext,
      adUnitId      = adUnitId,
      configuration = options.toPreloadConfiguration(),
      listener      = preloadListener
    )

    promise.resolve(started)
  }

  @ReactMethod
  fun tryToShowAd(promise: Promise) {
    val appOpenAd = appOpenAd

    if (appOpenAd == null) {
        promise.resolve(false)
        return
    }

    val currentActivity = reactApplicationContext.currentActivity

    appOpenAd.tryToShowAd(currentActivity)

    promise.resolve(true)
  }


  private fun adUnitIdMap(adUnitId: String) = Arguments.createMap().also {
    it.putString("adUnitId", adUnitId)
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
    const val EVENT_ADS_EXHAUSTED     = "onAppOpenAdsExhausted"
    const val EVENT_AD_PRELOADED      = "onAppOpenAdPreloaded"
    const val EVENT_CLICKED           = "onAppOpenClicked"
    const val EVENT_DISMISSED         = "onAppOpenDismissed"
    const val EVENT_FAILED_TO_LOAD    = "onAppOpenFailedToLoad"
    const val EVENT_FAILED_TO_PRELOAD = "onAppOpenFailedToPreload"
    const val EVENT_FAILED_TO_SHOW    = "onAppOpenFailedToShow"
    const val EVENT_IMPRESSION        = "onAppOpenImpression"
    const val EVENT_LOADED            = "onAppOpenLoaded"
    const val EVENT_REVENUE_PAID      = "onAppOpenRevenuePaid"
    const val EVENT_SHOWN             = "onAppOpenShown"
  }
}
