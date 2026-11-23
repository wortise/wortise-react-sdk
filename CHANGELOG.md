## 1.8.2-beta.1

* Wortise Android SDK updated to version 1.8.2-beta01
* Wortise iOS SDK updated to version 1.8.2-beta.1
* Android: requires Android Gradle Plugin 8.9.1+, compileSdk 36 and minSdk 24
* Supported versions: React Native 0.81.5+, React 19.1+ and, optionally, Expo SDK 54+
* Android: banner events use React Native's event dispatcher, for compatibility with newer React Native versions
* Banner `onFailedToLoad` and `onRevenuePaid` listeners now receive the data directly, as their types declare (it is still available in `nativeEvent` too)
* Android: a banner is now destroyed when its component is unmounted, instead of when the activity is destroyed
* iOS: fixed an error when loading App Open, interstitial and rewarded ads without request parameters

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
