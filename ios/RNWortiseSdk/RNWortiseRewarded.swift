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


    override static func requiresMainQueueSetup() -> Bool {
        return false
    }


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
    

    @objc(cooldownRemainingMs:reject:)
    func cooldownRemainingMs(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(Int((self?.rewardedAd?.cooldownRemaining ?? 0) * 1000))
        }
    }

    @objc
    func destroy() {
        DispatchQueue.main.async { [weak self] in
            self?.rewardedAd?.destroy()
            self?.rewardedAd = nil
        }
    }

    @objc(isAvailable:reject:)
    func isAvailable(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.rewardedAd?.isAvailable ?? false)
        }
    }

    @objc(isInCooldown:reject:)
    func isInCooldown(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.rewardedAd?.isInCooldown ?? false)
        }
    }

    @objc(isShowing:reject:)
    func isShowing(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in
            resolve(self?.rewardedAd?.isShowing ?? false)
        }
    }

    @objc
    func loadAd() {
        DispatchQueue.main.async { [weak self] in
            self?.rewardedAd?.loadAd()
        }
    }

    @objc(setAdUnitId:)
    func setAdUnitId(_ adUnitId: String) {
        DispatchQueue.main.async { [weak self] in

            guard let self = self else {
                return
            }

            self.rewardedAd?.destroy()

            self.rewardedAd = WARewardedAd(adUnitId: adUnitId)
            self.rewardedAd?.delegate = self
        }
    }

    @objc(showAd:reject:)
    func showAd(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        DispatchQueue.main.async { [weak self] in

            guard let rewardedAd = self?.rewardedAd,
                  let controller = RCTPresentedViewController() else {
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
