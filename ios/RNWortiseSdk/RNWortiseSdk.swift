import Foundation
import React
import WortiseSDK

@objc(RNWortiseSdk)
class RNWortiseSdk: NSObject {

    @objc(getVersion:reject:)
    func getVersion(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WortiseAds.shared.version)
    }

    @objc(initialize:resolve:reject:)
    func initialize(_ assetKey: String,
                    resolve:    @escaping RCTPromiseResolveBlock,
                    reject:     RCTPromiseRejectBlock) {
        
        DispatchQueue.main.async {
            WortiseAds.shared.initialize(assetKey: assetKey) {
                resolve(nil)
            }
        }
    }

    @objc(isInitialized:reject:)
    func isInitialized(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WortiseAds.shared.isInitialized)
    }

    @objc(isReady:reject:)
    func isReady(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WortiseAds.shared.isReady)
    }

    @objc(wait:reject:)
    func wait(_ resolve: @escaping RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        WortiseAds.shared.wait {
            resolve(nil)
        }
    }
}
