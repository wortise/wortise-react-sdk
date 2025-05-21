package com.wortise.ads.react

import android.content.Context
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.facebook.react.views.view.ReactViewGroup
import com.wortise.ads.AdError
import com.wortise.ads.AdSize
import com.wortise.ads.RevenueData
import com.wortise.ads.banner.BannerAd
import com.wortise.ads.react.extensions.toWritableMap

class RNWortiseBannerView(private val reactContext: ReactContext) : BannerAd(reactContext), BannerAd.Listener, LifecycleEventListener {

  private val eventEmitter by lazy {
    reactContext.getJSModule(RCTEventEmitter::class.java)
  }

  init {
    listener = this

    reactContext.addLifecycleEventListener(this)
  }


  private fun sendEvent(eventName: String, params: WritableMap? = null) {
    eventEmitter.receiveEvent(id, eventName, params)
  }

  private fun sendOnSizeChangeEvent() {
    val event = Arguments.createMap()

    event.putDouble("height", adHeight.toDouble())
    event.putDouble("width",  adWidth .toDouble())

    sendEvent(EVENT_SIZE_CHANGE, event)
  }


  override fun onHostDestroy() {
    destroy()
  }

  override fun onHostPause() {
    pause()
  }

  override fun onHostResume() {
    resume()
  }

  override fun onBannerClicked(ad: BannerAd) {
    sendEvent(EVENT_CLICKED)
  }

  override fun onBannerFailedToLoad(ad: BannerAd, error: AdError) {
    sendEvent(EVENT_FAILED_TO_LOAD, error.toWritableMap())
  }

  override fun onBannerImpression(ad: BannerAd) {
    sendEvent(EVENT_IMPRESSION)
  }

  override fun onBannerLoaded(ad: BannerAd) {
    val height = adHeightPx
    val width  = adWidthPx 

    measure(width, height)

    layout(left, top, left + width, top + height)

    sendOnSizeChangeEvent()

    sendEvent(EVENT_LOADED)
  }

  override fun onBannerRevenuePaid(ad: BannerAd, data: RevenueData) {
    sendEvent(EVENT_REVENUE_PAID, data.toWritableMap())
  }


  companion object {
    const val EVENT_CLICKED        = "onClicked"
    const val EVENT_FAILED_TO_LOAD = "onFailedToLoad"
    const val EVENT_IMPRESSION     = "onImpression"
    const val EVENT_LOADED         = "onLoaded"
    const val EVENT_REVENUE_PAID   = "onRevenuePaid"
    const val EVENT_SIZE_CHANGE    = "onSizeChange"
  }
}
