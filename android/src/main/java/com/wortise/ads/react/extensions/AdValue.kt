package com.wortise.ads.react.extensions

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.wortise.ads.AdValue

fun AdValue.toWritableMap() = Arguments.createMap().also {
    it.putString("currency",  currency)
    it.putString("precision", precision?.name)
    it.putDouble("value",     value)
}
