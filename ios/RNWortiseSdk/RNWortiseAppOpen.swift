import Foundation
import React
import WortiseSDK

@objc(RNWortiseAppOpen)
class RNWortiseAppOpen: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED        = "onAppOpenClicked"
    fileprivate static let EVENT_DISMISSED      = "onAppOpenDismissed"
    fileprivate static let EVENT_FAILED_TO_LOAD = "onAppOpenFailedToLoad"
    fileprivate static let EVENT_FAILED_TO_SHOW = "onAppOpenFailedToShow"
    fileprivate static let EVENT_IMPRESSION     = "onAppOpenImpression"
    fileprivate static let EVENT_LOADED         = "onAppOpenLoaded"
    fileprivate static let EVENT_REVENUE_PAID   = "onAppOpenRevenuePaid"
    fileprivate static let EVENT_SHOWN          = "onAppOpenShown"


    fileprivate var appOpenAd: WAAppOpenAd?


    override static func requiresMainQueueSetup() -> Bool {
        return false
    }


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseAppOpen.EVENT_CLICKED,
            RNWortiseAppOpen.EVENT_DISMISSED,
            RNWortiseAppOpen.EVENT_FAILED_TO_LOAD,
            RNWortiseAppOpen.EVENT_FAILED_TO_SHOW,
            RNWortiseAppOpen.EVENT_IMPRESSION,
            RNWortiseAppOpen.EVENT_LOADED,
            RNWortiseAppOpen.EVENT_REVENUE_PAID,
            RNWortiseAppOpen.EVENT_SHOWN
        ]
    }
    

    @objc(cooldownRemainingMs:reject:)
    func cooldownRemainingMs(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(Int((self?.appOpenAd?.cooldownRemaining ?? 0) * 1000))
        }
    }

    @objc
    func destroy() {
        DispatchQueue.main.async { [weak self] in
            self?.appOpenAd?.destroy()
            self?.appOpenAd = nil
        }
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.appOpenAd?.isAvailable ?? false)
        }
    }

    @objc(isInCooldown:reject:)
    func isInCooldown(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.appOpenAd?.isInCooldown ?? false)
        }
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.appOpenAd?.isShowing ?? false)
        }
    }

    @objc
    func loadAd() {
        DispatchQueue.main.async { [weak self] in
            self?.appOpenAd?.loadAd()
        }
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        DispatchQueue.main.async { [weak self] in

            guard let self = self else {
                return
            }

            self.appOpenAd?.destroy()

            self.appOpenAd = WAAppOpenAd(adUnitId: adUnitId)
            self.appOpenAd?.delegate = self
        }
    }

    @objc(setAutoReload:)
    func setAutoReload(_ autoReload: Bool) {
        DispatchQueue.main.async { [weak self] in
            self?.appOpenAd?.autoReload = autoReload
        }
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            guard let appOpenAd = self?.appOpenAd,
                  let controller = RCTPresentedViewController() else {
                resolve(false)
                return
            }

            appOpenAd.showAd(from: controller)

            resolve(true)
        }
    }

    @objc(tryToShowAd:reject:)
    func tryToShowAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            guard let appOpenAd = self?.appOpenAd,
                  let controller = RCTPresentedViewController() else {
                resolve(false)
                return
            }

            appOpenAd.tryToShowAd(from: controller)

            resolve(true)
        }
    }
}

extension RNWortiseAppOpen: WAAppOpenDelegate {
  
    func didClick(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_CLICKED, body: nil)
    }

    func didDismiss(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_DISMISSED, body: nil)
    }

    func didFailToLoad(appOpenAd: WAAppOpenAd, error: WAAdError) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_FAILED_TO_LOAD, body: error.toMap())
    }

    func didFailToShow(appOpenAd: WAAppOpenAd, error: WAAdError) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_FAILED_TO_SHOW, body: error.toMap())
    }

    func didImpress(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_IMPRESSION, body: nil)
    }

    func didLoad(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_LOADED, body: nil)
    }

    func didPayRevenue(appOpenAd: WAAppOpenAd, data: WARevenueData) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_REVENUE_PAID, body: data.toMap())
    }

    func didShow(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_SHOWN, body: nil)
    }
}
