import Foundation
import WortiseSDK

@objc
public protocol WortiseNativeAdViewFactory {
    func createNativeAdView() -> WANativeAdView
}

@objc
public class WortiseNativeAds: NSObject {

    private static var factories = [String: WortiseNativeAdViewFactory]()


    @objc
    public static func registerFactory(_ id: String, factory: WortiseNativeAdViewFactory) {
        factories[id] = factory
    }

    @objc
    public static func unregisterFactory(_ id: String) {
        factories.removeValue(forKey: id)
    }


    static func getFactory(_ id: String) -> WortiseNativeAdViewFactory? {
        return factories[id]
    }
}
