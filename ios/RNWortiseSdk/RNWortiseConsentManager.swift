import Foundation
import React
import WortiseSDK

@objc(RNWortiseConsentManager)
class RNWortiseConsentManager: NSObject {

    @objc(canCollectData:reject:)
    func canCollectData(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAConsentManager.shared.canCollectData)
    }

    @objc(canRequestPersonalizedAds:reject:)
    func canRequestPersonalizedAds(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAConsentManager.shared.canRequestPersonalizedAds)
    }

    @objc(exists:reject:)
    func exists(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAConsentManager.shared.exists)
    }

    @objc(request:reject:)
    func request(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let controller = RCTPresentedViewController() else {
            resolve(false)
            return
        }

        WAConsentManager.shared.request(controller) {
            resolve($0)
        }
    }

    @objc(requestIfRequired:reject:)
    func requestIfRequired(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        guard let controller = RCTPresentedViewController() else {
            resolve(false)
            return
        }

        WAConsentManager.shared.request(ifRequired: controller) {
            resolve($0)
        }
    }
}
