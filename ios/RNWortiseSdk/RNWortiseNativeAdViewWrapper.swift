import React
import UIKit
import WortiseSDK

class RNWortiseNativeAdViewWrapper: UIView {

    private var nativeAdView: WANativeAdView?


    @objc
    var factoryId: NSString? {
        didSet { tryRender() }
    }

    @objc
    var responseId: NSString? {
        didSet { tryRender() }
    }


    deinit {
        nativeAdView?.removeFromSuperview()
    }


    private func tryRender() {
        guard let factoryId  = factoryId as String?,
              let responseId = responseId as String? else {
            return
        }

        guard let factory  = WortiseNativeAds.getFactory(factoryId),
              let nativeAd = RNWortiseNativeAdLoader.sharedAds[responseId],
              let loader   = RNWortiseNativeAdLoader.sharedLoaders[responseId] else {
            return
        }

        nativeAdView?.removeFromSuperview()

        let adView = factory.createNativeAdView()

        adView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        adView.frame = bounds

        insertSubview(adView, at: 0)

        nativeAdView = adView

        loader.render(ad: nativeAd, into: adView)
    }
}
