import Foundation
import React
import WortiseSDK

@objc(RNWortiseDataManager)
class RNWortiseDataManager: NSObject {

    @objc(addEmail:)
    func addEmail(_email: String) {
        // Not supported
    }

    @objc(getAge:reject:)
    func getAge(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(nil)
    }

    @objc(getEmails:reject:)
    func getEmails(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve([])
    }

    @objc(getGender:reject:)
    func getGender(_ resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        resolve(nil)
    }

    @objc(setAge:)
    func setAge(_ age: Int) {
        // Not supported
    }

    @objc(setEmails:)
    func setEmails(_ list: NSArray?) {
        // Not supported
    }

    @objc(setGender:)
    func setGender(_ gender: String?) {
        // Not supported
    }
}
