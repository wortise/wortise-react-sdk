import Foundation
import React

@objc(RNWortiseBanner)
class RNWortiseBanner: RCTViewManager {

    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override func view() -> UIView! {
        return RNWortiseBannerView()
    }

  
    @objc
    func loadAd(_ reactTag: NSNumber) {
        DispatchQueue.main.async {

            guard let view = self.bridge.uiManager.view(forReactTag: reactTag) as? RNWortiseBannerView else {
                return
            }

            view.loadAd()
        }
    }
}
