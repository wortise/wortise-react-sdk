# Changelog

## 1.8.4-beta.1

- Updated the Android SDK to 1.8.2-beta01 and the iOS SDK to 1.8.2-beta.1.
- Upgraded AGP to 8.9.3 and raised compileSdk and targetSdk to 36, and minSdk to 24.
- Updated TypeScript event and revenue payload types, native ad listener handling, and banner callback payload mapping to improve compatibility with newer React Native versions.
- Replaced calls to the removed tryOrNull extension with runCatching on Android.
- Fixed an iOS null conversion error by passing empty request parameter dictionaries to native App Open, interstitial, and rewarded ad load methods when optional parameters are omitted.

## 1.8.1

- Wortise iOS SDK updated to version 1.8.1
- Raised the minimum iOS version to 15.0 (required by the DT Exchange and Meta mediation adapters)

## 1.8.0

- Wortise Android SDK updated to version 1.8.0
- Wortise iOS SDK updated to version 1.8.0
- Support for native ads
- Collapsible banner support
- Added cooldown methods
- Updated the Kotlin version and Maven repositories
- Various improvements and fixes
