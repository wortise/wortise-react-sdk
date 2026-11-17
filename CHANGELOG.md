# Changelog

All notable changes to this project are documented here.

## [1.8.4-beta.1]

### Added

- Added 59 iOS SKAdNetwork identifiers to the Expo config plugin while preserving identifiers supplied by the app.
- Added integration and compatibility guidance for React Native CLI and Expo, including Kotlin, CocoaPods and iOS 27 scene requirements.

### Changed

- Updated the development toolchain to React 19.3.0, React Native 0.88.1 and Expo SDK 58; removed `@types/react-native`, which React Native now provides.
- Updated Android SDK from `com.wortise:android-sdk:1.8.0` to `1.8.2-beta01` and iOS SDK from `WortiseSDK` 1.8.1 to `1.8.2-beta.1`. Raised AGP from 7.4.2 to 8.9.3, compile SDK from 35 to 36, minimum SDK from 23 to 24 and target SDK from 35 to 36.
- Bounded peer compatibility ranges to React Native 0.81.5–0.88.x, React 19.1–19.x and Expo SDK 54–58; the README distinguishes installable versions from tested host combinations.
- Updated TypeScript event and revenue payload types, native ad listener handling and banner callback payload mapping for newer React Native versions.

### Fixed

- Replaced Android calls to the removed `tryOrNull` extension with `runCatching`.
- Passed empty request parameter dictionaries to native App Open, interstitial and rewarded load methods when optional parameters are omitted, to avoid an iOS null conversion error.

### Validation

- Validated Android banner, app-open, interstitial, rewarded and native test ad loads, plus selected dismissal callbacks, with the Expo 58 test app.
- iOS 27 tests with WortiseSDK 1.8.2-beta.1 observed banner, fullscreen and native ad callbacks.
