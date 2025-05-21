package com.wortise.ads.react.extensions

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.wortise.ads.RevenueData

fun RevenueData.toWritableMap() = Arguments.createMap().also {
    it.putMap   ("revenue", revenue.toWritableMap())
    it.putString("source",  source)
}
