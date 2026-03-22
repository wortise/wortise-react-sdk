import Foundation
import React

@objc(RNWortiseNativeAdView)
class RNWortiseNativeAdView: RCTViewManager {

    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override func view() -> UIView! {
        return RNWortiseNativeAdViewWrapper()
    }
}
