import { NativeEventEmitter, NativeModules } from "react-native";

const { RNWortiseRewarded } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseRewarded);

export default {
  get isAvailable() {
    return RNWortiseRewarded.isAvailable();
  },

  get isShowing() {
    return RNWortiseRewarded.isShowing();
  },

  addEventListener(eventType, handler) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseRewarded.destroy();
  },

  loadAd() {
    RNWortiseRewarded.loadAd();
  },

  removeAllListeners(eventType) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId) {
    RNWortiseRewarded.setAdUnitId(adUnitId);
  },

  showAd() {
    RNWortiseRewarded.showAd();
  },
};
