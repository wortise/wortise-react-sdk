import WortiseSDK

extension WAPreloadConfiguration {

    static func from(_ options: NSDictionary?) -> WAPreloadConfiguration {
        let bufferSize = options?["bufferSize"] as? Int ?? 1

        let parameters = WARequestParameters.from(options?["requestParameters"] as? NSDictionary)

        return WAPreloadConfiguration(bufferSize: bufferSize, parameters: parameters)
    }
}
