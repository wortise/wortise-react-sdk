# Wortise SDK for React Native

Integrate Wortise advertising into React Native applications using the native Wortise SDKs for Android and iOS.

Supports React Native CLI and Expo projects through the Expo config plugin.

## Requirements

Host applications must meet the requirements of the Wortise native SDKs and their React Native or Expo toolchain.

| Platform            | Requirement                                               |
| ------------------- | --------------------------------------------------------- |
| Android             | `minSdk` 24 and `compileSdk` 36 or later                  |
| Android build tools | Android Gradle Plugin 8.9.1+, Gradle 8.11.1+, Kotlin 2.2+ |
| iOS                 | Deployment target iOS 15.0+                               |
| Expo SDK 58         | Node.js 22.13+                                            |

See the [Wortise integration requirements](https://docs.wortise.com/react-native/sdk-integration/#requirements).

## Compatibility

| Package        | Supported range       |
| -------------- | --------------------- |
| `react-native` | `>=0.81.5 <0.89`      |
| `react`        | `>=19.1 <20`          |
| `expo`         | `>=54 <59` (optional) |

`expo` and `@expo/config-plugins` are optional peer dependencies. **React Native CLI projects do not require Expo.**

For Expo, use the React Native and React versions associated with your Expo SDK. See the [Expo version documentation](https://docs.expo.dev/versions/latest/).

## Installation

Install the package in your application:

```sh
npm install @wortise/react-native-sdk
```

### Expo

The native Wortise modules require an Expo development build; they do not run in Expo Go.

Add the Wortise config plugin and your Google Mobile Ads application IDs to `app.json`:

```json
{
  "expo": {
    "plugins": [
      [
        "@wortise/react-native-sdk",
        {
          "androidGoogleAppId": "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy",
          "iosGoogleAppId": "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
        }
      ]
    ]
  }
}
```

The plugin configures the Android Maven repositories and Google application ID. On iOS, it adds `GADApplicationIdentifier`.

Add the identifiers from the [Wortise iOS integration guide](https://docs.wortise.com/react-native/sdk-integration/#setup-infoplist-ios) to `expo.ios.infoPlist.SKAdNetworkItems` in `app.json` before running prebuild. Use an array of objects with a `SKAdNetworkIdentifier` key and keep any entries your app already uses. See the [Expo `ios.infoPlist` reference](https://docs.expo.dev/versions/latest/config/app/#infoplist).

Generate the native projects and build your app:

```sh
npx expo prebuild
npx expo run:android
# Or, to build for iOS:
npx expo run:ios
```

After changing native configuration, rebuild the app. If Metro needs a clean restart, run `npx expo start --dev-client --clear`.

### React Native CLI

Configure the native projects manually:

1. **Android:** Add the Google Mobile Ads application ID to `android/app/src/main/AndroidManifest.xml`.
2. **iOS:** Add `GADApplicationIdentifier` and `SKAdNetworkItems` to `ios/<App>/Info.plist` using the [Wortise iOS integration guide](https://docs.wortise.com/react-native/sdk-integration/#setup-infoplist-ios).
3. **Android repositories:** If Gradle cannot resolve the ad SDK dependencies, add the required Maven repositories to the root project:

   ```text
   https://maven.wortise.com/artifactory/public
   https://artifact.bytedance.com/repository/pangle
   https://artifactory.bidmachine.io/bidmachine
   ```

Install CocoaPods dependencies for iOS:

```sh
cd ios && pod install --repo-update
```

For more details, see the [Wortise React Native integration guide](https://docs.wortise.com/react-native/sdk-integration/).

## Test a local package with Yarn

To try unpublished SDK changes in a host app, use Yarn Classic 1.x and install a tarball. This avoids Metro resolving a symlink into the SDK checkout, where the host app's `react-native` dependency may not be visible. The SDK repository's `yarn.lock` uses the Yarn 1 format.

```sh
cd /path/to/wortise-react-sdk
yarn install
yarn build
yarn build:plugin
yarn pack --filename /tmp/wortise-react-sdk-local.tgz

cd /path/to/host-app
yarn add file:/tmp/wortise-react-sdk-local.tgz
```

After installing the tarball, rebuild the native app and restart Metro. Repeat the build, pack and install steps after SDK changes. Do not keep the local tarball path as the host app's release dependency.

## Native integration notes

- **Android:** Older React Native or Expo projects may use Kotlin versions below the SDK minimum. Update the host project's Kotlin Gradle plugin, and any associated KSP version, if required by the build.
- **iOS:** When building with the iOS 27 SDK, the host app must support the `UIScene` lifecycle. Older React Native templates may need a migration; Expo 57 provides the `ios.enableSceneSupport` option through `expo-build-properties`.
- **CocoaPods:** After updating the package, reinstall pods and check `Podfile.lock` if you need to confirm which native Wortise SDK version was resolved.

For additional platform configuration, refer to the [official integration documentation](https://docs.wortise.com/react-native/sdk-integration/).

## Native build troubleshooting

### iOS: CocoaPods deployment targets (Xcode 27)

Some ad network dependencies declare deployment targets below iOS 15, which can cause compilation errors with Xcode 27.

If you encounter this issue, add the following code to your `Podfile`, **inside the existing `post_install` block**, after `react_native_post_install`:

```ruby
affected_targets = %w[
  Ads-Global-AdsGlobalSDK
  Ads-Global-TikTokBusinessSDK
  BidMachine-BidMachine
  Google-Mobile-Ads-SDK-GoogleMobileAdsResources
  GoogleUserMessagingPlatform-UserMessagingPlatformResources
  OMSDK_Appodeal-OMSDK_Appodeal
  UnityAds-UnityAdsResources
  UnityCoherenceLib-UnityCoherenceLib
  VungleAds-VungleAds
]

installer.pods_project.targets.each do |target|
  next unless affected_targets.include?(target.name)

  target.build_configurations.each do |configuration|
    minimum = configuration.build_settings['IPHONEOS_DEPLOYMENT_TARGET']

    if minimum && minimum.to_f < 15.0
      configuration.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '15.0'
    end
  end
end
```

Then reinstall the pods:

```bash
cd ios
pod install
```

**Notes:**

- Do not create a second `post_install` block.
- Apply this workaround only when encountering deployment target errors.
- Pod target names may change when ad network dependencies are updated.

### Android: Kotlin and KSP compatibility

Older React Native and Expo projects may use Kotlin versions that are incompatible with the Wortise Android SDK.

If you encounter Kotlin compilation errors, update the host project's Kotlin Gradle plugin and related configuration.

The following versions have been verified to compile:

| Environment | Kotlin | Additional configuration |
| ----------- | ------ | ------------------------ |
| Expo SDK 54 | 2.3.0  | KSP 2.3.7                |

Ensure that the configured Kotlin version matches the Kotlin Gradle plugin dependency.

```groovy
ext.kspVersion = '2.3.7'
```

Rebuild the Android project after updating the toolchain.
