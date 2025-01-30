import Foundation
import React
import WortiseSDK

@objc(RNWortiseInterstitial)
class RNWortiseInterstitial: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED        = "onInterstitialClicked";
    fileprivate static let EVENT_DISMISSED      = "onInterstitialDismissed";
    fileprivate static let EVENT_FAILED_TO_LOAD = "onInterstitialFailedToLoad";
    fileprivate static let EVENT_FAILED_TO_SHOW = "onInterstitialFailedToShow";
    fileprivate static let EVENT_IMPRESSION     = "onInterstitialImpression";
    fileprivate static let EVENT_LOADED         = "onInterstitialLoaded";
    fileprivate static let EVENT_SHOWN          = "onInterstitialShown";


    fileprivate var interstitialAd: WAInterstitialAd?


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseInterstitial.EVENT_CLICKED,
            RNWortiseInterstitial.EVENT_DISMISSED,
            RNWortiseInterstitial.EVENT_FAILED_TO_LOAD,
            RNWortiseInterstitial.EVENT_FAILED_TO_SHOW,
            RNWortiseInterstitial.EVENT_IMPRESSION,
            RNWortiseInterstitial.EVENT_LOADED,
            RNWortiseInterstitial.EVENT_SHOWN
        ]
    }
    

    @objc
    func destroy() {
        interstitialAd?.destroy()
        interstitialAd = nil
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(interstitialAd?.isAvailable ?? false)
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(interstitialAd?.isShowing ?? false)
    }

    @objc
    func loadAd() {
        interstitialAd?.loadAd()
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        destroy()

        interstitialAd = WAInterstitialAd(adUnitId: adUnitId)
        interstitialAd?.delegate = self
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let interstitialAd = interstitialAd else {
            resolve(false)
            return
        }

        guard let controller = RCTPresentedViewController() else {
            resolve(false)
            return
        }

        DispatchQueue.main.async {

            interstitialAd.showAd(from: controller)

            resolve(true)
        }
    }
}

extension RNWortiseInterstitial: WAInterstitialDelegate {
  
    func didClick(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_CLICKED, body: nil)
    }

    func didDismiss(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_DISMISSED, body: nil)
    }

    func didFailToLoad(interstitialAd: WAInterstitialAd, error: WAAdError) {
        let body = [
            "message": error.message,
            "name":    error.name
        ]

        sendEvent(withName: RNWortiseInterstitial.EVENT_FAILED_TO_LOAD, body: body)
    }

    func didFailToShow(interstitialAd: WAInterstitialAd, error: WAAdError) {
        let body = [
            "message": error.message,
            "name":    error.name
        ]

        sendEvent(withName: RNWortiseInterstitial.EVENT_FAILED_TO_SHOW, body: body)
    }

    func didImpress(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_IMPRESSION, body: nil)
    }

    func didLoad(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_LOADED, body: nil)
    }

    func didShow(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_SHOWN, body: nil)
    }
}
