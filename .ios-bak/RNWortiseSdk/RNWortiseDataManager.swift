import Foundation
import WortiseAds

@objc(RNWortiseDataManager)
class RNWortiseDataManager: NSObject {

    @objc(addEmail:email)
    func addEmail(_email: String) {
        // Not supported
    }

    @objc
    func getAge() -> Int? {
        return nil
    }

    @objc
    func getEmails() -> [String] {
        return []
    }

    @objc
    func getGender() -> String? {
        return nil
    }

    @objc(setAge:age)
    func setAge(_ age: Int?) {
        // Not supported
    }

    @objc(setEmails:list)
    func setEmails(_ list: [String]?) {
        // Not supported
    }

    @objc(setGender:gender)
    func setGender(_ gender: String?) {
        // Not supported
    }
}
