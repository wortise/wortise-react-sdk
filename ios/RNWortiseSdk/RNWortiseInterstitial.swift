import Foundation
import React
import WortiseSDK

@objc(RNWortiseInterstitial)
class RNWortiseInterstitial: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED        = "onInterstitialClicked"
    fileprivate static let EVENT_DISMISSED      = "onInterstitialDismissed"
    fileprivate static let EVENT_FAILED_TO_LOAD = "onInterstitialFailedToLoad"
    fileprivate static let EVENT_FAILED_TO_SHOW = "onInterstitialFailedToShow"
    fileprivate static let EVENT_IMPRESSION     = "onInterstitialImpression"
    fileprivate static let EVENT_LOADED         = "onInterstitialLoaded"
    fileprivate static let EVENT_REVENUE_PAID   = "onInterstitialRevenuePaid"
    fileprivate static let EVENT_SHOWN          = "onInterstitialShown"


    fileprivate var interstitialAd: WAInterstitialAd?


    override static func requiresMainQueueSetup() -> Bool {
        return false
    }


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseInterstitial.EVENT_CLICKED,
            RNWortiseInterstitial.EVENT_DISMISSED,
            RNWortiseInterstitial.EVENT_FAILED_TO_LOAD,
            RNWortiseInterstitial.EVENT_FAILED_TO_SHOW,
            RNWortiseInterstitial.EVENT_IMPRESSION,
            RNWortiseInterstitial.EVENT_LOADED,
            RNWortiseInterstitial.EVENT_REVENUE_PAID,
            RNWortiseInterstitial.EVENT_SHOWN
        ]
    }
    


    @objc(cooldownRemainingMs:reject:)
    func cooldownRemainingMs(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(Int((self?.interstitialAd?.cooldownRemaining ?? 0) * 1000))
        }
    }

    @objc
    func destroy() {
        DispatchQueue.main.async { [weak self] in
            self?.interstitialAd?.destroy()
            self?.interstitialAd = nil
        }
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.interstitialAd?.isAvailable ?? false)
        }
    }

    @objc(isInCooldown:reject:)
    func isInCooldown(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.interstitialAd?.isInCooldown ?? false)
        }
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.interstitialAd?.isShowing ?? false)
        }
    }

    @objc
    func loadAd() {
        DispatchQueue.main.async { [weak self] in
            self?.interstitialAd?.loadAd()
        }
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        DispatchQueue.main.async { [weak self] in

            guard let self = self else {
                return
            }

            self.interstitialAd?.destroy()

            self.interstitialAd = WAInterstitialAd(adUnitId: adUnitId)
            self.interstitialAd?.delegate = self
        }
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            guard let interstitialAd = self?.interstitialAd,
                  let controller = RCTPresentedViewController() else {
                resolve(false)
                return
            }

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
        sendEvent(withName: RNWortiseInterstitial.EVENT_FAILED_TO_LOAD, body: error.toMap())
    }

    func didFailToShow(interstitialAd: WAInterstitialAd, error: WAAdError) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_FAILED_TO_SHOW, body: error.toMap())
    }

    func didImpress(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_IMPRESSION, body: nil)
    }

    func didLoad(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_LOADED, body: nil)
    }

    func didPayRevenue(interstitialAd: WAInterstitialAd, data: WARevenueData) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_REVENUE_PAID, body: data.toMap())
    }

    func didShow(interstitialAd: WAInterstitialAd) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_SHOWN, body: nil)
    }
}
