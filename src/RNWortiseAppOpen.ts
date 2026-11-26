import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortisePreloadOptions } from './WortisePreloadOptions';
import { WortiseRequestParameters } from './WortiseRequestParameters';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseAppOpen } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseAppOpen);

type AppOpenEventMap = {
  onAppOpenAdPreloaded: { adUnitId: string };
  onAppOpenAdsExhausted: { adUnitId: string };
  onAppOpenClicked: null;
  onAppOpenDismissed: null;
  onAppOpenFailedToLoad: { message: string; name: string };
  onAppOpenFailedToPreload: { adUnitId: string; message: string; name: string };
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
    return emitter.addListener(eventType, event => handler(event as AppOpenEventMap[K]));
  },

  destroy() {
    RNWortiseAppOpen.destroy();
  },

  destroyAllPreloads() {
    RNWortiseAppOpen.destroyAllPreloads();
  },

  destroyPreload(adUnitId: string): Promise<boolean> {
    return RNWortiseAppOpen.destroyPreload(adUnitId);
  },

  getNumAdsAvailable(adUnitId: string): Promise<number> {
    return RNWortiseAppOpen.getNumAdsAvailable(adUnitId);
  },

  async isAdAvailable(adUnitId: string): Promise<boolean> {
    return (await RNWortiseAppOpen.getNumAdsAvailable(adUnitId)) > 0;
  },

  loadAd(requestParameters?: WortiseRequestParameters) {
    RNWortiseAppOpen.loadAd(requestParameters ?? {});
  },

  pollAd(adUnitId: string): Promise<boolean> {
    return RNWortiseAppOpen.pollAd(adUnitId);
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

  startPreload(adUnitId: string, options?: WortisePreloadOptions): Promise<boolean> {
    return RNWortiseAppOpen.startPreload(adUnitId, options ?? {});
  },

  tryToShowAd() {
    return RNWortiseAppOpen.tryToShowAd();
  },
};
