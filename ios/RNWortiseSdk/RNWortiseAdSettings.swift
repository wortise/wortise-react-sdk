import Foundation
import React
import WortiseSDK

@objc(RNWortiseAdSettings)
class RNWortiseAdSettings: NSObject {

    @objc(getAssetKey:reject:)
    func getAssetKey(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAAdSettings.assetKey)
    }

    @objc(getMaxAdContentRating:reject:)
    func getMaxAdContentRating(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAAdSettings.maxAdContentRating?.name)
    }

    @objc(getUserId:reject:)
    func getUserId(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(nil)
    }

    @objc(isChildDirected:reject:)
    func isChildDirected(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAAdSettings.childDirected)
    }

    @objc(isTestEnabled:reject:)
    func isTestEnabled(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(WAAdSettings.testEnabled)
    }

    @objc(setChildDirected:)
    func setChildDirected(_ enabled: Bool) {
        WAAdSettings.childDirected = enabled
    }

    @objc(setMaxAdContentRating:)
    func setMaxAdContentRating(_ rating: String?) {
        WAAdSettings.maxAdContentRating = WAAdContentRating.from(name: rating)
    }

    @objc(setTestEnabled:)
    func setTestEnabled(_ enabled: Bool) {
        WAAdSettings.testEnabled = enabled
    }

    @objc(setUserId:)
    func setUserId(_ userId: String?) {
        // Not supported
    }
}
