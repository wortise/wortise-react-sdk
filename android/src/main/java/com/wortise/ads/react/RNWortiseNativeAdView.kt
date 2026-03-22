package com.wortise.ads.react

import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class RNWortiseNativeAdView : SimpleViewManager<RNWortiseNativeAdViewWrapper>() {

    override fun createViewInstance(reactContext: ThemedReactContext) = RNWortiseNativeAdViewWrapper(reactContext)

    override fun getName(): String = "RNWortiseNativeAdView"

    @ReactProp(name = "factoryId")
    fun setFactoryId(view: RNWortiseNativeAdViewWrapper, factoryId: String?) {
        view.factoryId = factoryId
    }

    @ReactProp(name = "responseId")
    fun setResponseId(view: RNWortiseNativeAdViewWrapper, responseId: String?) {
        view.responseId = responseId
    }
}
