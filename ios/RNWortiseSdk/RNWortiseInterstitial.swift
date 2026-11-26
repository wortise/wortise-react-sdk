import Foundation
import React
import WortiseSDK

@objc(RNWortiseInterstitial)
class RNWortiseInterstitial: RCTEventEmitter {

    fileprivate static let EVENT_ADS_EXHAUSTED     = "onInterstitialAdsExhausted"
    fileprivate static let EVENT_AD_PRELOADED      = "onInterstitialAdPreloaded"
    fileprivate static let EVENT_CLICKED           = "onInterstitialClicked"
    fileprivate static let EVENT_DISMISSED         = "onInterstitialDismissed"
    fileprivate static let EVENT_FAILED_TO_LOAD    = "onInterstitialFailedToLoad"
    fileprivate static let EVENT_FAILED_TO_PRELOAD = "onInterstitialFailedToPreload"
    fileprivate static let EVENT_FAILED_TO_SHOW    = "onInterstitialFailedToShow"
    fileprivate static let EVENT_IMPRESSION        = "onInterstitialImpression"
    fileprivate static let EVENT_LOADED            = "onInterstitialLoaded"
    fileprivate static let EVENT_REVENUE_PAID      = "onInterstitialRevenuePaid"
    fileprivate static let EVENT_SHOWN             = "onInterstitialShown"


    fileprivate var interstitialAd: WAInterstitialAd?


    override static func requiresMainQueueSetup() -> Bool {
        return false
    }


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseInterstitial.EVENT_ADS_EXHAUSTED,
            RNWortiseInterstitial.EVENT_AD_PRELOADED,
            RNWortiseInterstitial.EVENT_CLICKED,
            RNWortiseInterstitial.EVENT_DISMISSED,
            RNWortiseInterstitial.EVENT_FAILED_TO_LOAD,
            RNWortiseInterstitial.EVENT_FAILED_TO_PRELOAD,
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

    @objc
    func destroyAllPreloads() {
        DispatchQueue.main.async {
            WAInterstitialAdPreloader.destroyAll()
        }
    }

    @objc(destroyPreload:resolve:reject:)
    func destroyPreload(_ adUnitId: String, resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            resolve(WAInterstitialAdPreloader.destroy(adUnitId: adUnitId))
        }
    }

    @objc(getNumAdsAvailable:resolve:reject:)
    func getNumAdsAvailable(_ adUnitId: String, resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            resolve(WAInterstitialAdPreloader.numAdsAvailable(adUnitId: adUnitId))
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

    @objc(loadAd:)
    func loadAd(_ requestParameters: NSDictionary?) {
        DispatchQueue.main.async { [weak self] in

            let parameters = WARequestParameters.from(requestParameters)

            self?.interstitialAd?.loadAd(parameters: parameters)
        }
    }

    @objc(pollAd:resolve:reject:)
    func pollAd(_ adUnitId: String, resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            guard let self = self, let ad = WAInterstitialAdPreloader.pollAd(adUnitId: adUnitId) else {
                resolve(false)
                return
            }

            self.interstitialAd?.destroy()

            self.interstitialAd = ad
            self.interstitialAd?.delegate = self

            resolve(true)
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

    @objc(startPreload:options:resolve:reject:)
    func startPreload(_ adUnitId: String, options: NSDictionary?, resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            let started = WAInterstitialAdPreloader.start(
                adUnitId:      adUnitId,
                configuration: WAPreloadConfiguration.from(options),
                delegate:      self
            )

            resolve(started)
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

extension RNWortiseInterstitial: WAPreloadDelegate {

    func didExhaustAds(adUnitId: String) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_ADS_EXHAUSTED, body: ["adUnitId": adUnitId])
    }

    func didFailToPreloadAd(adUnitId: String, error: WAAdError) {
        var body = error.toMap()

        body["adUnitId"] = adUnitId

        sendEvent(withName: RNWortiseInterstitial.EVENT_FAILED_TO_PRELOAD, body: body)
    }

    func didPreloadAd(adUnitId: String) {
        sendEvent(withName: RNWortiseInterstitial.EVENT_AD_PRELOADED, body: ["adUnitId": adUnitId])
    }
}
