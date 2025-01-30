import Foundation
import React
import WortiseSDK

@objc(RNWortiseAppOpen)
class RNWortiseAppOpen: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED        = "onAppOpenClicked";
    fileprivate static let EVENT_DISMISSED      = "onAppOpenDismissed";
    fileprivate static let EVENT_FAILED_TO_LOAD = "onAppOpenFailedToLoad";
    fileprivate static let EVENT_FAILED_TO_SHOW = "onAppOpenFailedToShow";
    fileprivate static let EVENT_IMPRESSION     = "onAppOpenImpression";
    fileprivate static let EVENT_LOADED         = "onAppOpenLoaded";
    fileprivate static let EVENT_SHOWN          = "onAppOpenShown";


    fileprivate var appOpenAd: WAAppOpenAd?


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseAppOpen.EVENT_CLICKED,
            RNWortiseAppOpen.EVENT_DISMISSED,
            RNWortiseAppOpen.EVENT_FAILED_TO_LOAD,
            RNWortiseAppOpen.EVENT_FAILED_TO_SHOW,
            RNWortiseAppOpen.EVENT_IMPRESSION,
            RNWortiseAppOpen.EVENT_LOADED,
            RNWortiseAppOpen.EVENT_SHOWN
        ]
    }
    

    @objc
    func destroy() {
        appOpenAd?.destroy()
        appOpenAd = nil
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(appOpenAd?.isAvailable ?? false)
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(appOpenAd?.isShowing ?? false)
    }

    @objc
    func loadAd() {
        appOpenAd?.loadAd()
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        destroy()

        appOpenAd = WAAppOpenAd(adUnitId: adUnitId)
        appOpenAd?.delegate = self
    }

    @objc(setAutoReload:)
    func setAutoReload(_ autoReload: Bool) {
        appOpenAd?.autoReload = autoReload
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let appOpenAd = appOpenAd else {
            resolve(false)
            return
        }

        guard let controller = RCTPresentedViewController() else {
            resolve(false)
            return
        }

        DispatchQueue.main.async {

            appOpenAd.showAd(from: controller)

            resolve(true)
        }
    }

    @objc(tryToShowAd:reject:)
    func tryToShowAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let appOpenAd = appOpenAd else {
            resolve(false)
            return
        }

        guard let controller = RCTPresentedViewController() else {
            resolve(false)
            return
        }

        DispatchQueue.main.async {

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
        let body = [
            "message": error.message,
            "name":    error.name
        ]

        sendEvent(withName: RNWortiseAppOpen.EVENT_FAILED_TO_LOAD, body: body)
    }

    func didFailToShow(appOpenAd: WAAppOpenAd, error: WAAdError) {
        let body = [
            "message": error.message,
            "name":    error.name
        ]

        sendEvent(withName: RNWortiseAppOpen.EVENT_FAILED_TO_SHOW, body: body)
    }

    func didImpress(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_IMPRESSION, body: nil)
    }

    func didLoad(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_LOADED, body: nil)
    }

    func didShow(appOpenAd: WAAppOpenAd) {
        sendEvent(withName: RNWortiseAppOpen.EVENT_SHOWN, body: nil)
    }
}
