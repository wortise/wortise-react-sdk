# Wortise React Native plugin

npm package `@wortise/react-native-sdk`, with an Expo config plugin (`plugin/`). Wraps the Android SDK (`android/build.gradle` pin) and the iOS SDK (`RNWortiseSdk.podspec` pin). Old architecture (works on the new one through interop). Sample app: `../wortise-react-expo-sample` (`file:` dependency, copied by yarn: reinstall after edits).

The `wortise-sdk` agent plugin (repo `wortise/wortise-sdk-agent`) holds the full context: how this repo relates to the other SDKs and plugins, versioning, branches, and the release procedure (`wortise-release` skill). Read it before releasing or making cross-platform changes.

## Commands

```bash
yarn lint             # eslint + tsc
npm pack --dry-run    # builds lib/ and plugin/build/
```

## Rules

- Remote `origin` only; ignore `gold`.
- Version: `package.json`; every release adds a `CHANGELOG.md` entry and a git tag. Pre-releases: `npm publish --tag beta`.
- Branch flow: `support/*` or `feature/*` -> PR into `test` -> pre-releases from `test` -> `master` for finals.
- Never publish or push without the maintainer's OK.
