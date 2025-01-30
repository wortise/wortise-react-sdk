import Foundation
import WortiseAds

@objc(RNWortiseInterstitial)
class RNWortiseInterstitial: NSObject {

    fileprivate static let EVENT_CLICKED        = "onInterstitialClicked";
    fileprivate static let EVENT_DISMISSED      = "onInterstitialDismissed";
    fileprivate static let EVENT_FAILED_TO_LOAD = "onInterstitialFailedToLoad";
    fileprivate static let EVENT_FAILED_TO_SHOW = "onInterstitialFailedToShow";
    fileprivate static let EVENT_IMPRESSION     = "onInterstitialImpression";
    fileprivate static let EVENT_LOADED         = "onInterstitialLoaded";
    fileprivate static let EVENT_SHOWN          = "onInterstitialShown";


    fileprivate var interstitialAd: WAInterstitialAd?
  

    @objc
    func destroy() {
        interstitialAd?.destroy()
        interstitialAd = nil
    }

    @objc
    func isAvailable() -> Bool {
        return interstitialAd?.isAvailable ?? false
    }

    @objc
    func isShowing() -> Bool {
        return interstitialAd?.isShowing ?? false
    }

    @objc
    func loadAd() {
        return interstitialAd?.loadAd()
    }

    @objc(setAdUnitId:adUnitId)
    func setAdUnitId(_ adUnitId: String) -> Bool {
        destroy()

        interstitialAd = WAInterstitialAd(adUnitId: adUnitId)
        interstitialAd?.delegate = self
    }

    @objc
    func showAd() -> Bool {
        guard let interstitialAd = interstitialAd else {
            return false
        }

        let controller = RCTPresentedViewController()

        interstitialAd.showAd(from: controller)

        return true
    }
}

extension RNWortiseInterstitial: WAInterstitialDelegate {
  
  func didClick(interstitialAd: WAInterstitialAd) {
    sendEvent(withName: EVENT_CLICKED, body: nil)
  }

  func didDismiss(interstitialAd: WAInterstitialAd) {
    sendEvent(withName: EVENT_DISMISSED, body: nil)
  }

  func didFailToLoad(interstitialAd: WAInterstitialAd, error: WAAdError) {
    let body = [
        "message": error.message,
        "name":    error.name
    ]

    sendEvent(withName: EVENT_FAILED_TO_LOAD, body: body)
  }

  func didFailToShow(interstitialAd: WAInterstitialAd, error: WAAdError) {
    let body = [
        "message": error.message,
        "name":    error.name
    ]

    sendEvent(withName: EVENT_FAILED_TO_SHOW, body: body)
  }

  func didImpress(interstitialAd: WAInterstitialAd) {
    sendEvent(withName: EVENT_IMPRESSION, body: nil)
  }

  func didLoad(interstitialAd: WAInterstitialAd) {
    sendEvent(withName: EVENT_LOADED, body: nil)
  }

  func didShow(interstitialAd: WAInterstitialAd) {
    sendEvent(withName: EVENT_SHOWN, body: nil)
  }
}