import WortiseSDK

extension WARevenueData {

    func toMap() -> [String: Any?] {
        return [
            "revenue": revenue.toMap(),
            "source":  source
        ]
    }
}
