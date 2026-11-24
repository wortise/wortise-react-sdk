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
import com.wortise.ads.preload.PreloadListener
import com.wortise.ads.react.extensions.toPreloadConfiguration
import com.wortise.ads.react.extensions.toRequestParameters
import com.wortise.ads.react.extensions.toWritableMap
import com.wortise.ads.rewarded.RewardedAd
import com.wortise.ads.rewarded.RewardedAdPreloader
import com.wortise.ads.rewarded.models.Reward

class RNWortiseRewarded(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), RewardedAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
  }

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

  private var rewardedAd: RewardedAd? = null


  init {
    reactContext.addLifecycleEventListener(this)
  }


  override fun getName(): String = "RNWortiseRewarded"

  @ReactMethod
  fun addListener(eventName: String) {}

  @ReactMethod
  fun cooldownRemainingMs(promise: Promise) {
    promise.resolve(rewardedAd?.cooldownRemainingMs?.toDouble() ?: 0.0)
  }

  @ReactMethod
  fun destroy() {
    rewardedAd?.destroy()
    rewardedAd = null
  }

  @ReactMethod
  fun destroyAllPreloads() {
    RewardedAdPreloader.destroyAll()
  }

  @ReactMethod
  fun destroyPreload(adUnitId: String, promise: Promise) {
    promise.resolve(RewardedAdPreloader.destroy(adUnitId))
  }

  @ReactMethod
  fun getNumAdsAvailable(adUnitId: String, promise: Promise) {
    promise.resolve(RewardedAdPreloader.getNumAdsAvailable(adUnitId))
  }

  @ReactMethod
  fun isAvailable(promise: Promise) {
    promise.resolve(rewardedAd?.isAvailable == true)
  }

  @ReactMethod
  fun isInCooldown(promise: Promise) {
    promise.resolve(rewardedAd?.isInCooldown == true)
  }

  @ReactMethod
  fun isShowing(promise: Promise) {
    promise.resolve(rewardedAd?.isShowing == true)
  }

  @ReactMethod
  fun loadAd(requestParameters: ReadableMap?) {
    val parameters = requestParameters.toRequestParameters()

    rewardedAd?.loadAd(parameters)
  }

  @ReactMethod
  fun pollAd(adUnitId: String, promise: Promise) {
    val ad = RewardedAdPreloader.pollAd(adUnitId) ?: run {
      promise.resolve(false)
      return
    }

    destroy()

    rewardedAd = ad.also {
      it.listener = this
    }

    promise.resolve(true)
  }

  @ReactMethod
  fun removeListeners(count: Int) {}

  @ReactMethod
  fun setAdUnitId(adUnitId: String) {
    destroy()

    rewardedAd = RewardedAd(reactApplicationContext.currentActivity ?: reactApplicationContext, adUnitId).also {
      it.listener = this
    }
  }

  @ReactMethod
  fun showAd(promise: Promise) {
    val rewardedAd = rewardedAd

    if (rewardedAd == null) {
      promise.resolve(false)
      return
    }

    val currentActivity = reactApplicationContext.currentActivity

    currentActivity?.let(rewardedAd::showAd) ?: rewardedAd.showAd()

    promise.resolve(true)
  }

  @ReactMethod
  fun startPreload(adUnitId: String, options: ReadableMap?, promise: Promise) {
    val started = RewardedAdPreloader.start(
      context       = reactApplicationContext,
      adUnitId      = adUnitId,
      configuration = options.toPreloadConfiguration(),
      listener      = preloadListener
    )

    promise.resolve(started)
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

  override fun onRewardedClicked(ad: RewardedAd) {
    sendEvent(EVENT_CLICKED)
  }

  override fun onRewardedCompleted(ad: RewardedAd, reward: Reward) {
    val event = Arguments.createMap()

    event.putInt    ("amount",  reward.amount)
    event.putString ("label",   reward.label)
    event.putBoolean("success", reward.success)

    sendEvent(EVENT_COMPLETED, event)
  }

  override fun onRewardedDismissed(ad: RewardedAd) {
    sendEvent(EVENT_DISMISSED)
  }

  override fun onRewardedFailedToLoad(ad: RewardedAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_LOAD, error.toWritableMap())
  }

  override fun onRewardedFailedToShow(ad: RewardedAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_SHOW, error.toWritableMap())
  }

  override fun onRewardedImpression(ad: RewardedAd) {
    sendEvent(EVENT_IMPRESSION)
  }

  override fun onRewardedLoaded(ad: RewardedAd) {
    sendEvent(EVENT_LOADED)
  }

  override fun onRewardedRevenuePaid(ad: RewardedAd, data: RevenueData) {
    sendEvent(EVENT_REVENUE_PAID, data.toWritableMap())
  }

  override fun onRewardedShown(ad: RewardedAd) {
    sendEvent(EVENT_SHOWN)
  }


  companion object {
    const val EVENT_ADS_EXHAUSTED     = "onRewardedAdsExhausted"
    const val EVENT_AD_PRELOADED      = "onRewardedAdPreloaded"
    const val EVENT_CLICKED           = "onRewardedClicked"
    const val EVENT_COMPLETED         = "onRewardedCompleted"
    const val EVENT_DISMISSED         = "onRewardedDismissed"
    const val EVENT_FAILED_TO_LOAD    = "onRewardedFailedToLoad"
    const val EVENT_FAILED_TO_PRELOAD = "onRewardedFailedToPreload"
    const val EVENT_FAILED_TO_SHOW    = "onRewardedFailedToShow"
    const val EVENT_IMPRESSION        = "onRewardedImpression"
    const val EVENT_LOADED            = "onRewardedLoaded"
    const val EVENT_REVENUE_PAID      = "onRewardedRevenuePaid"
    const val EVENT_SHOWN             = "onRewardedShown"
  }
}
