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


  request(withOptOut) {
    return RNWortiseConsentManager.request(withOptOut ?? false);
  },
  
  requestOnce(withOptOut) {
    return RNWortiseConsentManager.requestOnce(withOptOut ?? false);
  },

  set(granted) {
    RNWortiseConsentManager.set(granted);
  }
}; 
