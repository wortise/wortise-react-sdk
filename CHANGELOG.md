# Changelog

## 1.8.4-beta.1

### Changed

- Updated the Android SDK to 1.8.2-beta01 and the iOS SDK to 1.8.2-beta.1.
- Upgraded AGP to 8.9.3 and raised compileSdk and targetSdk to 36, and minSdk to 24.
- Updated the development baseline to React 19.3.0, React Native 0.88.1, and Expo SDK 58. The declared peer ranges are React >=19.1.0 <20.0.0, React Native >=0.81.5 <0.89.0, and optional Expo >=54.0.0 <59.0.0.
- Updated TypeScript event types, native ad listener handling, and banner view and command types for compatibility with newer React Native versions.
- Updated Android banner event dispatch to use React Native's UIManager event dispatcher and top-level event names.

### Fixed

- Fixed Android compilation by replacing `tryOrNull` with Kotlin's `runCatching` in ad settings and data manager.
- Fixed an iOS null conversion error by passing empty request parameter dictionaries to native App Open, interstitial, and rewarded ad load methods when optional parameters are omitted.

## 1.8.1

* Wortise iOS SDK updated to version 1.8.1
* Raised the minimum iOS version to 15.0 (required by the DT Exchange and Meta mediation adapters)

## 1.8.0

* Wortise Android SDK updated to version 1.8.0
* Wortise iOS SDK updated to version 1.8.0
* Support for native ads
* Collapsible banner support
* Added cooldown methods
* Updated the Kotlin version and Maven repositories
* Various improvements and fixes
