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
import com.wortise.ads.rewarded.RewardedAd
import com.wortise.ads.rewarded.models.Reward
import com.wortise.ads.react.extensions.toWritableMap

class RNWortiseRewarded(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), RewardedAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
  }

  private var rewardedAd: RewardedAd? = null


  override fun getName(): String = "RNWortiseRewarded"

  @ReactMethod
  fun addListener(eventName: String) {}

  @ReactMethod
  fun destroy() {
    rewardedAd?.destroy()
    rewardedAd = null
  }

  @ReactMethod
  fun isAvailable(promise: Promise) {
    promise.resolve(rewardedAd?.isAvailable == true)
  }

  @ReactMethod
  fun isShowing(promise: Promise) {
    promise.resolve(rewardedAd?.isShowing == true)
  }

  @ReactMethod
  fun loadAd() {
    rewardedAd?.loadAd()
  }

  @ReactMethod
  fun removeListeners(count: Int) {}

  @ReactMethod
  fun setAdUnitId(adUnitId: String) {
    val currentActivity = currentActivity ?: return

    destroy()

    rewardedAd = RewardedAd(currentActivity, adUnitId).also {
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

    currentActivity?.let(rewardedAd::showAd) ?: rewardedAd.showAd()

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
    const val EVENT_CLICKED        = "onRewardedClicked"
    const val EVENT_COMPLETED      = "onRewardedCompleted"
    const val EVENT_DISMISSED      = "onRewardedDismissed"
    const val EVENT_FAILED_TO_LOAD = "onRewardedFailedToLoad"
    const val EVENT_FAILED_TO_SHOW = "onRewardedFailedToShow"
    const val EVENT_IMPRESSION     = "onRewardedImpression"
    const val EVENT_LOADED         = "onRewardedLoaded"
    const val EVENT_REVENUE_PAID   = "onRewardedRevenuePaid"
    const val EVENT_SHOWN          = "onRewardedShown"
  }
}
