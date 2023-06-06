import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNWortiseRewarded } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseRewarded);

export default {
  get isAvailable() {
    return RNWortiseRewarded.isAvailable();
  },

  get isShowing() {
    return RNWortiseRewarded.isShowing();
  },

  addEventListener(eventType: string, handler: (event: object) => void) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseRewarded.destroy();
  },

  loadAd() {
    RNWortiseRewarded.loadAd();
  },

  removeAllListeners(eventType: string) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseRewarded.setAdUnitId(adUnitId);
  },

  showAd() {
    RNWortiseRewarded.showAd();
  },
};
