package com.wortise.ads.react

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class RNWortisePackage : ReactPackage {

  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> = listOf(
    RNWortiseAdSettings    (reactContext),
    RNWortiseAppOpen       (reactContext),
    RNWortiseConsentManager(reactContext),
    RNWortiseDataManager   (reactContext),
    RNWortiseInterstitial  (reactContext),
    RNWortiseRewarded      (reactContext),
    RNWortiseSdk           (reactContext)
  )

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> = listOf(
    RNWortiseBanner()
  )
}
