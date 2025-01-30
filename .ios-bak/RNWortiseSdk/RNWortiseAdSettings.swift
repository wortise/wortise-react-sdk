import Foundation
import WortiseAds

@objc(RNWortiseAdSettings)
class RNWortiseAdSettings: NSObject {

    @objc
    func getAssetKey() -> String? {
        return WAAdSettings.assetKey
    }

    @objc
    func getMaxAdContentRating() -> String? {
        return WAAdSettings.maxAdContentRating?.name
    }

    @objc
    func getUserId() -> String? {
        return nil
    }

    @objc
    func isChildDirected() -> Bool {
        return WAAdSettings.childDirected
    }

    @objc
    func isTestEnabled() -> Bool {
        return WAAdSettings.testEnabled
    }

    @objc(setChildDirected:enabled)
    func setChildDirected(_ enabled: Bool) {
        return WAAdSettings.childDirected = enabled
    }

    @objc(setMaxAdContentRating:rating)
    func setMaxAdContentRating(_ rating: String?) {
        let value = WAAdContentRating.from(rating)
        WAAdSettings.maxAdContentRating = value
    }

    @objc(setTestEnabled:enabled)
    func setTestEnabled(_ enabled: Bool) {
        WAAdSettings.testEnabled = enabled
    }

    @objc(setUserId:userId)
    func setUserId(_ userId: String?) {
        // Not supported
    }
}
