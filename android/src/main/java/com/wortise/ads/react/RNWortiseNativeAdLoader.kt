package com.wortise.ads.react

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
import com.wortise.ads.natives.NativeAd
import com.wortise.ads.natives.NativeAdLoader
import com.wortise.ads.react.extensions.toRequestParameters
import com.wortise.ads.react.extensions.toWritableMap
import java.util.UUID

class RNWortiseNativeAdLoader(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {

    private val eventEmitter by lazy {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
    }


    init {
        reactContext.addLifecycleEventListener(this)
    }


    override fun getName(): String = "RNWortiseNativeAdLoader"

    @ReactMethod
    fun addListener(eventName: String) {}

    @ReactMethod
    fun removeListeners(count: Int) {}

    @ReactMethod
    fun loadAd(adUnitId: String, requestParameters: ReadableMap?, promise: Promise) {
        val context = reactApplicationContext.currentActivity ?: reactApplicationContext

        val responseId = UUID.randomUUID().toString()

        val listener = object : NativeAdLoader.Listener {

            override fun onNativeLoaded(ad: NativeAd) {
                loadedAds[responseId] = ad

                promise.resolve(serializeNativeAd(responseId, ad))
            }

            override fun onNativeFailedToLoad(error: AdError) {
                adLoaders.remove(responseId)?.destroy()

                promise.reject("AD_LOAD_FAILED", error.toString())
            }

            override fun onNativeClicked(ad: NativeAd) {
                val params = Arguments.createMap()

                params.putString("responseId", responseId)

                sendEvent(EVENT_CLICKED, params)
            }

            override fun onNativeImpression(ad: NativeAd) {
                val params = Arguments.createMap()

                params.putString("responseId", responseId)

                sendEvent(EVENT_IMPRESSION, params)
            }

            override fun onNativeRevenuePaid(ad: NativeAd, data: RevenueData) {
                val params = Arguments.createMap()

                params.putString("responseId", responseId)
                params.putMap   ("data",       data.toWritableMap())

                sendEvent(EVENT_REVENUE_PAID, params)
            }
        }

        val loader = NativeAdLoader(context, adUnitId, listener)

        adLoaders[responseId] = loader

        val parameters = requestParameters.toRequestParameters()

        loader.loadAd(parameters)
    }

    @ReactMethod
    fun destroyAd(responseId: String) {
        loadedAds.remove(responseId)
        adLoaders.remove(responseId)?.destroy()
    }


    private fun serializeNativeAd(responseId: String, ad: NativeAd): WritableMap {
        val map = Arguments.createMap()

        map.putString("responseId",   responseId)
        map.putString("headline",     ad.headline)
        map.putString("body",         ad.body)
        map.putString("callToAction", ad.callToAction)
        map.putString("advertiser",   ad.advertiser)
        map.putString("price",        ad.price)
        map.putString("store",        ad.store)

        ad.rating?.let { map.putDouble("rating", it) }

        ad.icon?.let { icon ->
            val iconMap = Arguments.createMap()

            icon.uri?.let { iconMap.putString("uri", it.toString()) }

            icon.drawable?.let { drawable ->
                iconMap.putInt("width",  drawable.intrinsicWidth)
                iconMap.putInt("height", drawable.intrinsicHeight)
            }

            map.putMap("icon", iconMap)
        }

        val images = ad.images

        if (images.isNotEmpty()) {
            val imagesArray = Arguments.createArray()

            for (image in images) {
                val imageMap = Arguments.createMap()

                image.uri?.let { imageMap.putString("uri", it.toString()) }

                image.drawable?.let { drawable ->
                    imageMap.putInt("width",  drawable.intrinsicWidth)
                    imageMap.putInt("height", drawable.intrinsicHeight)
                }

                imagesArray.pushMap(imageMap)
            }

            map.putArray("images", imagesArray)
        }

        ad.mediaContent?.let { media ->
            val mediaMap = Arguments.createMap()

            media.aspectRatio?.let { mediaMap.putDouble("aspectRatio", it.toDouble()) }

            map.putMap("mediaContent", mediaMap)
        }

        return map
    }

    private fun sendEvent(eventName: String, params: WritableMap? = null) {
        eventEmitter.emit(eventName, params)
    }


    override fun onHostDestroy() {
        loadedAds.clear()

        adLoaders.values.forEach { it.destroy() }
        adLoaders.clear()
    }

    override fun onHostPause() {}

    override fun onHostResume() {}


    companion object {
        const val EVENT_CLICKED      = "onNativeClicked"
        const val EVENT_IMPRESSION   = "onNativeImpression"
        const val EVENT_REVENUE_PAID = "onNativeRevenuePaid"


        val adLoaders = mutableMapOf<String, NativeAdLoader>()

        val loadedAds = mutableMapOf<String, NativeAd>()
    }
}
