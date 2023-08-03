import { NativeModules } from 'react-native';

const { RNWortiseConsentManager } = NativeModules;

export default {
  get canCollectData() {
    return RNWortiseConsentManager.canCollectData();
  },

  get canRequestPersonalizedAds() {
    return RNWortiseConsentManager.canRequestPersonalizedAds();
  },

  get isGranted() {
    return RNWortiseConsentManager.isGranted();
  },

  get isReplied() {
    return RNWortiseConsentManager.isReplied();
  },

  request() {
    return RNWortiseConsentManager.request();
  },

  requestIfRequired() {
    return RNWortiseConsentManager.requestIfRequired();
  },

  requestOnce() {
    return RNWortiseConsentManager.requestOnce();
  },

  set(granted: boolean) {
    RNWortiseConsentManager.set(granted);
  },

  setIabString(value: string) {
    RNWortiseConsentManager.setIabString(value);
  },
};
