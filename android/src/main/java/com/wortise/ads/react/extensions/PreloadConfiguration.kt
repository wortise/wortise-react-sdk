package com.wortise.ads.react.extensions

import com.facebook.react.bridge.ReadableMap
import com.wortise.ads.preload.PreloadConfiguration

fun ReadableMap?.toPreloadConfiguration(): PreloadConfiguration {
    val options = this ?: return PreloadConfiguration()

    val bufferSize = if (options.hasKey("bufferSize")) options.getInt("bufferSize") else 1

    val parameters = options
        .getMap("requestParameters")
        .toRequestParameters()

    return PreloadConfiguration(bufferSize, parameters)
}
