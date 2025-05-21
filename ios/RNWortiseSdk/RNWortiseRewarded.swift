import Foundation
import React
import WortiseSDK

@objc(RNWortiseRewarded)
class RNWortiseRewarded: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED        = "onRewardedClicked"
    fileprivate static let EVENT_COMPLETED      = "onRewardedCompleted"
    fileprivate static let EVENT_DISMISSED      = "onRewardedDismissed"
    fileprivate static let EVENT_FAILED_TO_LOAD = "onRewardedFailedToLoad"
    fileprivate static let EVENT_FAILED_TO_SHOW = "onRewardedFailedToShow"
    fileprivate static let EVENT_IMPRESSION     = "onRewardedImpression"
    fileprivate static let EVENT_LOADED         = "onRewardedLoaded"
    fileprivate static let EVENT_REVENUE_PAID   = "onRewardedRevenuePaid"
    fileprivate static let EVENT_SHOWN          = "onRewardedShown"


    fileprivate var rewardedAd: WARewardedAd?


    override func supportedEvents() -> [String]! {
        return [
            RNWortiseRewarded.EVENT_CLICKED,
            RNWortiseRewarded.EVENT_COMPLETED,
            RNWortiseRewarded.EVENT_DISMISSED,
            RNWortiseRewarded.EVENT_FAILED_TO_LOAD,
            RNWortiseRewarded.EVENT_FAILED_TO_SHOW,
            RNWortiseRewarded.EVENT_IMPRESSION,
            RNWortiseRewarded.EVENT_LOADED,
            RNWortiseRewarded.EVENT_REVENUE_PAID,
            RNWortiseRewarded.EVENT_SHOWN
        ]
    }
    

    @objc
    func destroy() {
        rewardedAd?.destroy()
        rewardedAd = nil
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(rewardedAd?.isAvailable ?? false)
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(rewardedAd?.isShowing ?? false)
    }

    @objc
    func loadAd() {
        rewardedAd?.loadAd()
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        destroy()

        rewardedAd = WARewardedAd(adUnitId: adUnitId)
        rewardedAd?.delegate = self
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let rewardedAd = rewardedAd else {
            resolve(false)
            return
        }

        DispatchQueue.main.async {

            guard let controller = RCTPresentedViewController() else {
                resolve(false)
                return
            }

            rewardedAd.showAd(from: controller)

            resolve(true)
        }
    }
}

extension RNWortiseRewarded: WARewardedDelegate {
  
    func didClick(rewardedAd: WARewardedAd) {
        sendEvent(withName: RNWortiseRewarded.EVENT_CLICKED, body: nil)
    }

    func didComplete(rewardedAd: WARewardedAd, reward: WAReward) {
        let body: [String : Any?] = [
            "amount":  reward.amount,
            "label":   reward.label,
            "success": reward.success
        ]

        sendEvent(withName: RNWortiseRewarded.EVENT_COMPLETED, body: body)
    }

    func didDismiss(rewardedAd: WARewardedAd) {
        sendEvent(withName: RNWortiseRewarded.EVENT_DISMISSED, body: nil)
    }

    func didFailToLoad(rewardedAd: WARewardedAd, error: WAAdError) {
        sendEvent(withName: RNWortiseRewarded.EVENT_FAILED_TO_LOAD, body: error.toMap())
    }

    func didFailToShow(rewardedAd: WARewardedAd, error: WAAdError) {
        sendEvent(withName: RNWortiseRewarded.EVENT_FAILED_TO_SHOW, body: error.toMap())
    }

    func didImpress(rewardedAd: WARewardedAd) {
        sendEvent(withName: RNWortiseRewarded.EVENT_IMPRESSION, body: nil)
    }

    func didLoad(rewardedAd: WARewardedAd) {
        sendEvent(withName: RNWortiseRewarded.EVENT_LOADED, body: nil)
    }

    func didPayRevenue(rewardedAd: WARewardedAd, data: WARevenueData) {
        sendEvent(withName: RNWortiseRewarded.EVENT_REVENUE_PAID, body: data.toMap())
    }

    func didShow(rewardedAd: WARewardedAd) {
        sendEvent(withName: RNWortiseRewarded.EVENT_SHOWN, body: nil)
    }
}
