import WortiseSDK

extension WAAdValue {

    func toMap() -> [String: Any?] {
        return [
            "currency":  currency,
            "precision": precision?.name,
            "value":     value
        ]
    }
}
