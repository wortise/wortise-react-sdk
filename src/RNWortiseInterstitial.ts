import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortisePreloadOptions } from './WortisePreloadOptions';
import { WortiseRequestParameters } from './WortiseRequestParameters';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseInterstitial } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseInterstitial);

type InterstitialEventMap = {
  onInterstitialAdPreloaded: { adUnitId: string };
  onInterstitialAdsExhausted: { adUnitId: string };
  onInterstitialClicked: null;
  onInterstitialDismissed: null;
  onInterstitialFailedToLoad: { message: string; name: string };
  onInterstitialFailedToPreload: { adUnitId: string; message: string; name: string };
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
    return emitter.addListener(eventType, event => handler(event as InterstitialEventMap[K]));
  },

  destroy() {
    RNWortiseInterstitial.destroy();
  },

  destroyAllPreloads() {
    RNWortiseInterstitial.destroyAllPreloads();
  },

  destroyPreload(adUnitId: string): Promise<boolean> {
    return RNWortiseInterstitial.destroyPreload(adUnitId);
  },

  getNumAdsAvailable(adUnitId: string): Promise<number> {
    return RNWortiseInterstitial.getNumAdsAvailable(adUnitId);
  },

  async isAdAvailable(adUnitId: string): Promise<boolean> {
    return (await RNWortiseInterstitial.getNumAdsAvailable(adUnitId)) > 0;
  },

  loadAd(requestParameters?: WortiseRequestParameters) {
    RNWortiseInterstitial.loadAd(requestParameters ?? {});
  },

  pollAd(adUnitId: string): Promise<boolean> {
    return RNWortiseInterstitial.pollAd(adUnitId);
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

  startPreload(adUnitId: string, options?: WortisePreloadOptions): Promise<boolean> {
    return RNWortiseInterstitial.startPreload(adUnitId, options ?? {});
  },
};
