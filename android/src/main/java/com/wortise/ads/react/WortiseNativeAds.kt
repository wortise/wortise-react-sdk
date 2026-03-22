package com.wortise.ads.react

object WortiseNativeAds {

    private val factories = mutableMapOf<String, WortiseNativeAdViewFactory>()


    @JvmStatic
    fun registerFactory(id: String, factory: WortiseNativeAdViewFactory) {
        factories[id] = factory
    }

    @JvmStatic
    fun unregisterFactory(id: String) {
        factories.remove(id)
    }


    internal fun getFactory(id: String): WortiseNativeAdViewFactory? = factories[id]
}
