import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNWortiseAppOpen } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseAppOpen);

const Orientation = {
  LANDSCAPE: 'LANDSCAPE',
  PORTRAIT: 'PORTRAIT',
};

export default {
  Orientation,

  get isAvailable() {
    return RNWortiseAppOpen.isAvailable();
  },

  get isShowing() {
    return RNWortiseAppOpen.isShowing();
  },

  addEventListener(eventType: string, handler: (event: object) => void) {
    emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseAppOpen.destroy();
  },

  loadAd() {
    RNWortiseAppOpen.loadAd();
  },

  removeAllListeners(eventType: string) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseAppOpen.setAdUnitId(adUnitId);
  },

  setAutoReload(autoReload: boolean) {
    RNWortiseAppOpen.setAutoReload(autoReload);
  },

  setOrientation(orientation: string) {
    RNWortiseAppOpen.setOrientation(orientation);
  },

  showAd() {
    RNWortiseAppOpen.showAd();
  },

  tryToShowAd() {
    RNWortiseAppOpen.tryToShowAd();
  },
};
