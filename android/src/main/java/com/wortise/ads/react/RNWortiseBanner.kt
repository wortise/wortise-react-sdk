package com.wortise.ads.react

import android.content.Context
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.wortise.ads.AdSize
import com.wortise.ads.banner.BannerAd

class RNWortiseBanner : SimpleViewManager<RNWortiseBannerView>() {

  override fun createViewInstance(reactContext: ThemedReactContext) = RNWortiseBannerView(reactContext)

  override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any> {
    val events = arrayOf(
      RNWortiseBannerView.EVENT_CLICKED,
      RNWortiseBannerView.EVENT_FAILED_TO_LOAD,
      RNWortiseBannerView.EVENT_IMPRESSION,
      RNWortiseBannerView.EVENT_LOADED,
      RNWortiseBannerView.EVENT_REVENUE_PAID,
      RNWortiseBannerView.EVENT_SIZE_CHANGE
    )

    val map = mutableMapOf<String, Any>()

    events.forEach {
      map.put(it, mapOf("registrationName" to it))
    }

    return map
  }

  override fun getName(): String = "RNWortiseBanner"

  override fun receiveCommand(view: RNWortiseBannerView, commandId: String, args: ReadableArray?) {
    when (commandId) {
      "loadAd" -> view.loadAd()
    }
  }


  private fun getAdSize(context: Context, adSize: ReadableMap): AdSize {
    val height = adSize.getInt   ("height")
    val width  = adSize.getInt   ("width")
    val type   = adSize.getString("type")

    return when (type) {

      "anchored" -> AdSize.getAnchoredAdaptiveBannerAdSize(context, width)

      "inline"   -> AdSize.getInlineAdaptiveBannerAdSize(context, width, height)

      else       -> AdSize(width, height)
    }
  }

  
  @ReactProp(name = "adSize")
  fun setAdSize(view: RNWortiseBannerView, adSize: ReadableMap?) {
    if (adSize == null) {
      return
    }

    view.adSize = getAdSize(view.context, adSize)
  }

  @ReactProp(name = "adUnitId")
  fun setAdUnitId(view: RNWortiseBannerView, adUnitId: String?) {
    view.adUnitId = adUnitId
  }

  @ReactProp(name = "autoRefreshTime", defaultInt = 0)
  fun setAutoRefresh(view: RNWortiseBannerView, autoRefreshTime: Int) {
    view.autoRefreshTime = autoRefreshTime.toLong()
  }
}
