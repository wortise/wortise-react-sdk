import Foundation
import WortiseAds

@objc(RNWortiseConsentManager)
class RNWortiseConsentManager: NSObject {

    @objc
    func canCollectData() -> Bool {
        return WAConsentManager.shared.canCollectData
    }

    @objc
    func canRequestPersonalizedAds() -> Bool {
        return WAConsentManager.shared.canRequestPersonalizedAds
    }

    @objc
    func exists() -> Bool {
        return WAConsentManager.shared.exists
    }

    @objc(request:resolve:reject)
    func request(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        let controller = RCTPresentedViewController()

        WAConsentManager.shared.request(controller) {
            resolve($0)
        }
    }

    @objc(requestIfRequired:resolve:reject)
    func requestIfRequired(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        let controller = RCTPresentedViewController()

        WAConsentManager.shared.request(ifRequired: controller) {
            resolve($0)
        }
    }
}
