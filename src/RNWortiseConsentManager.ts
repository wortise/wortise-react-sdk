import { NativeModules } from 'react-native';

const { RNWortiseConsentManager } = NativeModules;

export default {
  get canCollectData() {
    return RNWortiseConsentManager.canCollectData();
  },

  get canRequestPersonalizedAds() {
    return RNWortiseConsentManager.canRequestPersonalizedAds();
  },

  get exists() {
    return RNWortiseConsentManager.exists();
  },

  request() {
    return RNWortiseConsentManager.request();
  },

  requestIfRequired() {
    return RNWortiseConsentManager.requestIfRequired();
  },
};
