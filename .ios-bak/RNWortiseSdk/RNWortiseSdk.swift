import Foundation
import WortiseAds

@objc(RNWortiseSdk)
class RNWortiseSdk: NSObject {

    @objc
    func getVersion() -> String {
        return WortiseAds.shared.version
    }

    @objc(initialize:assetKey:resolve)
    func initialize(_ assetKey: String, resolve: RCTPromiseResolveBlock) {
        WortiseAds.shared.initialize(assetKey: assetKey) {
            resolve(nil)
        }
    }

    @objc
    func isInitialized() -> Bool {
        return WortiseAds.shared.isInitialized
    }

    @objc
    func isReady() -> Bool {
        return WortiseAds.shared.isReady
    }

    @objc(wait:resolve)
    func wait(_ resolve: RCTPromiseResolveBlock) {
        WortiseAds.shared.wait {
            resolve(nil)
        }
    }
}
