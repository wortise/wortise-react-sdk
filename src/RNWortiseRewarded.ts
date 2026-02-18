import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseRewarded } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseRewarded);

type RewardedEventMap = {
  onRewardedClicked: null;
  onRewardedCompleted: { amount: number; label: string; success: boolean };
  onRewardedDismissed: null;
  onRewardedFailedToLoad: { message: string; name: string };
  onRewardedFailedToShow: { message: string; name: string };
  onRewardedImpression: null;
  onRewardedLoaded: null;
  onRewardedRevenuePaid: WortiseRevenueData;
  onRewardedShown: null;
};

export type RewardedEvent = keyof RewardedEventMap;

export default {
  get isAvailable() {
    return RNWortiseRewarded.isAvailable();
  },

  get isShowing() {
    return RNWortiseRewarded.isShowing();
  },

  addEventListener<K extends RewardedEvent>(
    eventType: K,
    handler: (event: RewardedEventMap[K]) => void,
  ): EmitterSubscription {
    return emitter.addListener(eventType, handler);
  },

  destroy() {
    RNWortiseRewarded.destroy();
  },

  loadAd() {
    RNWortiseRewarded.loadAd();
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
};
