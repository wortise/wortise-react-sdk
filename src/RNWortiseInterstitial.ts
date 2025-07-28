import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNWortiseInterstitial } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseInterstitial);

export default {
  get isAvailable() {
    return RNWortiseInterstitial.isAvailable();
  },

  get isShowing() {
    return RNWortiseInterstitial.isShowing();
  },

  addEventListener(eventType: string, handler: (event: object) => void) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseInterstitial.destroy();
  },

  loadAd() {
    RNWortiseInterstitial.loadAd();
  },

  removeAllListeners(eventType: string) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseInterstitial.setAdUnitId(adUnitId);
  },

  showAd() {
    RNWortiseInterstitial.showAd();
  },
};
