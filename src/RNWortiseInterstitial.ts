import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortiseRequestParameters } from './WortiseRequestParameters';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseInterstitial } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseInterstitial);

type InterstitialEventMap = {
  onInterstitialClicked: null;
  onInterstitialDismissed: null;
  onInterstitialFailedToLoad: { message: string; name: string };
  onInterstitialFailedToShow: { message: string; name: string };
  onInterstitialImpression: null;
  onInterstitialLoaded: null;
  onInterstitialRevenuePaid: WortiseRevenueData;
  onInterstitialShown: null;
};

export type InterstitialEvent = keyof InterstitialEventMap;

export default {
  get cooldownRemainingMs() {
    return RNWortiseInterstitial.cooldownRemainingMs();
  },

  get isAvailable() {
    return RNWortiseInterstitial.isAvailable();
  },

  get isInCooldown() {
    return RNWortiseInterstitial.isInCooldown();
  },

  get isShowing() {
    return RNWortiseInterstitial.isShowing();
  },

  addEventListener<K extends InterstitialEvent>(
    eventType: K,
    handler: (event: InterstitialEventMap[K]) => void,
  ): EmitterSubscription {
    return emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseInterstitial.destroy();
  },

  loadAd(requestParameters?: WortiseRequestParameters) {
    RNWortiseInterstitial.loadAd(requestParameters ?? null);
  },

  removeAllListeners(eventType: InterstitialEvent) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseInterstitial.setAdUnitId(adUnitId);
  },

  showAd() {
    return RNWortiseInterstitial.showAd();
  },
};
