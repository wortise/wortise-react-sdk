package com.wortise.ads.react.extensions

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.wortise.ads.AdError

fun AdError.toWritableMap() = Arguments.createMap().also {
    it.putString("message", toString())
    it.putString("name",    name)
}
