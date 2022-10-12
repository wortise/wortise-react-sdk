import { NativeEventEmitter, NativeModules } from "react-native";

const { RNWortiseAppOpen } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseAppOpen);

const Orientation = {
  LANDSCAPE: "LANDSCAPE",
  PORTRAIT: "PORTRAIT",
};

export default {
  Orientation,

  get isAvailable() {
    return RNWortiseAppOpen.isAvailable();
  },

  get isShowing() {
    return RNWortiseAppOpen.isShowing();
  },

  addEventListener(eventType, handler) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseAppOpen.destroy();
  },

  loadAd() {
    RNWortiseAppOpen.loadAd();
  },

  removeAllListeners(eventType) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId) {
    RNWortiseAppOpen.setAdUnitId(adUnitId);
  },

  setAutoReload(autoReload) {
    RNWortiseAppOpen.setAutoReload(autoReload);
  },

  setOrientation(orientation) {
    RNWortiseAppOpen.setOrientation(orientation);
  },

  showAd() {
    RNWortiseAppOpen.showAd();
  },

  tryToShowAd() {
    RNWortiseAppOpen.tryToShowAd();
  },
};
