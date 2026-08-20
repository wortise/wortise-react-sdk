import Foundation
import React
import WortiseSDK

@objc(RNWortiseNativeAdLoader)
class RNWortiseNativeAdLoader: RCTEventEmitter {

    fileprivate static let EVENT_CLICKED      = "onNativeClicked"
    fileprivate static let EVENT_IMPRESSION   = "onNativeImpression"
    fileprivate static let EVENT_REVENUE_PAID = "onNativeRevenuePaid"

    static var sharedAds     = [String: WANativeAd]()
    static var sharedLoaders = [String: WANativeAdLoader]()


    fileprivate var delegates = [String: NativeAdDelegateWrapper]()


    override static func requiresMainQueueSetup() -> Bool {
        return false
    }

    override func supportedEvents() -> [String]! {
        return [
            RNWortiseNativeAdLoader.EVENT_CLICKED,
            RNWortiseNativeAdLoader.EVENT_IMPRESSION,
            RNWortiseNativeAdLoader.EVENT_REVENUE_PAID
        ]
    }


    @objc(destroyAd:)
    func destroyAd(_ responseId: String) {
        RNWortiseNativeAdLoader.sharedAds.removeValue(forKey: responseId)
        RNWortiseNativeAdLoader.sharedLoaders.removeValue(forKey: responseId)?.destroy()

        delegates.removeValue(forKey: responseId)
    }

    @objc(loadAd:requestParameters:resolve:reject:)
    func loadAd(_ adUnitId:        String,
                requestParameters: NSDictionary?,
                resolve:           @escaping RCTPromiseResolveBlock,
                reject:            @escaping RCTPromiseRejectBlock) {

        let responseId = UUID().uuidString

        DispatchQueue.main.async { [weak self] in

            guard let self = self else { return }

            let delegate = NativeAdDelegateWrapper(
                responseId: responseId,
                resolve: resolve,
                reject: reject,
                owner: self
            )

            let loader = WANativeAdLoader(adUnitId: adUnitId, delegate: delegate)

            self.delegates[responseId] = delegate

            RNWortiseNativeAdLoader.sharedLoaders[responseId] = loader

            let parameters = WARequestParameters.from(requestParameters)

            loader.loadAd(parameters: parameters)
        }
    }


    fileprivate func serializeNativeAd(_ responseId: String, ad: WANativeAd) -> [String: Any?] {
        var map: [String: Any?] = [
            "advertiser":   ad.advertiser,
            "body":         ad.body,
            "callToAction": ad.callToAction,
            "headline":     ad.headline,
            "price":        ad.price,
            "responseId":   responseId,
            "store":        ad.store
        ]

        if let rating = ad.rating {
            map["rating"] = rating
        }

        if let icon = ad.icon {

            var iconMap: [String: Any?] = [:]

            iconMap["uri"] = icon.url?.absoluteString

            if let image = icon.image {
                iconMap["width"]  = Int(image.size.width)
                iconMap["height"] = Int(image.size.height)
                iconMap["scale"]  = image.scale
            }

            map["icon"] = iconMap
        }

        let images = ad.images

        if !images.isEmpty {

            var imagesArray: [[String: Any?]] = []

            for nativeImage in images {

                var imageMap: [String: Any?] = [:]

                imageMap["uri"] = nativeImage.url?.absoluteString

                if let img = nativeImage.image {
                    imageMap["width"]  = Int(img.size.width)
                    imageMap["height"] = Int(img.size.height)
                    imageMap["scale"]  = img.scale
                }

                imagesArray.append(imageMap)
            }

            map["images"] = imagesArray
        }

        if let media = ad.mediaContent {

            var mediaMap: [String: Any?] = [:]

            if let aspectRatio = media.aspectRatio {
                mediaMap["aspectRatio"] = aspectRatio
            }

            map["mediaContent"] = mediaMap
        }

        return map
    }

    fileprivate func storeAd(_ responseId: String, ad: WANativeAd) {
        RNWortiseNativeAdLoader.sharedAds[responseId] = ad
    }
}

private class NativeAdDelegateWrapper: NSObject, WANativeDelegate {

    let responseId: String

    var resolve: RCTPromiseResolveBlock?
    var reject:  RCTPromiseRejectBlock?

    weak var owner: RNWortiseNativeAdLoader?


    init(responseId: String,
         resolve:    @escaping RCTPromiseResolveBlock,
         reject:     @escaping RCTPromiseRejectBlock,
         owner:      RNWortiseNativeAdLoader) {
        self.responseId = responseId
        self.resolve    = resolve
        self.reject     = reject
        self.owner      = owner
    }


    func didClick(nativeAd: WANativeAd) {
        let body = [
            "responseId": responseId
        ]

        owner?.sendEvent(
            withName: RNWortiseNativeAdLoader.EVENT_CLICKED,
            body: body
        )
    }

    func didFailToLoad(nativeAd error: WAAdError) {
        RNWortiseNativeAdLoader.sharedLoaders.removeValue(forKey: responseId)?.destroy()

        reject?("AD_LOAD_FAILED", error.message, nil)

        resolve = nil
        reject  = nil
    }

    func didImpress(nativeAd: WANativeAd) {
        let body = [
            "responseId": responseId
        ]

        owner?.sendEvent(
            withName: RNWortiseNativeAdLoader.EVENT_IMPRESSION,
            body: body
        )
    }

    func didLoad(nativeAd: WANativeAd) {
        owner?.storeAd(responseId, ad: nativeAd)

        let data = owner?.serializeNativeAd(responseId, ad: nativeAd)

        resolve?(data)

        resolve = nil
        reject  = nil
    }

    func didPayRevenue(nativeAd: WANativeAd, data: WARevenueData) {
        let body: [String : Any] = [
            "responseId": responseId,
            "data": data.toMap()
        ]

        owner?.sendEvent(
            withName: RNWortiseNativeAdLoader.EVENT_REVENUE_PAID,
            body: body
        )
    }
}
