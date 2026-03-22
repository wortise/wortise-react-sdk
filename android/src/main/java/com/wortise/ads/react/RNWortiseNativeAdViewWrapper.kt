package com.wortise.ads.react

import android.content.Context
import android.widget.FrameLayout

class RNWortiseNativeAdViewWrapper(context: Context) : FrameLayout(context) {

    var factoryId: String? = null
        set(value) {
            field = value
            tryRender()
        }

    var responseId: String? = null
        set(value) {
            field = value
            tryRender()
        }


    private fun tryRender() {
        val factoryId  = factoryId  ?: return
        val responseId = responseId ?: return

        val factory = WortiseNativeAds.getFactory(factoryId) ?: return

        val ad      = RNWortiseNativeAdLoader.loadedAds[responseId] ?: return
        val loader  = RNWortiseNativeAdLoader.adLoaders[responseId] ?: return

        removeAllViews()

        val adView = factory.createNativeAdView()

        adView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        addView(adView)

        loader.renderAd(adView, ad)
    }
}
