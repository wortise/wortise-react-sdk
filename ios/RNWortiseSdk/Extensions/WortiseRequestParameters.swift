import WortiseSDK

extension WARequestParameters {

    static func from(_ params: NSDictionary?) -> WARequestParameters {
        let agent = params?["agent"] as? String

        let collapsible: WACollapsiblePosition?

        switch params?["collapsible"] as? String {
        case "bottom": collapsible = .bottom
        case "top":    collapsible = .top
        default:       collapsible = nil
        }

        return WARequestParameters(agent: agent, collapsible: collapsible)
    }
}
