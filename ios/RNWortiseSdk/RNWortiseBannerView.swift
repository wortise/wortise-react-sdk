import React
import UIKit
import WortiseSDK

@objc(RNWortiseBannerView)
class RNWortiseBannerView: UIView {
  
    private var _adUnitId: String?

    private var _adSize: WAAdSize?

    private var _autoRefreshTime: Double = 0

    private var bannerAd: WABannerAd?


    @objc
    var onClicked: RCTBubblingEventBlock?
    @objc
    var onFailedToLoad: RCTBubblingEventBlock?
    @objc 
    var onImpression: RCTBubblingEventBlock?
    @objc
    var onLoaded: RCTBubblingEventBlock?
    @objc
    var onRevenuePaid: RCTBubblingEventBlock?
    @objc
    var onSizeChange: RCTBubblingEventBlock?


    @objc
    var adUnitId: NSString? {
        didSet { _adUnitId = adUnitId as String? }
    }

    @objc
    var adSize: NSDictionary? {
        didSet {
            guard
                let dict   = adSize,
                let width  = dict["width"]  as? Int,
                let height = dict["height"] as? Int,
                let type   = dict["type"]   as? String
            else {
                return
            }

            _adSize = createAdSize(type: type, width: width, height: height)
        }
    }

    @objc
    var autoRefreshTime: NSNumber? {
        didSet { _autoRefreshTime = (autoRefreshTime?.doubleValue ?? 0) / 1000 }
    }


    override init(frame: CGRect) {
        super.init(frame: frame)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    deinit {
        bannerAd?.destroy()
        bannerAd = nil
    }


    fileprivate func createAdSize(type: String, width: Int, height: Int) -> WAAdSize {
        let h = CGFloat(height)
        let w = CGFloat(width)

        switch type {
        case "anchored":
            return WAAdSize.getAnchoredAdaptiveBannerAdSize(width: w)

        case "inline":
            return WAAdSize.getInlineAdaptiveBannerAdSize(width: w, maxHeight: h)

        default:
            return WAAdSize(width: w, height: h)
        }
    }


    @objc
    func loadAd() {
        guard let adUnitId = _adUnitId, let adSize = _adSize else {
            return
        }

        bannerAd?.removeFromSuperview()
        bannerAd?.destroy()
        bannerAd = nil

        let banner = WABannerAd(frame: bounds)
    
        banner.adSize             = adSize
        banner.adUnitId           = adUnitId
        banner.autoRefreshTime    = _autoRefreshTime
        banner.delegate           = self
        banner.rootViewController = RCTPresentedViewController()
    
        addSubview(banner)

        bannerAd = banner

        banner.loadAd()
    }
}

extension RNWortiseBannerView: WABannerDelegate {

    func didClick(bannerAd: WABannerAd) {
        onClicked?([:])
    }
    
    func didFailToLoad(bannerAd: WABannerAd, error: WAAdError) {
        onFailedToLoad?(error.toMap())
    }
    
    func didImpress(bannerAd: WABannerAd) {
        onImpression?([:])
    }

    func didLoad(bannerAd: WABannerAd) {
        onLoaded?([:])

        let body = [
            "height": bannerAd.adSize.height,
            "width":  bannerAd.adSize.width
        ]

        onSizeChange?(body)
    }

    func didPayRevenue(bannerAd: WABannerAd, data: WARevenueData) {
        onRevenuePaid?(data.toMap())
    }
}
