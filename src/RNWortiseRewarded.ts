import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortisePreloadOptions } from './WortisePreloadOptions';
import { WortiseRequestParameters } from './WortiseRequestParameters';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseRewarded } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseRewarded);

type RewardedEventMap = {
  onRewardedAdPreloaded: { adUnitId: string };
  onRewardedAdsExhausted: { adUnitId: string };
  onRewardedClicked: null;
  onRewardedCompleted: { amount: number; label: string; success: boolean };
  onRewardedDismissed: null;
  onRewardedFailedToLoad: { message: string; name: string };
  onRewardedFailedToPreload: { adUnitId: string; message: string; name: string };
  onRewardedFailedToShow: { message: string; name: string };
  onRewardedImpression: null;
  onRewardedLoaded: null;
  onRewardedRevenuePaid: WortiseRevenueData;
  onRewardedShown: null;
};

export type RewardedEvent = keyof RewardedEventMap;

export default {
  get cooldownRemainingMs() {
    return RNWortiseRewarded.cooldownRemainingMs();
  },

  get isAvailable() {
    return RNWortiseRewarded.isAvailable();
  },

  get isInCooldown() {
    return RNWortiseRewarded.isInCooldown();
  },

  get isShowing() {
    return RNWortiseRewarded.isShowing();
  },

  addEventListener<K extends RewardedEvent>(
    eventType: K,
    handler: (event: RewardedEventMap[K]) => void,
  ): EmitterSubscription {
    return emitter.addListener(eventType, event => handler(event as RewardedEventMap[K]));
  },

  destroy() {
    RNWortiseRewarded.destroy();
  },

  destroyAllPreloads() {
    RNWortiseRewarded.destroyAllPreloads();
  },

  destroyPreload(adUnitId: string): Promise<boolean> {
    return RNWortiseRewarded.destroyPreload(adUnitId);
  },

  getNumAdsAvailable(adUnitId: string): Promise<number> {
    return RNWortiseRewarded.getNumAdsAvailable(adUnitId);
  },

  async isAdAvailable(adUnitId: string): Promise<boolean> {
    return (await RNWortiseRewarded.getNumAdsAvailable(adUnitId)) > 0;
  },

  loadAd(requestParameters?: WortiseRequestParameters) {
    RNWortiseRewarded.loadAd(requestParameters ?? {});
  },

  pollAd(adUnitId: string): Promise<boolean> {
    return RNWortiseRewarded.pollAd(adUnitId);
  },

  removeAllListeners(eventType: RewardedEvent) {
    emitter.removeAllListeners(eventType);
  },

  setAdUnitId(adUnitId: string) {
    RNWortiseRewarded.setAdUnitId(adUnitId);
  },

  showAd() {
    return RNWortiseRewarded.showAd();
  },

  startPreload(adUnitId: string, options?: WortisePreloadOptions): Promise<boolean> {
    return RNWortiseRewarded.startPreload(adUnitId, options ?? {});
  },
};
