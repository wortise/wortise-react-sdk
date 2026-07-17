package com.wortise.ads.react.extensions

import com.facebook.react.bridge.ReadableMap
import com.wortise.ads.CollapsiblePosition
import com.wortise.ads.RequestParameters

fun ReadableMap?.toRequestParameters(): RequestParameters {
    val params = this ?: return RequestParameters()

    val agent = params.getString("agent")

    val collapsible = CollapsiblePosition.fromValue(
        params.getString("collapsible")
    )

    return RequestParameters(
        agent       = agent,
        collapsible = collapsible
    )
}
