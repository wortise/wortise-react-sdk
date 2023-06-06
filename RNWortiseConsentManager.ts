import { NativeModules } from 'react-native';

const { RNWortiseConsentManager } = NativeModules;

export default {
  get canCollectData() {
    return RNWortiseConsentManager.canCollectData();
  },

  get isGranted() {
    return RNWortiseConsentManager.isGranted();
  },

  get isReplied() {
    return RNWortiseConsentManager.isReplied();
  },

  request(withOptOut: boolean) {
    return RNWortiseConsentManager.request(withOptOut ?? false);
  },

  requestOnce(withOptOut: boolean) {
    return RNWortiseConsentManager.requestOnce(withOptOut ?? false);
  },

  set(granted: boolean) {
    RNWortiseConsentManager.set(granted);
  },

  setIabString(value: string) {
    RNWortiseConsentManager.setIabString(value);
  },
};
