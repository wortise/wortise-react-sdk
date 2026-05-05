import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseAppOpen } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseAppOpen);

type AppOpenEventMap = {
  onAppOpenClicked: null;
  onAppOpenDismissed: null;
  onAppOpenFailedToLoad: { message: string; name: string };
  onAppOpenFailedToShow: { message: string; name: string };
  onAppOpenImpression: null;
  onAppOpenLoaded: null;
  onAppOpenRevenuePaid: WortiseRevenueData;
  onAppOpenShown: null;
};

export type AppOpenEvent = keyof AppOpenEventMap;

export default {
  get cooldownRemainingMs() {
    return RNWortiseAppOpen.cooldownRemainingMs();
  },

  get isAvailable() {
    return RNWortiseAppOpen.isAvailable();
  },

  get isInCooldown() {
    return RNWortiseAppOpen.isInCooldown();
  },

  get isShowing() {
    return RNWortiseAppOpen.isShowing();
  },

  addEventListener<K extends AppOpenEvent>(
    eventType: K,
    handler: (event: AppOpenEventMap[K]) => void,
  ): EmitterSubscription {
    return emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseAppOpen.destroy();
  },

  loadAd() {
    RNWortiseAppOpen.loadAd();
  },

  removeAllListeners(eventType: AppOpenEvent) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseAppOpen.setAdUnitId(adUnitId);
  },

  setAutoReload(autoReload: boolean) {
    RNWortiseAppOpen.setAutoReload(autoReload);
  },

  showAd() {
    return RNWortiseAppOpen.showAd();
  },

  tryToShowAd() {
    return RNWortiseAppOpen.tryToShowAd();
  },
};
