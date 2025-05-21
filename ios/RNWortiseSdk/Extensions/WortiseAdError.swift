import WortiseSDK

extension WAAdError {

    func toMap() -> [String: Any?] {
        return [
            "message": message,
            "name":    name
        ]
    }
}