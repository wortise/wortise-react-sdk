import { NativeEventEmitter, NativeModules } from "react-native";

const { RNWortiseInterstitial } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseInterstitial);

export default {
  get isAvailable() {
    return RNWortiseInterstitial.isAvailable();
  },

  addEventListener(eventType, handler) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseInterstitial.destroy();
  },

  loadAd() {
    RNWortiseInterstitial.loadAd();
  },

  removeAllListeners(eventType) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId) {
    RNWortiseInterstitial.setAdUnitId(adUnitId);
  },

  showAd() {
    RNWortiseInterstitial.showAd();
  },
};
